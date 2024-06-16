#!/bin/bash
# Batch java purge Individual and GDPR

echo "Start Purge Individual + GDPR"

echo "Starting Purge GDPR"

/app/$FS_DIR/prod/bin/java/BatchPurgeIndividusGDPR.sh $@
batch_exit_GDPR=$?

echo "Ending Purge GDPR ${batch_exit_GDPR}"

echo "Starting Purge Individual"

/app/$FS_DIR/prod/bin/java/BatchPurgeIndividus.sh $@
batch_exit=$?

echo "Ending Purge Individual ${batch_exit}"

if (( batch_exit_GDPR == 1 || batch_exit == 1 )); then
        exit 1
fi

exit 0

echo "End Purge Individual + GDPR"
