#!/bin/sh

kubectl get pods -n $NAMESPACE -o jsonpath="{.items[*].spec.containers[*].image}" > $OUTPUT_FILE
