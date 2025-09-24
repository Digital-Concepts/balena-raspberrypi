SECTION = "console/network"
SUMMARY = "dhcpcd - a DHCP client"
DESCRIPTION = "dhcpcd runs on your machine and silently configures your \
               computer to work on the attached networks without trouble \
               and mostly without configuration."

LICENSE = "CLOSED"
#LIC_FILES_CHKSUM = "file://LICENSE;md5=ba9c7e534853aaf3de76c905b2410ffd"

#tar -czf dhcpcd-debian.tar.gz extracted/ 
# mv dhcpcd-debian.tar.gz files/

SRC_URI = "file://dhcpcd-debian.tar.gz \
           file://dhcpcd.service \
           file://dhcpcd@.service \
           file://99-dns-info"

S = "${WORKDIR}/extracted"

inherit pkgconfig systemd useradd

SYSTEMD_SERVICE:${PN} = "dhcpcd.service"

DBDIR ?= "${localstatedir}/lib/${BPN}"

USERADD_PACKAGES = "${PN}"
USERADD_PARAM:${PN} = "--system -d ${DBDIR} -M -s /bin/false -U dhcpcd"

EXTRA_OECONF = "--enable-ipv4 \
                --enable-ipv6 \
                --dbdir=${DBDIR} \
                --sbindir=${base_sbindir} \
                --runstatedir=/run \
                --enable-privsep \
                --privsepuser=dhcpcd \
                --with-hooks \
                --with-eghooks"

do_install() {
    # Install the precompiled binary
    install -d ${D}${base_sbindir}
    install -m 0755 ${S}/sbin/dhcpcd5 ${D}${base_sbindir}/dhcpcd

    # Install hooks and scripts
    install -d ${D}${libdir}/dhcpcd
    cp -r ${S}/lib/dhcpcd/* ${D}${libdir}/dhcpcd/
    install -d ${D}${datadir}/dhcpcd/hooks
    cp -r ${S}/usr/share/dhcpcd/hooks/* ${D}${datadir}/dhcpcd/hooks/
    # Install configuration files
    install -d ${D}${sysconfdir}
    install -m 0755 ${S}/etc/dhcpcd.conf ${D}${sysconfdir}/dhcpcd.conf

    # Install systemd unit files
    install -d ${D}${systemd_system_unitdir}
    install -m 0755 ${WORKDIR}/dhcpcd*.service ${D}${systemd_system_unitdir}

    install -m 0755 ${WORKDIR}/99-dns-info ${D}${libdir}/dhcpcd/dhcpcd-hooks/
    chown -R dhcpcd:dhcpcd ${D}${libdir}/dhcpcd/dhcpcd-hooks/
    # Install man pages
    install -d ${D}${mandir}/man5
    install -m 0755 ${S}/usr/share/man/man5/dhcpcd.conf.5.gz ${D}${mandir}/man5/
    install -d ${D}${mandir}/man8
    install -m 0755 ${S}/usr/share/man/man8/dhcpcd*.8.gz ${D}${mandir}/man8/

    # Install additional files
    install -d ${D}${localstatedir}/lib/dhcpcd5

    # Remove unnecessary /DEBIAN directory
    rm -rf ${S}/DEBIAN
}

INSANE_SKIP:${PN} += "already-stripped"

FILES:${PN} += "/etc /usr/lib /usr/sbin /usr /var"