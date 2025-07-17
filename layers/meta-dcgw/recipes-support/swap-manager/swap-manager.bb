DESCRIPTION = "Swap file management service"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

inherit systemd

SYSTEMD_SERVICE:${PN} = "create-swap.service"
SYSTEMD_AUTO_ENABLE = "enable"

SRC_URI = "file://create-swap.sh \
           file://create-swap.service"

RDEPENDS:${PN} = "bash"


do_install() {
    install -d ${D}${bindir}
    install -d ${D}${systemd_system_unitdir}
    
    install -m 0755 ${WORKDIR}/create-swap.sh ${D}${bindir}/
    install -m 0644 ${WORKDIR}/create-swap.service ${D}${systemd_system_unitdir}/
}

FILES:${PN} += "${systemd_system_unitdir}/create-swap.service"