#!/bin/bash
#Positionnement des variables de la VM
JAVA_HOME=/tech/java/openjdk/1.17
JAVA17_ARG="--add-opens=java.base/java.lang=ALL-UNNAMED --add-opens=java.management/javax.management.openmbean=ALL-UNNAMED --add-opens=java.management/javax.management=ALL-UNNAMED --add-opens=java.base/java.lang.reflect=ALL-UNNAMED"
JAVA_RPD=/app/REPIND/prod/bin/tcsic/java
BATCH_PKG=com.airfrance.batch.compref.migklsub
CLASSPATH_SIC=${JAVA_RPD}/lib/*
INPUT_DIR=/app/REPIND/ftp/SFMC/
FILE_FORMAT=SFMC_Feed_*.pgp
PROSPECT_DIR=/app/REPIND/data/PROSPECT/
memory=8192m

email_subject="CSM2RI file > Logs identification phase 1 (FB/MA) > "
emailDest="mail.sic.it@airfrance.fr, kecaron-ext@airfrance.fr, Marcio.Seidenberg@klm.com, Maurice.Poels@klm.com, Paul.Neeleman@klm.com, Tatiana.Ni@klm.com"
#emailDest="lotomponiony@airfrance.fr"
email_content="This email is automatically generated, please do not reply to this, for any comment see below. \n"
email_content="$email_content \n""Dear, \n"
email_content="$email_content \n""Please find in this email the identification logs on CSM phase 1. For more details on each rejection, please check the attached files.

Identification process :
1.            GIN + CIN (FB_Identifier / MyAccount Identifier) 
If we don't match the individual we do another identification with 
2.            GIN + Email.
\n"


execute()
{
	echo $*
	$*
}

# Verification de la presence de java
[ -f ${JAVA_HOME}/bin/java ] || fatal_error "Runtime java absent ${JAVA_HOME}/bin/java"

# Repertoire de sortie
EXEC_DATE=`date +"%Y%m%d"`
OUTPUT_DIR="$PROSPECT_DIR$EXEC_DATE/"
mkdir -p $OUTPUT_DIR

if [ "$hostType" = "dev" ]
		then
    emailDest="lotomponiony@airfrance.fr"
fi
if [ "$hostType" = "rct" ]
    then
    email_subject="[TEST] CSM2RI file > Logs identification phase 1 (FB/MA) > "
fi

# Recuperer liste des fichiers d'input et lancement du process

for fileDir in `ls $INPUT_DIR$FILE_FORMAT`
do
	fileDirClean="${fileDir%.*}"
	fileName=`basename "$fileDirClean"`
	fileName_without_ext="${fileName%.*}"
	fileClean="$fileName_without_ext.CSV"
	mv $fileDir $INPUT_DIR$fileClean
	OptionsBatch="$* -f $fileClean -o $OUTPUT_DIR -p $INPUT_DIR"

	cd $JAVA_RPD

	split -a 3 -dl 40000 $INPUT_DIR$fileClean $INPUT_DIR$fileName_without_ext- ; for file in $INPUT_DIR$fileName_without_ext-* ; do mv "$file" "$file".CSV; done

	# Lancer / relancer process
	execute ${JAVA_HOME}/bin/java  -Dbatch=BatchMigrationKLSubscriptions -server -Xmx${memory} ${JAVA17_ARG} -cp ${CLASSPATH_SIC}:${JAVA_RPD}/sic-metier-root-0.0.1-SNAPSHOT.jar ${BATCH_PKG}.BatchMigrationKLSubscriptions $OptionsBatch

	email_content="$email_content \n""On the daily file ""$fileName : \n"
    report="$(cat $OUTPUT_DIR/success_$fileName)"
    email_content="$email_content""$report \n"


    email_attach_gin="$OUTPUT_DIR/out_of_gin_not_match_$fileName"
    email_attach_gin_email="$OUTPUT_DIR/out_gin_email_not_match_$fileName"
    email_attach_gin_cin="$OUTPUT_DIR/out_gin_cin_not_match_$fileName"
    email_attach_lang="$OUTPUT_DIR/out_of_language_not_found_$fileName"
    email_attach_market="$OUTPUT_DIR/out_of_market_not_found_$fileName"

    echo -e "$email_content" | mailx -s "$email_subject $fileName" -a "$email_attach_gin" -a "$email_attach_gin_email" -a "$email_attach_gin_cin" -a "$email_attach_lang" -a "$email_attach_market" "${emailDest}"

  	rm -rf $INPUT_DIR$fileName_without_ext-*
	mv $INPUT_DIR$fileClean $PROSPECT_DIR"processed/"
done

exit 0
