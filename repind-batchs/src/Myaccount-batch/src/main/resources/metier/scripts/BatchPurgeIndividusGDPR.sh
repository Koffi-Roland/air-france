#!/bin/bash
# Batch java purge Individual GDPR - 2018
# Purge GDPR Xavier Bontemps

echo "Purge Individual Forgotten (GDPR)"

host=$(hostname)

JAVA_SIC=/app/$FS_DIR/prod/bin/java
JAVA_HOME=/tech/java/openjdk/1.17
JAVA17_ARG="--add-opens=java.base/java.lang=ALL-UNNAMED --add-opens=java.management/javax.management.openmbean=ALL-UNNAMED --add-opens=java.management/javax.management=ALL-UNNAMED --add-opens=java.base/java.lang.reflect=ALL-UNNAMED"
BATCH_PKG=com.airfrance.jraf.batch.individu
CLASSPATH_SIC=${JAVA_SIC}/lib/*
memory=1G

dateTraitement=`date '+%Y%m%d%H%M'`

# Detection Flux Production ou Recette
# RCT = TFGPUR
# PRD = PFGPUR
environnement=$hostType
if [ $environnement = "prd" ] 
then
	FLUX_PURGE_F_OUT=PFGPUR
elif [ $environnement = "rct" ]
then
	FLUX_PURGE_F_OUT=TFGPUR
elif [ $environnement = "rc2" ]
then
	FLUX_PURGE_F_OUT=TFGPUR
else
    echo "Pas de flux defini pour cet environnement : <$environnement>" 
fi

environnement=`echo ${environnement} | tr [a-z] [A-Z]`

##############################
# PURGE INDIVIDUAL FORGOTTEN #
##############################

# Create PURGE GDPR folder if does not exist
if [[ ! -d "/app/$FS_DIR/data/PURGE_GDPR" ]]
then
    mkdir /app/$FS_DIR/data/PURGE_GDPR
fi

TRACE_FILE_PURGE_GDPR=/app/$FS_DIR/data/PURGE_GDPR/"PURGE_INDIVIDUAL_GDPR_"$dateTraitement.trace

echo `date '+%Y-%m-%d %H:%M:%S'`" - Starting Purge GDPR" >>$TRACE_FILE_PURGE_GDPR
batch_result=$(${JAVA_HOME}/bin/java -Dbatch=BatchPurgeIndividualGDPR -Djava.security.krb5.kdc=$KDCSERVER -Djava.security.krb5.realm=$REALMENV \
-Djava.security.auth.login.config=$JAASCONF -Dsun.security.krb5.debug=false -Dzookeeper.sasl.client=false \
${JAVA17_ARG} -server -Xms${memory} -Xmx${memory} -cp ${CLASSPATH_SIC}:${JAVA_SIC}/Myaccount-batch-0.1.0-SNAPSHOT.jar ${BATCH_PKG}.BatchPurgeIndividualGDPR "$@" 2>&1 )
batch_exit_GDPR=$?
echo "Batch Exit result: $batch_exit_GDPR" >>$TRACE_FILE_PURGE_GDPR

if (( batch_exit_GDPR == 1 )); then
        email_content="Environment: $FS_DIR""\n"
        email_content="$email_content""\n""Hostname: ""$host""\n"
        email_content="$email_content""\n""ERROR: $batch_exit_GDPR""\n"
		email_content="$email_content""\n""$batch_result""\n"
        echo -e "$email_content" | mailx -s "$FS_DIR $environnement [FATAL!] Individual GDPR Purge" "mail.sic.it@airfrance.fr"
		exit 1
fi

echo `date '+%Y-%m-%d %H:%M:%S'`" - Process Purge GDPR done" >>$TRACE_FILE_PURGE_GDPR

fileExist=`ls /app/$FS_DIR/data/PURGE_GDPR/*_deleted_fgt.gz 2>>/dev/null`
if [[ ! -e "$fileExist" ]]
then
    email_content="Environment: $FS_DIR""\n"
	email_content="$email_content""\n""Hostname: ""$host""\n"
	email_content="$email_content""\n""No individuals GDPR to be purged found.""\n"
	echo -e "$email_content" | mailx -s "$FS_DIR $environnement [INFO] Individual Purge GDPR" "mail.sic.it@airfrance.fr"
	echo `date '+%Y-%m-%d %H:%M:%S'`" - Ending Purge Individual GDPR with no Individuals found" >>$TRACE_FILE_PURGE_GDPR
	exit 0 
fi

# Send list of purged GDPR gins to XFiles
echo `date '+%Y-%m-%d %H:%M:%S'`" - List of purged gins will be sent to: $FLUX_PURGE_F_OUT" >>$TRACE_FILE_PURGE_GDPR
deletedGinsGDPR=`ls /app/$FS_DIR/data/PURGE_GDPR/*_deleted_fgt.gz 2>>/dev/null`

for myFile in $deletedGinsGDPR; do
	#REPIND-1668: Replace timestamp by DDMMYYYYHHMM before sending to xfiles
	OUTPUT_FILENAME=${myFile##*/}
	OUTPUT_FILENAME=${OUTPUT_FILENAME#*_}
	OUTPUT_FILENAME=`date '+%d%m%Y%H%M'`"_$OUTPUT_FILENAME"
	
	echo `date '+%Y-%m-%d %H:%M:%S'`" - Sending file $myFile ($OUTPUT_FILENAME) to flux $FLUX_PURGE_F_OUT" >>$TRACE_FILE_PURGE_GDPR
	
	$XFB/gwqvi1.sh $myFile $FLUX_PURGE_F_OUT $OUTPUT_FILENAME 2>>$TRACE_FILE_PURGE_GDPR
	
	xfilesGDPRCode=$?
	
	if [[ "$xfilesGDPRCode" -ne "0" ]]
	then
		filesGDPRNotSent="$filesGDPRNotSent""$myFile($xfilesGDPRCode);"
		
		echo `date '+%Y-%m-%d %H:%M:%S'`" - File $myFile sent failed with code error $xfilesGDPRCode" >>$TRACE_FILE_PURGE_GDPR
	else
		filesGDPRSent="$filesGDPRSent""$myFile;"
		
		echo `date '+%Y-%m-%d %H:%M:%S'`" - File $myFile sent with success" >>$TRACE_FILE_PURGE_GDPR
	fi
	
done

deletedGins=`zgrep -Ec "$" /app/$FS_DIR/data/PURGE_GDPR/*_deleted_fgt.gz 2>>/dev/null`

# Create BACKUP GDPR folder if does not exist
if [[ ! -d "/app/$FS_DIR/data/PURGE_GDPR/BACKUP" ]]
then
    mkdir /app/$FS_DIR/data/PURGE_GDPR/BACKUP 2>>$TRACE_FILE_PURGE_GDPR
fi

# Create dedicated GDPR folder for backup
folderBackupPurgeIndividualGDPR=/app/$FS_DIR/data/PURGE_GDPR/BACKUP/$dateTraitement

if [[ ! -d "$folderBackupPurgeIndividualGDPR" ]]
then
    mkdir $folderBackupPurgeIndividualGDPR 2>>$TRACE_FILE_PURGE_GDPR
	
	# Backup of report files
	reportFiles=`ls /app/$FS_DIR/data/PURGE_GDPR/*.gz 2>>/dev/null`

	for myFile in $reportFiles; do
		echo `date '+%Y-%m-%d %H:%M:%S'`" - Backup file $myFile" >>$TRACE_FILE_PURGE_GDPR
		mv $myFile $folderBackupPurgeIndividualGDPR/. 2>>$TRACE_FILE_PURGE_GDPR
	done
	
fi

echo `date '+%Y-%m-%d %H:%M:%S'`" - Ending Purge GDPR" >>$TRACE_FILE_PURGE_GDPR


if [[ -z ${filesGDPRNotSent} ]]
then
	labelSubject="INFO";
else
	labelSubject="WARNING";
fi
email_content="Environment: $FS_DIR""\n"
email_content="$email_content""\n""Hostname: ""$host""\n\n"
email_content="$email_content""XFiles report:""\n"
if [[ ! -z ${filesGDPRSent} ]]
then
	email_content="$email_content""   *** Files PURGE GDPR sent: ""$filesGDPRSent""\n"
fi
if [[ ! -z ${filesGDPRNotSent} ]]
then
	email_content="$email_content""   *** Files PURGE GDPR not sent: ""$filesGDPRNotSent -> to be sent manually""\n"
fi
email_content="$email_content""\n""Result of individuals GDPR purged: ${deletedGins} (Deleted)""\n" 

echo -e "$email_content" | mailx -s "$FS_DIR $environnement [$labelSubject] Individual Purge GDPR - ${deletedGins} (Deleted)" "mail.sic.it@airfrance.fr"
 
echo `date '+%Y-%m-%d %H:%M:%S'`" - Ending Purge GDPR Individual" >>$TRACE_FILE_PURGE_GDPR

exit 0
