def call(String target)
{
    def labels = [:]
    labels['anvil-ci-bm-phy01'] = ['centos-8','stable','x86-64','rpm','yum']
    labels['anvil-ci-bm-phy02'] = ['anvil-bm','centos-8','stable','x86-64','rpm','yum']
    labels['anvil-ci-bm-phy03'] = ['centos-8','stable','x86-64','rpm','yum','anvil-bm']
    labels['anvil-ci-bm-phy04'] = ['centos-8','stable','x86-64','rpm','yum','anvil-bm']
    labels['anvil-ci-centos-8-stream'] = ['centos-8','stable','x86-64','rpm','yum','anvil']
    labels['anvil-ci-centos-9-stream'] = ['centos-9','unstable','x86-64','rpm','yum','anvil']
    labels['anvil-ci-rhel-8'] = ['rhel-8','stable','x86-64','rpm','yum','anvil']
    labels['anvil-ci-rhel-9'] = ['rhel-9','stable','x86-64','rpm','yum','anvil']
    labels['centos-8-x86-64'] = ['centos-8','stable','x86-64','voting','rpm','yum','nonvoting-clang','buildrpms']
    labels['centos-9-power9-ppc64le'] = ['centos-9','unstable','power9-ppc64le','nonvoting','rpm','yum','nonvoting-clang','buildrpms']
    labels['centos-9-s390x'] = ['centos-9','unstable','s390x','nonvoting','rpm','yum','nonvoting-clang','buildrpms']
    labels['centos-9-x86-64'] = ['centos-9','unstable','x86-64','nonvoting','rpm','yum','nonvoting-clang','buildrpms']
    labels['debian10-x86-64'] = ['debian10','x86-64','voting','apt','stable','nonvoting-clang']
    labels['debian11-x86-64'] = ['debian11','x86-64','voting','apt','stable','nonvoting-clang']
    labels['debian12-ci-test-x86-64'] = ['debian12','x86-64','apt','stable','ci-test','test-nonvoting']
    labels['debian12-x86-64'] = ['debian12','x86-64','voting','apt','stable','nonvoting-clang']
    labels['debian-experimental-x86-64'] = ['debian','experimental','x86-64','nonvoting','apt','nonvoting-clang']
    labels['debian-testing-x86-64'] = ['debian','testing','x86-64','voting','apt','nonvoting-clang']
    labels['debian-unstable-cross-x86-64'] = ['debian','unstable','cross','x86-64','apt']
    labels['debian-unstable-x86-64'] = ['debian','unstable','x86-64','nonvoting','apt','nonvoting-clang']
    labels['fedora37-x86-64'] = ['fedora37','stable','x86-64','voting','rpm','yum','nonvoting-clang','buildrpms']
    labels['fedora38-power9-ppc64le'] = ['fedora38','stable','power9-ppc64le','voting','rpm','yum','nonvoting-clang','buildrpms']
    labels['fedora38-s390x'] = ['fedora38','stable','x390x','voting','rpm','yum','nonvoting-clang','buildrpms']
    labels['fedora38-x86-64'] = ['fedora38','stable','x86-64','voting','rpm','yum','nonvoting-clang','buildrpms']
    labels['fedora-rawhide-power9-ppc64le'] = ['fedora-rawhide','unstable','power9-ppc64le','nonvoting','rpm','yum','nonvoting-clang','buildrpms']
    labels['fedora-rawhide-s390x'] = ['fedora-rawhide','unstable','s390x','nonvoting','rpm','yum','nonvoting-clang','buildrpms']
    labels['fedora-rawhide-x86-64'] = ['fedora-rawhide','unstable','x86-64','nonvoting','rpm','yum','nonvoting-clang','buildrpms']
    labels['freebsd-12-x86-64'] = ['freebsd-12','stable','x86-64','voting','freebsd','nonvoting-clang']
    labels['freebsd-13-x86-64'] = ['freebsd-13','stable','x86-64','voting','freebsd','nonvoting-clang']
    labels['freebsd-devel-x86-64'] = ['freebsd-devel','unstable','x86-64','nonvoting','freebsd','nonvoting-clang']
    labels['jenkins-jumphost'] = ['yum']
    labels['opensuse-15-x86-64'] = ['opensuse-15','stable','x86-64','voting','rpm','zypper','nonvoting-clang','buildrpms']
    labels['opensuse-tumbleweed-x86-64'] = ['opensuse-tumbleweed','unstable','x86-64','nonvoting','rpm','zypper','nonvoting-clang','buildrpms']
    labels['rhel88z-power9-ppc64le'] = ['rhel88z','stable','power9','ppc64le','voting','rpm','yum','nonvoting-clang','buildrpms']
    labels['rhel88z-s390x'] = ['rhel88z','stable','s390x','voting','rpm','yum','nonvoting-clang','buildrpms']
    labels['rhel88z-x86-64'] = ['rhel88z','stable','x86-64','voting','rpm','yum','nonvoting-clang','buildrpms']
    labels['rhel8-ci-test-x86-64'] = ['rhel88z','stable','x86-64','rpm','yum','ci-test','test-voting','test-buildrpms']
    labels['rhel8-coverity-x86-64'] = ['rhel88z','stable','x86-64','voting','rhel8-coverity','yum','covscan']
    labels['rhel92z-power9-ppc64le'] = ['rhel92z','stable','power9-ppc64le','voting','rpm','yum','nonvoting-clang','buildrpms']
    labels['rhel92z-s390x'] = ['rhel92z','stable','s390x','voting','rpm','yum','nonvoting-clang','buildrpms']
    labels['rhel92z-x86-64'] = ['rhel92z','stable','x86-64','voting','rpm','yum','nonvoting-clang','buildrpms']
    labels['rhel9-ci-test-x86-64'] = ['rhel92z','stable','x86-64','rpm','yum','ci-test','test-voting']
    labels['rhel9-coverity-x86-64'] = ['rhel92z','stable','x86-64','voting','rhel9-coverity','yum','covscan','test-covscan']
    labels['rhel9-kbuild-x86-64'] = ['rhel9','stable','x86-64','yum','libvirtd']
    labels['rhel9-vapor-x86-64'] = ['rhel9','stable','x86-64','yum','osp','az','gcp','ocpv']
    labels['ubuntu-20-04-lts-x86-64'] = ['ubuntu20.04','stable','x86-64','voting','apt','nonvoting-clang']
    labels['ubuntu-22-04-lts-x86-64'] = ['ubuntu22.04','stable','x86-64','voting','apt','nonvoting-clang']
    labels['ubuntu-22-10-x86-64'] = ['ubuntu22.10','stable','x86-64','voting','apt','nonvoting-clang']
    labels['ubuntu-23-04-x86-64'] = ['ubuntu23.04','stable','x86-64','voting','apt','nonvoting-clang']
    labels['ubuntu-devel-x86-64'] = ['ubuntu-devel','unstable','x86-64','nonvoting','apt','nonvoting-clang']

    def nodelist = []
    for (i in labels) {
	if (labels[i.key].contains(target)) {
	    nodelist += i.key
	}
    }
    return nodelist
}


