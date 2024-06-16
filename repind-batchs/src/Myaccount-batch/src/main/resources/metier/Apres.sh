#!/bin/bash

# Define target project
project=${app.name}

# Process for RAP delivery
if [[ $project == "RAP" ]]
then
	mkdir -p /app/$FS_DIR/ftp
	mkdir -p /app/$FS_DIR/data/BSP
	mkdir -p /app/$FS_DIR/data/AGENCES/IATA_AIRPORT_CODE_UPDATE
else
	# Process for REPIND delivery
	if [[ $project == "REPIND" ]]
	then
		mkdir -p /app/$FS_DIR/ftp
		mkdir -p /app/$FS_DIR/data/EXTERNAL_IDENTIFIER
		mkdir -p /app/$FS_DIR/data/EVENT_NOTIFICATIONS
		mkdir -p /app/$FS_DIR/data/EVENT_NOTIFICATIONS_SECURITY
		mkdir -p /app/$FS_DIR/data/PREFILLED_NUMBER
		mkdir -p /app/$FS_DIR/data/PROSPECT
		mkdir -p /app/$FS_DIR/data/QUALIF_ADRESSES/CRMPUSH_R3
		mkdir -p /app/$FS_DIR/prod/logs/traces_batch_java
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

exit 0