#!/bin/bash
# Batch java purge Individual - 2018
# V1 - Manual Purge by Didier Martini
# V2 - Automatique Purge with OCP by Michael Zangarell

echo "**************** Start Purge Individual ****************"

host=$(hostname)

date_limit=$(date +%d/%m/%Y -d '14 days')
date_purge=$(date +%d/%m/%Y -d '15 days')
JAVA_SIC=/app/$FS_DIR/prod/bin/java
JAVA_HOME=/tech/java/openjdk/1.17
JAVA17_ARG="--add-opens=java.base/java.lang=ALL-UNNAMED --add-opens=java.management/javax.management.openmbean=ALL-UNNAMED --add-opens=java.management/javax.management=ALL-UNNAMED --add-opens=java.base/java.lang.reflect=ALL-UNNAMED"
BATCH_PKG=com.afklm.batch.individu.purge
CLASSPATH_SIC=${JAVA_SIC}/lib/*
memory=3G

emailDest="mail.sic.it@airfrance.fr"

dateTraitement=`date '+%Y%m%d%H%M'`

# Detection Flux Production ou Recette
# RCT = TNLPUR
# PRD = PNLPUR
environnement=$hostType
if [ $environnement = "prd" ] 
then
    FLUX_PURGE_OUT=PNLPUR
elif [ $environnement = "rct" ]
then
    FLUX_PURGE_OUT=TNLPUR
elif [ $environnement = "rc2" ]
then
    FLUX_PURGE_OUT=TNLPUR
else
    echo "Pas de flux defini pour cet environnement : <$environnement>" 
fi

environnement=`echo ${environnement} | tr [a-z] [A-Z]`

# Create PURGE folder if does not exist
if [[ ! -d "/app/$FS_DIR/data/PURGE" ]]
then
    mkdir /app/$FS_DIR/data/PURGE
fi

TRACE_FILE_PURGE=/app/$FS_DIR/data/PURGE/"PURGE_INDIVIDUAL_"$dateTraitement.trace

echo `date '+%Y-%m-%d %H:%M:%S'`" - Starting Process Purge" >>$TRACE_FILE_PURGE

# Remove report file before recreating it
if [[ -e "/app/$FS_DIR/data/PURGE/report" ]]
then
    rm /app/$FS_DIR/data/PURGE/report
fi

# Run Purge Individual
set -o pipefail
batch_result=$(${JAVA_HOME}/bin/java -Dbatch=BatchPurgeIndividual -Djava.security.krb5.kdc=$KDCSERVER -Djava.security.krb5.realm=$REALMENV \
-Djava.security.auth.login.config=$JAASCONF -Dsun.security.krb5.debug=false -Dzookeeper.sasl.client=false \
${JAVA17_ARG} -server -Xms${memory} -Xmx${memory} -cp ${CLASSPATH_SIC}:${JAVA_SIC}/individu-purge-0.0.1-SNAPSHOT.jar ${BATCH_PKG}.BatchPurgeIndividual "$@" 2>&1 )
batch_exit=$?
echo "Batch Exit result: $batch_result" >>$TRACE_FILE_PURGE
echo "Batch Exit code: $batch_exit" >>$TRACE_FILE_PURGE

echo `date '+%Y-%m-%d %H:%M:%S'`" - Process Purge Done" >>$TRACE_FILE_PURGE

# Code for printing help, all fine, do nothing (never called by Control M)
if (( batch_exit == 2 )); then
	echo `date '+%Y-%m-%d %H:%M:%S'`" - Ending Purge Individual Printing Help" >>$TRACE_FILE_PURGE
    exit 0
fi

# Error code
if (( batch_exit == 1 )); then
        email_content="Environment: $FS_DIR""\n"
        email_content="$email_content""\n""Hostname: ""$host""\n"
        email_content="$email_content""\n""ERROR: $batch_exit""\n"
        email_content="$email_content""\n""$batch_result""\n"
        echo -e "$email_content" | mailx -s "$FS_DIR $environnement [FATAL!] Individual Purge" "${emailDest}"
		echo `date '+%Y-%m-%d %H:%M:%S'`" - Ending Purge Individual in ERROR" >>$TRACE_FILE_PURGE
		exit 1
fi

result=$(cat /app/$FS_DIR/data/PURGE/report)

IFS=';' read -r -a files <<< "$result"
individual_to_be_purged=${files[0]}
individual_purged=${files[1]}
purge_errors=${files[2]}
send_mail=2

if [ -n "$purge_errors" ]; then
	email_content="Environment: $FS_DIR""\n"
	email_content="$email_content""\n""Hostname: ""$host""\n"
	email_content="$email_content""\n""ERROR: $purge_errors""\n"
	echo -e "$email_content" | mailx -s "$FS_DIR $environnement [ERROR] Individual Purge" "${emailDest}"
fi

if [ -z "$individual_to_be_purged" ]; then
	individual_to_be_purged="No individual are purgeable"
	send_mail=$((send_mail-1))
fi
if [ -z "$individual_purged" ]; then
	individual_purged="No individual was purged"
	send_mail=$((send_mail-1))
fi

if (( send_mail == 0 )); then
	email_content="Environment: $FS_DIR""\n"
	email_content="$email_content""\n""Hostname: ""$host""\n"
	email_content="$email_content""\n""No individuals to be purged found.""\n"
	echo -e "$email_content" | mailx -s "$FS_DIR $environnement [INFO] Individual Purge" "${emailDest}"
	echo `date '+%Y-%m-%d %H:%M:%S'`" - Ending Purge Individual with no Individuals found" >>$TRACE_FILE_PURGE
	exit 0 
fi

# Send list of purged gins to XFiles
echo `date '+%Y-%m-%d %H:%M:%S'`" - List of purged gins will be sent to: $FLUX_PURGE_OUT" >>$TRACE_FILE_PURGE

deletedGins=`ls /app/$FS_DIR/data/PURGE/*_deleted.gz 2>>/dev/null`

# For loop in case of backup (mv) has failed. In this case we will have several deleted.gz
for myFile in $deletedGins; do
	#REPIND-1668: Replace timestamp by DDMMYYYYHHMM before sending to xfiles
	OUTPUT_FILENAME=${myFile##*/}
	OUTPUT_FILENAME=${OUTPUT_FILENAME#*_}
	OUTPUT_FILENAME=`date '+%d%m%Y%H%M'`"_$OUTPUT_FILENAME"

	echo `date '+%Y-%m-%d %H:%M:%S'`" - Sending file $myFile ($OUTPUT_FILENAME) to flux $FLUX_PURGE_OUT" >>$TRACE_FILE_PURGE
	
	$XFB/gwqvi1.sh $myFile $FLUX_PURGE_OUT $OUTPUT_FILENAME 2>>$TRACE_FILE_PURGE
	
	xfilesCode=$?
	
	if [[ "$xfilesCode" -ne "0" ]]
	then
		filesNotSent="$filesNotSent""$myFile($xfilesCode);"
		
		echo `date '+%Y-%m-%d %H:%M:%S'`" - File $myFile sent failed with code error $xfilesCode" >>$TRACE_FILE_PURGE
	else
		filesSent="$filesSent""$myFile;"
		
		echo `date '+%Y-%m-%d %H:%M:%S'`" - File $myFile sent with success" >>$TRACE_FILE_PURGE
	fi
done

selectedGins="???"
deletedGins="???"
selectedGins=`zgrep -Ec "$" /app/$FS_DIR/data/PURGE/*_selected.gz 2>>/dev/null`
deletedGins=`zgrep -Ec "$" /app/$FS_DIR/data/PURGE/*_deleted.gz 2>>/dev/null`


# Create BACKUP folder if does not exist
if [[ ! -d "/app/$FS_DIR/data/PURGE/BACKUP" ]]
then
	mkdir /app/$FS_DIR/data/PURGE/BACKUP 2>>$TRACE_FILE_PURGE
fi

# Create dedicated folder for backup
folderBackupPurgeIndividual=/app/$FS_DIR/data/PURGE/BACKUP/$dateTraitement

if [[ ! -d "$folderBackupPurgeIndividual" ]]
then
	mkdir $folderBackupPurgeIndividual 2>>$TRACE_FILE_PURGE
	
	# Backup of report files
	reportFiles=`ls /app/$FS_DIR/data/PURGE/*.gz 2>>/dev/null`

	for myFile in $reportFiles; do
		echo `date '+%Y-%m-%d %H:%M:%S'`" - Backup file $myFile" >>$TRACE_FILE_PURGE
		mv $myFile $folderBackupPurgeIndividual/. 2>>$TRACE_FILE_PURGE
	done
	
fi

# If xfiles flux has failed, email will be a WARNING, else INFO
if [[ -z ${filesNotSent} ]]
then
	labelSubject="INFO"
else
	labelSubject="WARNING"
fi

maximumDelete="???"
maximumDelete=`echo $@ | sed -rn 's/.*maximum-number-delete.([[:digit:]]+).*/\1 /p' 2>>/dev/null`

email_content="Environment: $FS_DIR""\n"
email_content="$email_content""\n""Hostname: ""$host""\n\n"
email_content="$email_content""XFiles report:""\n"
if [[ ! -z ${filesSent} ]]
then
	email_content="$email_content""   *** Files PURGE sent: ""$filesSent""\n"
fi
if [[ ! -z ${filesNotSent} ]]
then
	email_content="$email_content""   *** Files PURGE not sent: ""$filesNotSent -> to be sent manually""\n"
fi
email_content="$email_content""\n""Result of individuals purged: ${deletedGins} (Deleted) / ${selectedGins} (Selected) - (Expected: ${maximumDelete})""\n" 

echo -e "$email_content" | mailx -s "$FS_DIR $environnement [$labelSubject] Individual Purge - ${deletedGins} (Deleted)" "${emailDest}"

echo `date '+%Y-%m-%d %H:%M:%S'`" - Ending Process Purge" >>$TRACE_FILE_PURGE
 
echo "**************** End Purge Individual ****************"

exit 0
