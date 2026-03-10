#!/bin/bash
echo "++ Initializing RTC hardware clock ++"

# Bind the chip
if [ ! -e /sys/bus/i2c/devices/0-006f ]; then
    echo mcp7941x 0x6f > /sys/bus/i2c/devices/i2c-0/new_device
    echo "++ RTC chip bound to i2c-0 ++"
fi
if ! lsmod | grep -q rtc_ds1307; then
    modprobe rtc-ds1307
fi

sleep 1
# check if it worked
if [ -e /sys/bus/i2c/devices/0-006f ]; then
    echo "++ RTC hardware clock enabled ++"
else
    echo "++ ERROR: RTC hardware clock not found ++"
fi

