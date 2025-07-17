#!/bin/bash
SWAPFILE="/mnt/data/swapfile"
SWAPSIZE=1024  

if [ ! -f "$SWAPFILE" ]; then
    echo "Creating swap file..."
    fallocate -l ${SWAPSIZE}M "$SWAPFILE"
    chmod 600 "$SWAPFILE"
    mkswap "$SWAPFILE"
fi

if ! swapon -s | grep -q "$SWAPFILE"; then
    echo "Enabling swap..."
    swapon "$SWAPFILE"
fi