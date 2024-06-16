#!/bin/bash
JAVA_HOME=/tech/java/openjdk/1.17
JAVA_RPD=/app/REPIND/prod/bin/tcsic/java
JAVA17_ARG="--add-opens=java.base/java.lang=ALL-UNNAMED --add-opens=java.management/javax.management.openmbean=ALL-UNNAMED --add-opens=java.management/javax.management=ALL-UNNAMED --add-opens=java.base/java.lang.reflect=ALL-UNNAMED"
BATCH_PKG=com.airfrance.batch.compref.injestadhocdata
CLASSPATH_SIC=${JAVA_RPD}/lib/*
INPUT_DIR=/app/REPIND/ftp/INJEST_ADHOC_DATA/
FILE_FORMAT=*.json
PROCESS_DIR=/app/REPIND/data/INJEST_ADHOC_DATA/
memory=2048m


email_subject="BatchInjestAdhocData result"
emailDest="mail.sic.it@airfrance.fr, Tatiana.Ni@klm.com, padetaeye-ext@airfrance.fr, pepijn-de.kort@klm.com, IMO.Fam-E-Acquisition@klm.com"
email_content="This email is automatically generated, please do not reply to this, for any comment see below. \n"
email_content="$email_content \n""Dear, \n"
email_content="$email_content \n""Please find in attachment the logs report from BatchInjestAdhocData. \n"


execute()
{
	echo $*
	$*
}

# Verification de la presence de java
[ -f ${JAVA_HOME}/bin/java ] || fatal_error "Runtime java absent ${JAVA_HOME}/bin/java"

# Repertoire de sortie
EXEC_DATE=`date +"%Y%m%d"`
OUTPUT_DIR="$PROCESS_DIR$EXEC_DATE"
mkdir -p $OUTPUT_DIR

if [ "$hostType" = "dev" ]
		then
    emailDest="fltexier-ext@airfrance.fr"
fi
if [ "$hostType" = "rct" ]
    then
    email_subject="[TEST] BatchInjestAdhocData result"
    emailDest="mail.sic.it@airfrance.fr"
fi

# Recuperer liste des fichiers d'input et lancement du process

for fileDir in `ls $INPUT_DIR$FILE_FORMAT`
do
	fileName=`basename "$fileDir"`
	fileName_without_ext="${fileName%.*}"
	OptionsBatch="$* -p $INPUT_DIR -o $OUTPUT_DIR/ -f $fileName"

	cd $JAVA_RPD

	# Lancer / relancer process
	execute ${JAVA_HOME}/bin/java ${JAVA17_ARG} -Dbatch=BatchInjestAdhocData -server -Xmx${memory} -cp ${CLASSPATH_SIC}:${JAVA_RPD}/sic-metier-root-0.0.1-SNAPSHOT.jar ${BATCH_PKG}.BatchInjestAdhocData $OptionsBatch

	email_content="$email_content \n""Integration done for the file ""$fileName . \n"
    email_attach=$OUTPUT_DIR/"$fileName_without_ext"_report.txt

    echo -e "$email_content" | mailx -s "$email_subject $fileName" -a "$email_attach" "${emailDest}"

	mv $fileDir $PROCESS_DIR"processed/"
done

exit 0
