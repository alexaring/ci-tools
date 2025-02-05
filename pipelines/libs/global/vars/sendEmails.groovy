// Return true if we are running on the 'main' branch for
// all libraries.
def is_main_lib()
{
    def ret = true

    def envAll = getContext( hudson.EnvVars )
    envAll.collect { k, v ->
	if (k.startsWith("library")) {
	    println("LIB: ${k} = ${v}")
	    if (v != "main") {
		ret = false
	    }
	}
    }

    return ret
}


// Send completion emails
def call(Map info)
{
    def nonvoting_fail = 0
    def voting_fail = 0
    def stages_fail = 0
    def nonvoting_run = 0
    def voting_run = 0
    def stages_run = 0
    def state = "script error"
    def email_addrs = ''
    def project = env.BUILD_TAG
    def branch = env.BRANCH_NAME

    if (info.containsKey('voting_fail')) {
	voting_fail = info['voting_fail']
    }
    if (info.containsKey('nonvoting_fail')) {
	nonvoting_fail = info['nonvoting_fail']
    }
    if (info.containsKey('stages_fail')) {
	stages_fail = info['stages_fail']
    }
    if (info.containsKey('voting_run')) {
	voting_run = info['voting_run']
    }
    if (info.containsKey('nonvoting_run')) {
	nonvoting_run = info['nonvoting_run']
    }
    if (info.containsKey('stages_run')) {
	stages_run = info['stages_run']
    }

    if (info.containsKey('state')) {
	state = info['state']
    }
    if (info.containsKey('project')) {
	project = info['project']
    }
    if (info.containsKey('branch')) {
	branch = info['branch']
    }
    if (!info.containsKey('email_extra_text')) {
	info['email_extra_text'] = ''
    }
    if (!info.containsKey('exception_text')) {
	info['exception_text'] = ''
    }

    if (state == 'build-ignored') {
	println('build has been ignored, not sending emails')
	return
    }
    // Did we REALLY (like properly) fail??
    if (currentBuild.result == 'FAILURE') {
	state = 'failure'
    }

    // A script exception was logged
    if (info['exception_text'] != '') {
	info['exception_text'] = "\nPlease report the following errors to your friendly local Jenkins admin (though they have probably already seen them and are already panicking).\n\n" +
	    info['exception_text']
	state ='Jenkins exception'
    }

    // Get the per-project email option ('all', 'none', 'only-failures')
    def email_opts = getEmailOptions()
    println("Project email_opts: ${email_opts}")

    // Get the per-project email addresses (if wanted), then add the 'always' one.
    // This looks like it could be 'simplified' into one 'if', but I argue it's clearer
    // this way, as we deal in positive conditions only
    if (state == 'success' && email_opts == 'only-failures') {
	println('email_option is "only-failures" and pipeline has succeeded, only default email sent')
    } else {
	if ((email_opts == 'all' || email_opts == '') ||
	    (state == 'failure')) {
	    email_addrs = getEmails()
	    if (email_addrs != '') {
		email_addrs += ','
	    }
	}
    }
    email_addrs += 'commits@lists.kronosnet.org'
    println("Sending email to ${email_addrs}")

    // Projects can override the email Reply-To header too
    def email_replyto = getEmailReplyTo()
    if (email_replyto == '') {
	email_replyto = 'devel@lists.kronosnet.org' // default
    }
    println("reply-to: ${email_replyto}")

    // Remove "and counting" from the end of the duration string
    def duration = currentBuild.durationString
    def jobDuration = duration.substring(0, duration.length() - 13)

    // Build email strings that apply to all statuses
    def email_title = ''
    if (is_main_lib()) {
	email_title = "[jenkins] ${info['project']} ${branch} (build ${env.BUILD_ID})"
    } else {
	// Don't spam everybody with our test results
	email_title = "[jenkins][cidev] ${info['project']} ${branch} (build ${env.BUILD_ID})"
	email_addrs = "fdinitto@redhat.com, ccaulfie@redhat.com"
    }

    // Add links to coverity scans
    if (info['cov_results_urls'].size() > 0) {
	def cov_urls = '\nCoverity results:\n'
	for (u in info['cov_results_urls']) {
	    cov_urls += "http://ci.kronosnet.org/${u}\n"
	}
	// A bit of a code mess but it keeps the emails tidy
	if (info['email_extra_text'] != '') {
	    info['email_extra_text'] += '\n'
	}
	info['email_extra_text'] += cov_urls
    }

    def email_trailer = """total runtime: ${jobDuration}
${info['email_extra_text']}
Split logs: ${env.BUILD_URL}artifact/
Full log:   ${env.BUILD_URL}consoleText
${info['exception_text']}
"""

    // Make it look nice
    def voting_colon = ''
    if (voting_fail > 0) {
	voting_colon = ':'
    }
    def nonvoting_colon = ''
    if (nonvoting_fail > 0) {
	nonvoting_colon = ':'
    }
    def stages_colon = ''
    if (stages_fail > 0) {
	stages_colon = ':'
    }
    def voting_s = 's'
    if (voting_run == 1) {
	voting_s = ''
    }
    def nonvoting_s = 's'
    if (nonvoting_run == 1) {
	nonvoting_s = ''
    }
    def stage_s = 's'
    if (stages_run == 1) {
	stage_s = ''
    }

    // Now build the email bits
    def subject = ''
    def body = ''
    if (state == 'success' || state == 'completed') {
	// If this pipeline has 'stages' rather than voting/non-voting, then show 'stages' failed
	// FN testing jobs 'complete' but can have stage failures.
	if (stages_fail > 0) {
		subject = "${email_title} completed with state: ${state}"
		body = """
${stages_fail}/${stages_run} Stage${stage_s} failed${stages_colon} ${info['stages_fail_nodes']}
${email_trailer}
"""
	} else if (nonvoting_fail > 0) {
	    // Only non-voting fails
	    subject = "${email_title} succeeded but with ${nonvoting_fail}/${nonvoting_run} non-voting fail${nonvoting_s}"
	    body = """
failed job${nonvoting_s}: ${info['nonvoting_fail_nodes']}
${email_trailer}
"""
	} else {
	    // Just a normal success
	    subject = "${email_title} ${state}"
	    body = email_trailer
	}

	// Failures...
    } else if (voting_fail == 0) {
	// Failed but no voting/nonvoting jobs
	subject = "${email_title} ${state}"
	body = email_trailer
    } else {
	// Normal failure with voting/nonvoting jobs
	subject = "${email_title} completed with state: ${state}"
	body = """
${nonvoting_fail}/${nonvoting_run} Non-voting fail${nonvoting_s}${nonvoting_colon} ${info['nonvoting_fail_nodes']}
${voting_fail}/${voting_run} Voting fail${voting_s}${voting_colon} ${info['voting_fail_nodes']}
${email_trailer}
"""
    }

    // Dump the email text to the log, so it doesn't get lost
    println("""
email contents:

To: ${email_addrs}
ReplyTo: ${email_replyto}
Subject: ${subject}
${body}
""")

    // Actually send it
    mail to: email_addrs,
	replyTo: email_replyto,
	subject: subject,
	body: body
}
