#!/bin/sh
builduser=$1
buildenv=$2
#mysql=`kubectl get pods -n $builduser --template '{{range .items}}{{.metadata.name}}{{""}}{{end}}' | grep mysql${buildenv}`
POD=$(kubectl get pod -l app=mysql$buildenv -n user1 -o jsonpath="{.items[0].metadata.name}")
echo "${POD}"
kubectl exec -i "$POD" -n "$builduser" -- mysql -u root -ppassword < empreferal.sql