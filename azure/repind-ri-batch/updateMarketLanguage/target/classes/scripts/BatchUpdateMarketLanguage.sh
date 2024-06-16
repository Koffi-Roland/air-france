#!/bin/bash

#Positionnement des variables de la VM
JAVA_HOME=/tech/java/openjdk/1.17
JAVA17_ARG="--add-opens=java.base/java.lang=ALL-UNNAMED --add-opens=java.management/javax.management.openmbean=ALL-UNNAMED --add-opens=java.management/javax.management=ALL-UNNAMED --add-opens=java.base/java.lang.reflect=ALL-UNNAMED"
JAVA_RPD=/app/REPIND/prod/bin/tcsic/java
BATCH_PKG=com.afklm.batch.updateMarketLanguage
CLASSPATH_SIC=${JAVA_RPD}/lib/*
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

# Recuperer liste des options

  OptionsBatch="$* -o $OUTPUT_DIR"

	cd $JAVA_RPD

# Lancer / relancer process
	execute ${JAVA_HOME}/bin/java  -Dbatch=BatchUpdateMarketLanguage -server -Xmx${memory} ${JAVA17_ARG} -cp ${CLASSPATH_SIC}:${JAVA_RPD}/updateMarketLanguage-0.0.1-SNAPSHOT.jar ${BATCH_PKG}.BatchUpdateMarketLanguage $OptionsBatch

exit 0
