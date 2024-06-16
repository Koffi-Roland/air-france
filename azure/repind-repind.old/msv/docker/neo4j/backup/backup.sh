#!/bin/bash

if [ -z $HEAP_SIZE ] ; then
    export HEAP_SIZE=2G
fi

if [ -z $PAGE_CACHE ]; then
    export PAGE_CACHE=4G
fi

if [ -z $BACKUP_NAME ]; then
    export BACKUP_NAME=graph.db-backup
fi

BACKUP_SET="$BACKUP_NAME-$(date "+%Y-%m-%d-%H%M%S")"
REMOTE_RESTORE_ROOT=/data/backupdir
RESTORE_ROOT=/var/lib/neo4j/import

echo "=============== Neo4j Backup ==============================="
echo "Beginning backup"
echo "============================================================"
	
POD_NEO=$(kubectl get pod -l app=neo4j -o jsonpath="{.items[0].metadata.name}")

kubectl exec $POD_NEO -- bin/cypher-shell --format verbose "call apoc.export.cypher.all('$BACKUP_SET.cypher',{format:'cypher-shell', useOptimizations: {type: 'unwind_batch', unwindBatchSize: 20}, batchSize:100});" &

wait

echo "Backup size:"
kubectl exec $POD_NEO -- bash -c "
	cd $RESTORE_ROOT
	
	du -hs

	echo \"Tarring -> /data/$BACKUP_SET.tar\"
	tar -cvf \"$BACKUP_SET.tar\" \"$BACKUP_SET.cypher\" --remove-files

	echo \"Zipping -> /data/$BACKUP_SET.tar.gz\"
	gzip -9 \"$BACKUP_SET.tar\"

	echo \"Zipped backup size:\"
	du -hs \"$BACKUP_SET.tar.gz\"

	echo \"Files list:\"
	ls -la
" --RESTORE_ROOT=$RESTORE_ROOT --BACKUP_SET=$BACKUP_SET &

wait

POD_SERVER=$(kubectl get pod -l app=neo4j-toolbox -o jsonpath="{.items[0].metadata.name}")

kubectl cp $POD_NEO:$RESTORE_ROOT/$BACKUP_SET.tar.gz /data/$BACKUP_SET.tar.gz &

wait

echo "Get files:"
ls -la /data

kubectl cp /data/$BACKUP_SET.tar.gz $POD_SERVER:$REMOTE_RESTORE_ROOT/$BACKUP_SET.tar.gz &

wait

echo "Clean files"

kubectl exec $POD_SERVER -- bash -c "find $REMOTE_RESTORE_ROOT -type f -mtime +3 -exec rm -f {} \;" --REMOTE_RESTORE_ROOT=$REMOTE_RESTORE_ROOT &

wait

echo "Keep only 2 files by day"

kubectl exec $POD_SERVER -- bash -c "
find $REMOTE_RESTORE_ROOT -type f -mtime -3 -mtime +0 -print0 | while read -d $'\0' file
do
	BASENAME_TAR=\"${file%.*}\"
	FILE_BASENAME=\"${BASENAME_TAR%.*}\"
	time=$(echo $FILE_BASENAME | cut -f6 -d-)
	hour=${time:0:2}
	if [ $((10#$hour)) -eq 12 ] || [ $((10#$hour)) -eq 00 ]; then
		echo \"keep $f\"
	else 
		echo \"not keep $f\"
		rm $REMOTE_RESTORE_ROOT/$f
	fi
done" --REMOTE_RESTORE_ROOT=$REMOTE_RESTORE_ROOT &

wait

echo "Clean files on neo4j server"

kubectl exec $POD_NEO -- bash -c "find import -type f -mtime +0 -exec rm -f {} \;" &

wait

echo "Clean files on backup volume"

find /data -type f -mtime -1 -exec rm -f {} \;

exit $?
