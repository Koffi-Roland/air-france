#!/bin/bash

#Positionnement des variables de la VM
JAVA_HOME=/tech/java/openjdk/1.17
JAVA_RPD=/app/REPIND/prod/bin/tcsic/java
JAVA17_ARG="--add-opens=java.base/java.lang=ALL-UNNAMED --add-opens=java.management/javax.management.openmbean=ALL-UNNAMED --add-opens=java.management/javax.management=ALL-UNNAMED --add-opens=java.base/java.lang.reflect=ALL-UNNAMED"
BATCH_PKG=com.airfrance.batch.adrInvalidBarecode
CLASSPATH_SIC=${JAVA_RPD}/lib/*
INPUT_DIR=/app/REPIND/data/ADR_POST/INVALIDBARECODE
date=$(date '+%d%m%Y')
OUTPUT_DIR=/app/REPIND/data/ADR_POST/INVALIDBARECODE
memory=8192m

env=$hostType

email_subject=" [$env] Report of Batch Adr Invalid Barecode  "
emailDest="mail.sic.it@airfrance.fr"
email_content="This email is automatically generated, please do not reply to this, for any comment see below. \n"
email_content_no_file="This email is automatically generated, please do not reply to this, for any comment see below. \n Batch execution has been stopped.. no files to be processed.  \n "

execute()
{
	echo $*
	$*
}

function getFileContent() {
   	echo "$(<$1)"
}

#This function notify by email rejected gins
function sendEmailToNotifyAdrInvalidBarecode()
{
	echo -e "$email_content ${1}" | mailx -s "$email_subject" "${emailDest}"
}

function sendEmailToNotifyNoFileAdrInvalidBarecode()
{
	echo -e "$email_content_no_file" | mailx -s "$email_subject" "${emailDest}"
}

# Verification de la presence de java
[ -f ${JAVA_HOME}/bin/java ] || fatal_error "Runtime java absent ${JAVA_HOME}/bin/java"


# Créer le répertoire de sortie
mkdir -p $OUTPUT_DIR

##################################################
# Purge des fichiers de report anciens de 15 jours
##################################################

echo "Purge a 15 jours du repertoire $OUTPUT_DIR"
cd $OUTPUT_DIR
find $BARCODE_*.gz -mtime +15 -exec \rm {} \;

# Recuperer liste des options
OptionsBatch="$* -O $OUTPUT_DIR -I $INPUT_DIR"

cd $JAVA_RPD

# Lancer / relancer process
execute ${JAVA_HOME}/bin/java ${JAVA17_ARG} -Dbatch=BatchAdrInvalidBarecode -server -Xmx${memory} \
                                -cp ${CLASSPATH_SIC}:${JAVA_RPD}/sic-metier-root-0.0.1-SNAPSHOT.jar ${BATCH_PKG}.BatchAdrInvalidBarecode $OptionsBatch
cd $OUTPUT_DIR

if [ -f invalid_barecode_metric_*_$date.txt ]
then
#read file content
invalidBarecode_report="$(getFileContent invalid_barecode_metric_*_$date.txt)"

# Send email
$(sendEmailToNotifyAdrInvalidBarecode "$invalidBarecode_report")

rm invalid_barecode_metric_*_$date.txt

else
# Send email
$(sendEmailToNotifyNoFileAdrInvalidBarecode)

fi

exit 0
