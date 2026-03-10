DESCRIPTION = "Swap file management service"
LICENSE = "CLOSED"

inherit systemd

SYSTEMD_SERVICE:${PN} = "enable-hwclock.service"
SYSTEMD_AUTO_ENABLE = "enable"

SRC_URI = "file://enable-hwclock.sh \
           file://enable-hwclock.service"

RDEPENDS:${PN} = "bash"


do_install() {
    install -d ${D}${bindir}
    install -d ${D}${systemd_system_unitdir}
    
    install -m 0755 ${WORKDIR}/enable-hwclock.sh ${D}${bindir}/
    install -m 0644 ${WORKDIR}/enable-hwclock.service ${D}${systemd_system_unitdir}/
}

FILES:${PN} += "${systemd_system_unitdir}/enable-hwclock.service"