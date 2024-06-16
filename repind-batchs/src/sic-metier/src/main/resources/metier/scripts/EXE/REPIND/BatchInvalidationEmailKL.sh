#!/bin/bash

#Positionnement des variables de la VM
JAVA_HOME=/tech/java/openjdk/1.17
JAVA_RPD=/app/REPIND/prod/bin/tcsic/java
JAVA17_ARG="--add-opens=java.base/java.lang=ALL-UNNAMED --add-opens=java.management/javax.management.openmbean=ALL-UNNAMED --add-opens=java.management/javax.management=ALL-UNNAMED --add-opens=java.base/java.lang.reflect=ALL-UNNAMED"
BATCH_PKG=com.airfrance.batch.invalidationemailkl
CLASSPATH_SIC=${JAVA_RPD}/lib/*
date=$(date '+%d%m%Y')
INPUT_DIR=/app/REPIND/travail/Mjadar/invalidation/input
OUTPUT_DIR=/app/REPIND/data/INVALIDATION_MAIL/KL
memory=2048m

execute()
{
	echo $*
	$*
}

function getFileContent() {
   	echo "$(<$1)"
}

# Verification de la presence de java
[ -f ${JAVA_HOME}/bin/java ] || fatal_error "Runtime java absent ${JAVA_HOME}/bin/java"

OptionsBatch="$* -I $INPUT_DIR -O $OUTPUT_DIR"

cd $JAVA_RPD

# Lancer / relancer process
execute ${JAVA_HOME}/bin/java ${JAVA17_ARG} -Dbatch=BatchInvalidationEmailKL -server -Xmx${memory} \
                                -cp ${CLASSPATH_SIC}:${JAVA_RPD}/sic-metier-root-0.0.1-SNAPSHOT.jar ${BATCH_PKG}.BatchInvalidationEmailKL $OptionsBatch


#read file content
metric_result="$(getFileContent $OUTPUT_DIR/metric_results.txt)"

# for email
env=$hostType
email_subject=" [$env] Report of Batch Invalidation Email KL - $date"
emailDest="mojadar-ext@airfrance.fr"
email_content="This email is automatically generated, please do not reply to this, for any comment see below. \n"
email_content="$email_content \n""Dear, \n"
email_content="$email_content \n""Please find in attachment the logs report from BatchInvalidationEmailKL. \n"
email_attach=$OUTPUT_DIR/reject_inputs.txt

# Send email
echo -e "$email_content $metric_result" | mailx -s "$email_subject" -a "$email_attach" "${emailDest}"

# delete metric_results.txt and reject_inputs.txt
rm -f $OUTPUT_DIR/reject_inputs.txt $OUTPUT_DIR/metric_results.txt

exit 0
