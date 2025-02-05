// Return options for this build.
// Return a map of key=value pairs that will
// be passed into the environment on the building node.
//
// info contains all of the global build information
//      (do NOT change anything in here)
// extras contains 'extra' variables added for this run only
//      during the buildRunMap phase
// agentName is the node we are building on,
// branch is the git branch we are building (for)
def call(Map info, Map extras, String agentName, String branch)
{
    def props = [:]

    props['MAKEOPTS'] = ''
    props['PARALLELMAKE'] = ''
    props['MAKEINSTALLOPTS'] = ''
    props['TOPTS'] = ''
    //props['CHECKS'] = ''
    props['EXTRACHECKS'] = ''
    props['EXTERNAL_LD_LIBRARY_PATH'] = ''
    props['SPECVERSION'] = env.BUILD_NUMBER
    props['RPMDEPS'] = 'libqb-devel doxygen2man'

    props['DISTROCONFOPTS'] = ''

    if (agentName.startsWith("debian-unstable-cross")) {
	if (extras['ARCH'] == 'ARM') {
	    props['compiler'] = 'arm-linux-gnueabihf-gcc'
	    props['DISTROCONFOPTS'] += ' --host=arm-linux-gnueabihf --target=arm-linux-gnueabihf'
	}
    }

    return props
}
