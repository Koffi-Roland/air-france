#!/bin/bash

if [ -z $PURGE_ON_COMPLETE ]; then
    PURGE_ON_COMPLETE=false
fi

if [ -z $FORCE_OVERWRITE ]; then
    FORCE_OVERWRITE=false
fi

echo "=============== Neo4j Restore ==============================="
echo "Beginning restore process"
echo "FORCE_OVERWRITE=$FORCE_OVERWRITE"
RESTORE_ROOT=/data/backupset
REMOTE_RESTORE_ROOT=/data/restore

if [ "$FORCE_OVERWRITE" = true ]; then
	echo "We will be force-overwriting any data present"
	FORCE_FLAG="--force"
	echo "Force flag : " $FORCE_FLAG
else
	# Pass no flag in any other setup.
	echo "We will not force-overwrite data if present"
	FORCE_FLAG=""
fi


if [ -d "/data/databases/graph.db" ]; then 
	echo "You have an existing graph database at /data/databases/graph.db" 
	if [ "$FORCE_OVERWRITE" != "true" ]; then 
		echo "And you have not specified FORCE_OVERWRITE=true, so we will not restore because" 
		echo "that would overwrite your existing data.   Exiting."
		exit 0; 
	fi 
else 
	echo "No existing graph database found at /data/databases/graph.db" 
fi

echo $RESTORE_ROOT
echo "mkdir -p "$RESTORE_ROOT""
echo "Making restore directory"
mkdir -p "$RESTORE_ROOT"

echo "Backup size pre-uncompress:"
du -hs "$RESTORE_ROOT"
ls -l "$RESTORE_ROOT"

#cp /backupdir/$FILE $RESTORE_ROOT

rm -rf $RESTORE_ROOT/*

POD=$(kubectl get pod -l app=neo4j-server-restore -o jsonpath="{.items[0].metadata.name}")

kubectl cp $POD:$REMOTE_RESTORE_ROOT $RESTORE_ROOT &

wait

VAR=0

echo "=============== Fichier reçu ==============================="

ls -l "$RESTORE_ROOT"

echo "=============== Traitement file ==============================="

for f in $RESTORE_ROOT/*
do
	if [[ -f $f ]]; then
		FILE="$f"
		echo $FILE
	
		VAR=$((VAR+1))
		if (( VAR > 1 )); then
			echo "Too much files"
			exit 0
		fi
	else 
		echo "Nothing to restore"
		exit 0
	fi
done

BACKUP_FILENAME=$(basename "$FILE")
RESTORE_FROM=uninitialized

echo "FILE : " $BACKUP_FILENAME

if [[ $BACKUP_FILENAME =~ \.tar\.gz$ ]] ; then
    echo "Untarring backup file"
	
	echo "=============== File to unzip ==============================="
	echo "cd $RESTORE_ROOT && tar --force-local --overwrite -zxvf $BACKUP_FILENAME"

	ls -l "$RESTORE_ROOT"
	
    cd "$RESTORE_ROOT" && tar --force-local --overwrite -zxvf "$BACKUP_FILENAME"

    if [ $? -ne 0 ] ; then
        echo "Failed to unarchive target backup set"
        exit 1
    fi

	echo "=============== Fichier dézippé ? ==============================="

	ls -l "$RESTORE_ROOT"

    # foo.tar.gz untars/zips to a directory called foo.
    UNTARRED_BACKUP_DIR=${BACKUP_FILENAME%.tar.gz}
    RESTORE_FROM="$RESTORE_ROOT/data/$UNTARRED_BACKUP_DIR"
	
elif [[ $BACKUP_FILENAME =~ \.zip$ ]] ; then
    echo "Unzipping backupset"
    cd "$RESTORE_ROOT" && unzip -o "$BACKUP_FILENAME"
    
    if [ $? -ne 0 ]; then 
        echo "Failed to unzip target backup set"
        exit 1
    fi

    # Remove file extension, get to directory name  
    UNZIPPED_BACKUP_DIR=${BACKUP_FILENAME%.zip}
    RESTORE_FROM="$RESTORE_ROOT/data/$UNZIPPED_BACKUP_DIR"
else
    # If user stores backups as uncompressed directories, we would have pulled down the entire directory
    echo "This backup $BACKUP_FILENAME looks uncompressed."
    RESTORE_FROM="$RESTORE_ROOT/$BACKUP_FILENAME"
fi


echo "RESTORE_FROM=$RESTORE_FROM"

echo "Set to restore from $RESTORE_FROM"
echo "Post uncompress backup size:"
ls -al "$RESTORE_ROOT"
du -hs "$RESTORE_FROM"

cd /data/databases 

echo "Dry-run command"
echo neo4j-admin restore \
	--from="$RESTORE_FROM" \
	--database=graph.db $FORCE_FLAG

echo "Volume mounts and sizing"
df -h

echo "Now restoring"
neo4j-admin restore \
	--from="$RESTORE_FROM" \
	--database=graph.db $FORCE_FLAG

echo "Rehoming database"
echo "Restored to:"
ls -l /var/lib/neo4j/data/databases


if [ -d "/data/databases/graph.db" ] ; then
   if [ "$FORCE_OVERWRITE" = "true" ] ; then
	  echo "Removing previous database because FORCE_OVERWRITE=true"
	  rm -rf /data/databases
   fi
fi

mkdir /data/databases

cp -R /var/lib/neo4j/data/databases/. /data/databases/

chmod -R 777 /data/databases
chown -R neo4j /data/databases
chgrp -R neo4j /data/databases

echo "Final permissions"
ls -al /data/databases/graph.db

echo "Final size"
du -hs /data/databases/graph.db

if [ "$PURGE_ON_COMPLETE" = true ] ; then
	echo "Purging backupset from disk"
	rm -rf $RESTORE_ROOT
	
	POD=$(kubectl get pod -l app=neo4j-server-restore -o jsonpath="{.items[0].metadata.name}")
	kubectl exec $POD -- bash -c "rm -rf $REMOTE_RESTORE_ROOT/*" &
	
	wait
fi
	
exit 0