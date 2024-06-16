#!/bin/bash

while getopts 'f:opb:' flag; do
  case "${flag}" in
    f) file="${OPTARG}" ;;
    o) FORCE_OVERWRITE='true' ;;
    *) print_usage
       exit 1 ;;
  esac
done

echo $file
echo $FORCE_OVERWRITE

print_usage() {
  printf "Usage: ..."
}

# Validation of inputs upfront
if [ -z $file ]; then
    echo "You must specify file"
    exit 1
fi
	
if [ "$FORCE_OVERWRITE" = true ]; then
	echo "We will be force-overwriting any data present"
else
	# Pass no flag in any other setup.
	echo "We will not force-overwrite data if present"
fi

echo "=============== Neo4j Restore ==============================="
echo "Beginning restore process"
echo "FORCE_OVERWRITE=$FORCE_OVERWRITE"

RESTORE_ROOT=/data/backupdir
REMOTE_RESTORE_ROOT=/var/lib/neo4j/import

if [[ -f $RESTORE_ROOT/$file ]]; then
	echo "File found"
else 
	echo "File not found"
	exit 0
fi

kubectl scale statefulsets neo4j-core --replicas=0

echo "Scale down"
while [[ $(kubectl get pods -l app=neo4j -o 'jsonpath={..status.conditions[?(@.type=="Terminating")].status}') == "True" ]]
do
	echo "waiting for scale down"
	sleep 1
done

echo "Suppression pvc"
kubectl delete pvc datadir-neo4j-core-0

while [[ $(kubectl get pvc datadir-neo4j-core-0 -o 'jsonpath={..status.conditions[?(@.type=="Terminating")].status}') == "True" ]]
do
	echo "waiting for deletion"
	sleep 1
done

echo "Scale up"
kubectl scale statefulsets neo4j-core --replicas=1

while [[ $(kubectl get pods -l app=neo4j -o 'jsonpath={..status.conditions[?(@.type=="Ready")].status}') != "True" ]]
do 
	echo "waiting for pod ready"
	sleep 1
done

POD=$(kubectl get pod -l app=neo4j -o jsonpath="{.items[0].metadata.name}")

kubectl exec neo4j-core-0 -- bash -c "
	mkdir -p \"$REMOTE_RESTORE_ROOT\"
	" --REMOTE_RESTORE_ROOT=$REMOTE_RESTORE_ROOT &
	
wait

kubectl cp $RESTORE_ROOT/$file $POD:$REMOTE_RESTORE_ROOT &

wait

BACKUP_FILENAME=$(basename "$FILE")
RESTORE_FROM=uninitialized

echo "FILE : " $BACKUP_FILENAME

kubectl exec $instance -- bash -c "
	if [[ $BACKUP_FILENAME =~ \.tar\.gz$ ]]; then
		echo \"Untarring backup file\"
		cd \"$REMOTE_RESTORE_ROOT\" && tar --force-local --overwrite -zxvf \"$BACKUP_FILENAME\"

		if [ $? -ne 0 ] ; then
			echo \"Failed to unarchive target backup set\"
			exit 1
		fi
	elif [[ $BACKUP_FILENAME =~ \.zip$ ]] ; then
		echo \"Unzipping backupset\"
		cd \"$REMOTE_RESTORE_ROOT\" && unzip -o \"$BACKUP_FILENAME\"
		
		if [ $? -ne 0 ]; then 
			echo \"Failed to unzip target backup set\"
			exit 1
		fi
	fi" --BACKUP_FILENAME=$BACKUP_FILENAME --REMOTE_RESTORE_ROOT=$REMOTE_RESTORE_ROOT &
	
wait

echo "Now restoring"
kubectl exec $POD_NEO -- bin/cypher-shell --format verbose "CALL apoc.import.graphml('/$BACKUP_SET.graphml', {readLabels:true, batchSize: 10000});" &

wait

exit 0