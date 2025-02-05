def sanity_tests_common() {
    return [
	'cleanup',
	'setup',
    ]
}


// pcs variant
def pcs_smoke_tests_common() {
    return \
	sanity_tests_common() +
	['pcs,cli,Setup']
}

def pcs_smoke_tags_common() {
    return [ ]
}

def pcs_basic_tests_common() {
    return [
	'pcs,cli,Auth',
	'pcs,cli,ClusterStartStop',
	'pcs,cli,Stonith',
    ]
}

def pcs_basic_tags_common() {
    return [ ]
}

def pcs_advanced_tests_common() {
    return [
	'pcs,cli,ResourceMove',
    ]
}

def pcs_advanced_tags_common() {
    return [ ]
}

def pcs_smoke_tests_generic_nodes() {
    return \
	pcs_smoke_tests_common()
}

def pcs_all_tests_generic_nodes() {
    return \
	pcs_smoke_tests_common() +
	pcs_basic_tests_common() +
	pcs_advanced_tests_common()
}

def pcs_smoke_tags_generic_nodes() {
    return \
	pcs_smoke_tags_common()
}

def pcs_all_tags_generic_nodes() {
    return \
	pcs_smoke_tags_common() +
	pcs_basic_tags_common() +
	pcs_advanced_tags_common()
}

def pcs_all_tests_2_nodes() {
    return \
	pcs_smoke_tests_common() +
	pcs_basic_tests_common()
}

// commmon sets, even if we don´t use them all
def default_smoke_tests_common() {
    return \
	pcs_smoke_tests_common() +
    [
	'pacemaker,resource,IPv4,recovery',
	'pacemaker,recovery,RecoveryActiveNode',
	'setup,setup_sbd,sbd-only'
    ]
}

def default_smoke_tags_common() {
    return \
	pcs_smoke_tags_common() +
	[ ]
}

// 2 nodes and up
def default_basic_tests_common() {
    return \
	pcs_basic_tests_common() +
    [
	'lvm,lvm_config,cluster-lvmlockd',
	'pacemaker,recovery,RecoveryActiveNode,variant:iterations,iterations:10',
	'pacemaker,recovery,RecoveryRandomNode,variant:iterations,iterations:10',
	'pacemaker,recovery,RecoveryRestartPacemaker-AllNodes',
	'pacemaker,recovery,RecoveryResourceFailure,variant:iterations,iterations:10',
	'pacemaker,recovery,RecoveryActiveNode,sbd-only,kill_reboot',
	'pacemaker,resource,Apache,recovery',
	'pacemaker,resource,Apache,variant:latency,latency:150ms',
	'pacemaker,resource,Clone,variant:scaling,scale:5',
	'pacemaker,resource,ConstraintsTest,variant:scaling,scale:5',
	'pacemaker,resource,Group,variant:scaling,scale:5',
	'pacemaker,resource,Filesystem',
	'pacemaker,resource,FilesystemGroup',
	'pacemaker,usecase,ha-apache',
	'pacemaker,usecase,NFS_Server-AP,lvmlockd,exclusive'
    ]
}

def default_basic_tags_common() {
    return \
	pcs_basic_tags_common() +
	[ ]
}

// 3 nodes and up
def default_advanced_tests_common() {
    return \
	pcs_advanced_tests_common() +
    [
	'pacemaker,recovery,RecoverySwitchFailure,variant:iterations,iterations:10',
	'pacemaker,recovery,RecoveryNodeNetworkFailure',
	'pacemaker,recovery,RecoveryRandomMultiNode,variant:iterations,iterations:10',
	'pacemaker,recovery,RecoveryRandomMultiNodeNQ,variant:iterations,iterations:10',
	'pacemaker,recovery,RecoveryNodeNetworkFailure,sbd-only,network_disruptor',
	'pacemaker,recovery,RecoveryNodeNetworkFailure,sbd-with-one-device,network_disruptor'
    ]
}

def default_advanced_tags_common() {
    return \
	pcs_advanced_tags_common() +
	[ ]
}

def default_smoke_tests_generic_nodes() {
    return \
	default_smoke_tests_common()
}

def default_smoke_tags_generic_nodes() {
    return \
	default_smoke_tags_common()
}

def default_all_tests_generic_nodes() {
    return \
	default_smoke_tests_common() +
	default_basic_tests_common() +
	default_advanced_tests_common()
}

def default_all_tags_generic_nodes() {
    return \
	default_smoke_tags_common() +
	default_basic_tags_common() +
	default_advanced_tags_common()
}

// pre node num override
def default_smoke_tests_1_nodes() {
    return [
	'lvm,snapper,display_snap,nostack,singlenode',
	'lvm,raid_sanity,display_raid,raid4,singlenode'
    ]
}

def default_all_tests_1_nodes() {
    return \
	default_smoke_tests_1_nodes()
}

def default_all_tests_2_nodes() {
    return \
	default_smoke_tests_common() +
	default_basic_tests_common()
}

def default_all_tests_4_nodes() {
    return \
	default_all_tests_generic_nodes() +
	['pacemaker,usecase,apachewebfarm,haproxy']
}

// kernel variant
def kernel_smoke_tests_common() {
    return \
	sanity_tests_common() +
	['skeet']
}

def kernel_smoke_tags_common() {
    return [ ]
}

def kernel_basic_tests_common() {
    return [ ]
}

def kernel_basic_tags_common() {
    return [
	'brawl_quick'
    ]
}

def kernel_smoke_tests_generic_nodes() {
    return \
	kernel_smoke_tests_common()
}

def kernel_all_tests_generic_nodes() {
    return \
	kernel_smoke_tests_common() +
	kernel_basic_tests_common()
}

def kernel_smoke_tags_generic_nodes() {
    return \
	kernel_smoke_tags_common()
}

def kernel_all_tags_generic_nodes() {
    return \
	kernel_smoke_tags_common() +
	kernel_basic_tags_common()
}

// wrapper
def call(Map info)
{
    method = "${info['testvariant']}_${info['tests']}_${info['testtype']}_${info['nodes']}_nodes"
    try {
	"$method"()
    } catch (java.lang.Throwable ex) {
	method = "${info['testvariant']}_${info['tests']}_${info['testtype']}_generic_nodes"
	try {
	    "$method"()
	} catch (java.lang.Throwable genex) {
	    return [ ]
	}
    }
}
