// Return options for this build.
// Return a map of key=value pairs that will
// be passed into the environment on the building node.
//
// info contains all of the global build information
//      (do NOT change anything in here)
// extras contains extra variables set for this build-type
// agentName is the node we are building on,
// branch is the git branch we are building (for)
def call(Map info, Map extras, String agentName, String branch)
{
    def props = [:]

    props['MAKEOPTS'] = ''
    props['PARALLELMAKE'] = ''
    props['MAKEINSTALLOPTS'] = ''
    props['TOPTS'] = ''
    props['CHECKS'] = ''
    props['EXTRACHECKS'] = ''
    props['EXTERNAL_LD_LIBRARY_PATH'] = ''
    props['extraver'] = "kronosnet-${extras['kronosnetver']}"
    props['SPECVERSION'] = env.BUILD_NUMBER
    props['RPMDEPS'] = 'libnozzle1-devel libknet1-devel libqb-devel'
    props['DISTROCONFOPTS'] = '--enable-snmp --enable-dbus --enable-systemd --enable-nozzle'

    if (!extras.containsKey('compiler') || extras['compiler'] == 'gcc') {
	props['DISTROCONFOPTS'] += ' --enable-fatal-warnings'
    }

    if (agentName.startsWith('freebsd')) {
	props['DISTROCONFOPTS'] += ' --disable-systemd'
    }

    return props
}
