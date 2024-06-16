#!/bin/bash

#Positionnement des variables de la VM
JAVA_HOME=/tech/java/openjdk/1.17
JAVA_RPD=/app/REPIND/prod/bin/tcsic/java
JAVA17_ARG="--add-opens=java.base/java.lang=ALL-UNNAMED --add-opens=java.management/javax.management.openmbean=ALL-UNNAMED --add-opens=java.management/javax.management=ALL-UNNAMED --add-opens=java.base/java.lang.reflect=ALL-UNNAMED"
BATCH_PKG=com.airfrance.batch.unsubscribecomprefkl
CLASSPATH_SIC=${JAVA_RPD}/lib/*
date=$(date '+%d%m%Y')
INPUT_DIR=/app/REPIND/travail/Mjadar/unsubscribe/compref/input/
FILE_FORMAT=*.csv
OUTPUT_DIR=/app/REPIND/data/UNSUBSCRIBE_COMPREF/KL/
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


# for email
env=$hostType
email_subject=" [$env] Report of Batch Unsubscribe Compref KL - $date"
emailDest="mojadar-ext@airfrance.fr"
email_content="This email is automatically generated, please do not reply to this, for any comment see below. \n"
email_content="$email_content \n""Dear, \n"
email_content="$email_content \n""Please find in attachment the logs report from BatchUnsubscribeCompref. \n"


# Recuperer liste des fichiers d'input et lancement du process

for fileDir in `ls $INPUT_DIR$FILE_FORMAT`
do
  fileName=`basename "$fileDir"`
  OptionsBatch="$* -I $INPUT_DIR -O $OUTPUT_DIR/ -F $fileName"

  cd $JAVA_RPD

  # Lancer / relancer process
  execute ${JAVA_HOME}/bin/java ${JAVA17_ARG} -Dbatch=BatchUnsubscribeComprefKL -server -Xmx${memory} \
                                  -cp ${CLASSPATH_SIC}:${JAVA_RPD}/sic-metier-root-0.0.1-SNAPSHOT.jar ${BATCH_PKG}.BatchUnsubscribeComprefKL $OptionsBatch

  email_content="$email_content \n""Integration done for the file ""$fileName . \n"
  email_attach=$OUTPUT_DIR/reject_inputs.txt
  metric_result="$(getFileContent $OUTPUT_DIR/metric_results.txt)"
  # Send email
  echo -e "$email_content $metric_result" | mailx -s "$email_subject" -a "$email_attach" "${emailDest}"

  mv $fileDir $OUTPUT_DIR"processed/"

  # delete metric_results.txt and reject_inputs.txt
  rm -f $OUTPUT_DIR/reject_inputs.txt $OUTPUT_DIR/metric_results.txt
done

exit 0
