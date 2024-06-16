#!/bin/bash
#Positionnement des variables de la VM
JAVA_HOME=/tech/java/openjdk/1.17
JAVA17_ARG="--add-opens=java.base/java.lang=ALL-UNNAMED --add-opens=java.management/javax.management.openmbean=ALL-UNNAMED --add-opens=java.management/javax.management=ALL-UNNAMED --add-opens=java.base/java.lang.reflect=ALL-UNNAMED"
JAVA_RPD=/app/REPIND/prod/bin/tcsic/java
BATCH_PKG=com.airfrance.batch.compref.fixafcompref
CLASSPATH_SIC=${JAVA_RPD}/lib/*
INPUT_DIR=/app/REPIND/ftp/
PROSPECT_DIR=/app/REPIND/data/PROSPECT/
memory=8192m

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

# Recuperer liste des fichiers d'input et lancement du process

  OptionsBatch="$* -o $OUTPUT_DIR -p $INPUT_DIR"

	cd $JAVA_RPD

	# Lancer / relancer process
	execute ${JAVA_HOME}/bin/java  -Dbatch=BatchFixAfComPref -server -Xmx${memory} ${JAVA17_ARG} -cp ${CLASSPATH_SIC}:${JAVA_RPD}/sic-metier-root-0.0.1-SNAPSHOT.jar ${BATCH_PKG}.BatchFixAfComPref $OptionsBatch

exit 0