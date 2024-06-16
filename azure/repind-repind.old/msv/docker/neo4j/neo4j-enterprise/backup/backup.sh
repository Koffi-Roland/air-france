#!/bin/bash

if [ -z $NEO4J_ADDR ] ; then
    echo "You must specify a NEO4J_ADDR env var"
    exit 1
fi

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

echo "=============== Neo4j Backup ==============================="
echo "Beginning backup from $NEO4J_ADDR to /data/$BACKUP_NAME"
echo "Using heap size $HEAP_SIZE and page cache $PAGE_CACHE"
echo "============================================================"

neo4j-admin backup \
    --from="$NEO4J_ADDR" \
    --backup-dir=/data \
    --name="$BACKUP_NAME" \
    --pagecache=$PAGE_CACHE

echo "Backup size:"
du -hs "/data/$BACKUP_NAME"

echo "Tarring -> /data/$BACKUP_NAME.tar"
tar -cvf "/backup-save/$BACKUP_SET.tar" "/data/$BACKUP_NAME"

echo "Zipping -> /data/$BACKUP_SET.tar.gz"
gzip -9 "/backup-save/$BACKUP_SET.tar"

echo "Zipped backup size:"
du -hs "/backup-save/$BACKUP_SET.tar.gz"

echo "Files list:"
ls /data

POD=$(kubectl get pod -l app=neo4j-server-restore -o jsonpath="{.items[0].metadata.name}")

kubectl cp /data/$BACKUP_SET.tar.gz $POD:$REMOTE_RESTORE_ROOT/$BACKUP_SET.tar.gz &

wait

kubectl exec $POD -- bash -c "find /data/backupdir -type f -mtime +10 -exec rm -f {} \;" &

wait

find /data -type f -mtime +10 -exec rm -f {} \;

exit $?