#!/bin/bash
clear
echo "Monitoring RSBC BI logs ..."
echo
oc logs -n iowaey-dev dc/bi-hub-apiadapter-poc -f --since=1s | cut -d':' -f 4-
