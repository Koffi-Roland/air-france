#!/bin/sh

source ~/.profile

# Define target project
project=${app.name}

# Process for RAP delivery
if [[ $project == "RAP" ]]
then
	mkdir -p /app/$FS_DIR/ftp
	mkdir -p /app/$FS_DIR/data/BSP
	mkdir -p /app/$FS_DIR/data/AGENCES/IATA_AIRPORT_CODE_UPDATE
	mkdir -p /app/$FS_DIR/data/HISTO
else
	# Process for REPIND delivery
	if [[ $project == "REPIND" ]]
	then
		mkdir -p /app/$FS_DIR/ftp
		mkdir -p /app/$FS_DIR/ftp/SFMC/WW
		chmod 777 /app/$FS_DIR/ftp/SFMC/WW
		mkdir -p /app/$FS_DIR/data/EXTERNAL_IDENTIFIER
		mkdir -p /app/$FS_DIR/data/EVENT_NOTIFICATIONS
		mkdir -p /app/$FS_DIR/data/EVENT_NOTIFICATIONS_SECURITY
		mkdir -p /app/$FS_DIR/data/NEW_PHONE_NUMBER/DAILY/
		mkdir -p /app/$FS_DIR/data/NEW_PHONE_NUMBER/DAILY/CLEAN
		mkdir -p /app/$FS_DIR/data/NEW_PHONE_NUMBER/DAILY/NORMALISATION
		mkdir -p /app/$FS_DIR/data/NEW_PHONE_NUMBER/DAILY/INVALIDATION
		mkdir -p /app/$FS_DIR/data/NEW_PHONE_NUMBER/DAILY/NORMINVAL
		mkdir -p /app/$FS_DIR/data/NEW_PHONE_NUMBER/DAILY/old
		mkdir -p /app/$FS_DIR/data/PREFILLED_NUMBER
		mkdir -p /app/$FS_DIR/data/PROSPECT/processed
		mkdir -p /app/$FS_DIR/data/PROSPECT/WW/processed
		mkdir -p /app/$FS_DIR/data/QUALIF_ADRESSES/CRMPUSH_R3
		mkdir -p /app/$FS_DIR/prod/logs/traces_batch_java
		mkdir -p /app/$FS_DIR/data/INJEST_ADHOC_DATA/processed
		mkdir -p /app/$FS_DIR/ftp/INJEST_ADHOC_DATA

		rm -f /app/$FS_DIR/data/REPLICATION_GENERIQUE/GLOBAL/GLOBAL-202108150100.tar.gz
		rm -f /app/$FS_DIR/data/REPLICATION_GENERIQUE/GLOBAL/GLOBAL-202207170100.tar.gz
		rm -f /app/$FS_DIR/data/REPLICATION_GENERIQUE/GLOBAL/GLOBAL-202208210100.tar.gz

	else 
		# Process for REPFIRM delivery
		if [[ $project == "REPFIRM" ]]
		then
			mkdir -p /app/$FS_DIR/ftp
			mkdir -p /app/$FS_DIR/data/BDC_LOAD/HISTO
			mkdir -p /app/$FS_DIR/data/EVENT_NOTIFICATIONS
			mkdir -p /app/$FS_DIR/data/EVENT_NOTIFICATIONS_SECURITY
			mkdir -p /app/$FS_DIR/data/PURGE
			mkdir -p /app/$FS_DIR/prod/logs/purge_firme
		fi
	fi
fi

# Clean CPP lib directory
# rm -rf /app/$FS_DIR/prod/bin/cpp/lib/*

# Clean data directory from corrupted file

rm /app/REPIND/data/DETECT_DUPLICATES/detect_duplicates_result_04012024.csv
rm /app/REPIND/data/DETECT_DUPLICATES/detect_duplicates_result_02012024.csv
rm /app/REPIND/data/DETECT_DUPLICATES/detect_duplicates_result_01012024.csv
rm /app/REPIND/data/DETECT_DUPLICATES/detect_duplicates_result_31122023.csv
rm /app/REPIND/data/DETECT_DUPLICATES/detect_duplicates_result_30122023.csv
rm /app/REPIND/data/DETECT_DUPLICATES/detect_duplicates_result_29122023.csv

exit 0
