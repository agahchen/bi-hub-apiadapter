#!/bin/bash

oc project iowaey-dev > /dev/null

echo "Before executing $0 ..."
oc get networksecuritypolicy

oc apply -f bi-hub-apiadapter-poc.yaml

echo "After executing $0 ..."
oc get networksecuritypolicy
