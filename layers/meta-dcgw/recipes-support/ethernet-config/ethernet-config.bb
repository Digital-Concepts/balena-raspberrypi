SUMMARY = "Force Ethernet speed to 100Mbps full duplex using ethtool"
DESCRIPTION = "Installs a systemd service that stop advertising 1000baseT"
LICENSE = "CLOSED"
PR = "r0"

inherit systemd

SRC_URI = "file://ethtool-speed.service"

SYSTEMD_SERVICE:${PN} = "ethtool-speed.service"
SYSTEMD_AUTO_ENABLE:${PN} = "enable"

do_install() {
    install -d ${D}${systemd_system_unitdir}
    install -m 0644 ${WORKDIR}/ethtool-speed.service ${D}${systemd_system_unitdir}/
}