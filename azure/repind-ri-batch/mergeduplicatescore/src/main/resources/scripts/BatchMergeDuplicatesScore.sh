#!/bin/bash

#Positionnement des variables de la VM
JAVA_HOME=/tech/java/openjdk/1.17
JAVA_RPD=/app/REPIND/prod/bin/tcsic/java
JAVA17_ARG="--add-opens=java.base/java.lang=ALL-UNNAMED --add-opens=java.management/javax.management.openmbean=ALL-UNNAMED --add-opens=java.management/javax.management=ALL-UNNAMED --add-opens=java.base/java.lang.reflect=ALL-UNNAMED"
BATCH_PKG=com.afklm.batch.mergeduplicatescore
CLASSPATH_SIC=${JAVA_RPD}/lib/*
INPUT_DIR=/app/REPIND/data/DETECT_DUPLICATES
date=$(date '+%d%m%Y')
OUTPUT_DIR=/app/REPIND/data/MERGE_DUPLICATES_DIR/$date
memory=8192m

env=$hostType

email_subject=" [$env] Report of Automatic Merge  "
emailDest="mail.sic.it@airfrance.fr"
email_content="This email is automatically generated, please do not reply to this, for any comment see below. \n"

execute()
{
	echo $*
	$*
}

function getFileContent() {
   	echo "$(<$1)"
}

#This function notify by email rejected gins
function sendEmailToNotifyRejectedGins()
{
	echo -e "$email_content ${1}" | mailx -s "$email_subject" "${emailDest}"
}


# Verification de la presence de java
[ -f ${JAVA_HOME}/bin/java ] || fatal_error "Runtime java absent ${JAVA_HOME}/bin/java"

# Créer le répertoire de sortie
mkdir -p $OUTPUT_DIR

# Recuperer liste des options
OptionsBatch="$* -O $OUTPUT_DIR -I $INPUT_DIR"

cd $JAVA_RPD

# Lancer / relancer process
execute ${JAVA_HOME}/bin/java ${JAVA17_ARG} -Djava.security.krb5.kdc=$KDCSERVER -Djava.security.krb5.realm=$REALMENV \
                                -Djava.security.auth.login.config=$JAASCONF -Dsun.security.krb5.debug=false -Dzookeeper.sasl.client=false \
                                -Dbatch=BatchMergeDuplicatesScore -server -Xmx${memory} \
                                -cp ${CLASSPATH_SIC}:${JAVA_RPD}/mergeduplicatescore-0.0.1-SNAPSHOT.jar ${BATCH_PKG}.BatchMergeDuplicatesScore $OptionsBatch


#read file content
merge_report="$(getFileContent $OUTPUT_DIR/merged_duplicates_metric_$date.txt)"


# Send email
$(sendEmailToNotifyRejectedGins "$merge_report")

exit 0
