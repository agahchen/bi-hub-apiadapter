#!/bin/bash

oc project iowaey-dev > /dev/null

echo "Before executing $0 ..."
oc get networksecuritypolicy

oc delete networksecuritypolicy bi-hub-apiadapter-poc

echo "After executing $0 ..."
oc get networksecuritypolicy
