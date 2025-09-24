DESCRIPTION = "D-Bus support for dhcpcd"
SECTION = "network"
LICENSE = "CLOSED"

DEPENDS = "dhcpcd5 dbus dbus-glib"
RDEPENDS:${PN} = "dhcpcd5 dbus glibc"

SRC_URI = "file://dhcpcd-dbus-debian-arm.tar.gz"

S = "${WORKDIR}/extracted-dbus-arm"

inherit pkgconfig

INSANE_SKIP:${PN} += "already-stripped"

do_install() {
    # Create the directories
    install -d ${D}${libexecdir}/dhcpcd-dbus
    install -d ${D}${datadir}/dbus-1/system-services
    install -d ${D}${sysconfdir}/dbus-1/system.d
    
    # Install the binary and config files
    install -m 0755 "${S}/usr/libexec/dhcpcd-dbus" "${D}${libexecdir}/dhcpcd-dbus/"
    
    # Install the service file
    install -m 0644 "${S}/usr/share/dbus-1/system-services/name.marples.roy.dhcpcd.service" \
                    "${D}${datadir}/dbus-1/system-services/"

    install -m 0644 ${S}/etc/dbus-1/system.d/dhcpcd-dbus.conf ${D}${sysconfdir}/dbus-1/system.d/
}

FILES:${PN} += "${libexecdir}/dhcpcd-dbus \
                ${datadir}/dbus-1/system-services/* \
                ${sysconfdir}/dbus-1/system.d/*"




#apt-get download dhcpcd-dbus
#dpkg -x dhcpcd-dbus_*.deb extracted-dbus-arm
#tar -czf dhcpcd-dbus-debian-arm.tar.gz extracted-dbus-arm/