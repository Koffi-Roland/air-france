#!/bin/bash

while getopts 'f:opb:' flag; do
  case "${flag}" in
    f) file="${OPTARG}" ;;
    *) print_usage
       exit 1 ;;
  esac
done

echo $file

print_usage() {
  printf "Usage: ..."
}

# Validation of inputs upfront
if [ -z $file ]; then
    echo "You must specify file"
	echo "Restore failed"
    exit 1
fi

echo "=============== Neo4j Restore ==============================="
echo "Beginning restore process"

RESTORE_ROOT=/data/backupdir
REMOTE_RESTORE_ROOT=/var/lib/neo4j/import


if [[ -f $RESTORE_ROOT/$file ]]; then
	echo "File found"
else 
	echo "File not found"
	echo "Restore failed"
	exit 1
fi

/scripts/launch_restore.sh "$file" "$RESTORE_ROOT" "$REMOTE_RESTORE_ROOT"

if [ $? -ne 0 ]; then
	echo "Restore failed"
else
	echo "Restore done"
fi

exit 0