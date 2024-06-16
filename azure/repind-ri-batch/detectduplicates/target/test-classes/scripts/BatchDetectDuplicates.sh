#!/bin/bash

#Positionnement des variables de la VM
JAVA_HOME=/tech/java/openjdk/1.17
JAVA_TCSIC=/app/REPIND/prod/bin/tcsic/java
JAVA17_ARG="--add-opens=java.base/java.lang=ALL-UNNAMED --add-opens=java.management/javax.management.openmbean=ALL-UNNAMED --add-opens=java.management/javax.management=ALL-UNNAMED --add-opens=java.base/java.lang.reflect=ALL-UNNAMED"
JAVA_RPD=/app/REPIND/prod/bin/java
BATCH_PKG=com.afklm.batch.detectduplicates
CLASSPATH_SIC=${JAVA_TCSIC}/lib/*
DETECT_DUPLICATES_DIR=/app/REPIND/data/DETECT_DUPLICATES
memory=8192m

execute() {
  echo $*
  $*
}

# Verification de la presence de java
[ -f ${JAVA_HOME}/bin/java ] || fatal_error "Runtime java absent ${JAVA_HOME}/bin/java"

# Repertoire de sortie
mkdir -p $DETECT_DUPLICATES_DIR

# Recuperer liste des options

cd $JAVA_RPD

OPTIONS_BATCH="$* -O $DETECT_DUPLICATES_DIR"

# Lancer / relancer process
execute ${JAVA_HOME}/bin/java ${JAVA17_ARG} -Dbatch=BatchDetectDuplicates -server -Xmx${memory} -cp ${CLASSPATH_SIC}:${JAVA_TCSIC}/detectduplicates-0.0.1-SNAPSHOT.jar ${BATCH_PKG}.BatchDetectDuplicates $OPTIONS_BATCH

exit 0
