#!/bin/bash

echo "Before executing $0 ..."
oc get -n iowaey-dev networksecuritypolicy | grep bi-hub-apiadapter-poc

oc delete -n iowaey-dev networksecuritypolicy bi-hub-apiadapter-poc 2> /dev/null

policy=`oc get -n iowaey-dev networksecuritypolicy | grep bi-hub-apiadapter-poc`

while [ ${#policy} -gt 0 ]; do
	echo "Waiting for 5 seconds ..."
	sleep 5
	policy=`oc get -n iowaey-dev networksecuritypolicy | grep bi-hub-apiadapter-poc`
done

echo "After executing $0 ..."
oc get -n iowaey-dev networksecuritypolicy | grep bi-hub-apiadapter-poc
