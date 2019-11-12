#!/bin/bash

echo "Before executing $0 ..."
oc get -n iowaey-dev networksecuritypolicy | grep bi-hub-apiadapter-poc

oc apply -n iowaey-dev -f bi-hub-apiadapter-poc.yaml

policy=`oc get -n iowaey-dev networksecuritypolicy | grep bi-hub-apiadapter-poc`

while [ ${#policy} -le 0 ]; do
	echo "Waiting for 5 seconds ..."
	sleep 5
	policy=`oc get -n iowaey-dev networksecuritypolicy | grep bi-hub-apiadapter-poc`
done

echo "After executing $0 ..."
oc get -n iowaey-dev networksecuritypolicy | grep bi-hub-apiadapter-poc
