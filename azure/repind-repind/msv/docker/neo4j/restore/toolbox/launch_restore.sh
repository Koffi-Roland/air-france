#!/bin/bash

file=$1
RESTORE_ROOT=$2
REMOTE_RESTORE_ROOT=$3

exit 2

echo "=============== Step 1/7 ==============================="
echo "Step 1/7 : Scale down neo4j pod"

kubectl scale statefulsets neo4j-core --replicas=0

exitCode=$?
if [ $exitCode -ne 0 ]; then
	exit $exitCode
else
	echo "Step 1/7 : Waiting for scale down neo4j pod"
fi

timeout 180 bash -c -- "while [[ ! -z $(kubectl get pods -l app=neo4j) ]]
do
	echo \"Step 1/7 : Waiting for scale down\"
	sleep 1
done"
exitCode=$?
if [ $exitCode -ne 0 ]; then
	echo "Step 1/7 : ERROR too long to scale down"
	exit $exitCode
else
	echo "Step 1/7 : Scale down neo4j pod done"
fi

echo "=============== Step 2/7 ==============================="
echo "Step 2/7 : Remove neo4j volume (pvc)"

kubectl delete pvc datadir-neo4j-core-0

exitCode=$?
if [ $exitCode -ne 0 ]; then
	exit $exitCode
else
	echo "Step 2/7 : Waiting for pvc deletion"
fi

timeout 180 bash -c -- "while [[ ! -z $(kubectl get pvc datadir-neo4j-core-0) ]]
do
	echo \"Step 2/7 : Waiting for pvc deletion\"
	sleep 1
done"
exitCode=$?
if [ $exitCode -ne 0 ]; then
	echo "Step 2/7 : ERROR too long to delete"
	exit $exitCode
else
	echo "Step 2/7 : Pvc deletion done"
fi

echo "=============== Step 3/7 ==============================="
echo "Step 3/7 : Scale up neo4j pod"

kubectl scale statefulsets neo4j-core --replicas=1

exitCode=$?
if [ $exitCode -ne 0 ]; then
	exit $exitCode
else
	echo "Step 3/7 : Waiting for neo4j pod ready"
fi

timeout 300 bash -c -- "while [[ $(kubectl get pods -l app=neo4j -o 'jsonpath={..status.conditions[?(@.type=="Ready")].status}') != \"True\" ]]
do 
	echo \"Step 3/7 : Waiting for pod ready\"
	sleep 2
done"
exitCode=$?
if [ $exitCode -ne 0 ]; then
	echo "Step 3/7 : ERROR too long to be ready"
	exit $exitCode
else
	echo "Step 3/7 : Pod ready"
fi

echo "=============== Step 4/7 ==============================="
echo "Step 4/7 : Get instance of the server and create directory if not exists"

instance=$(kubectl get pod -l app=neo4j -o jsonpath="{.items[0].metadata.name}")
kubectl exec $instance -- bash -c "
	mkdir -p \"$REMOTE_RESTORE_ROOT\"
	" --REMOTE_RESTORE_ROOT=$REMOTE_RESTORE_ROOT &
wait

exitCode=$?
if [ $exitCode -ne 0 ]; then
	exit $exitCode
else
	echo "Step 4/7 : Connexion to neo4j pod done and directory created"
fi

echo "=============== Step 5/7 ==============================="
echo "Step 5/7 : Copy backup file to neo4j pod"

kubectl cp $RESTORE_ROOT/$file $instance:$REMOTE_RESTORE_ROOT &
wait

exitCode=$?
if [ $exitCode -ne 0 ]; then
	exit $exitCode
else
	echo "Step 5/7 : File transfer done"
fi

echo "=============== Step 6/7 ==============================="

BACKUP_FILENAME=$(basename "$file")
echo "Step 6/7 : File processing : " $BACKUP_FILENAME
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

exitCode=$?
if [ $exitCode -ne 0 ]; then
	exit $exitCode
else
	echo "Step 6/7 : Untar/Unzip done"
fi

echo "=============== Step 7/7 ==============================="
echo "Step 7/7 : Start restore"
BASENAME_TAR="${BACKUP_FILENAME%.*}"
FILE_BASENAME="${BASENAME_TAR%.*}"

until kubectl exec $instance -- bash -c "
bin/cypher-shell < \"import/$FILE_BASENAME.cypher\" > \"output_restore_$FILE_BASENAME\"
"
do 
	echo "Step 7/7 : Waiting for neo4j shell"
	sleep 1
done

exitCode=$?
if [ $exitCode -ne 0 ]; then
	exit $exitCode
else
	echo "Step 7/7 : Restore complete on neo4j"
	exit 0
fi
