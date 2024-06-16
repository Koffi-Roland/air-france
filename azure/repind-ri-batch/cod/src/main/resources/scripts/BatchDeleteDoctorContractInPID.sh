#!/bin/bash

#Positionnement des variables de la VM

JAVA_HOME=/tech/java/openjdk/1.17
JAVA17_ARG="--add-opens=java.base/java.lang=ALL-UNNAMED --add-opens=java.management/javax.management.openmbean=ALL-UNNAMED --add-opens=java.management/javax.management=ALL-UNNAMED --add-opens=java.base/java.lang.reflect=ALL-UNNAMED"
JAVA_RPD=/app/REPIND/prod/bin/tcsic/java

BATCH_PKG=com.afklm.batch.cod
CLASSPATH_RPD=${JAVA_RPD}/lib/*

memory=1024m

fatal_error()
{
	echo "Erreur fatale :" $*
	exit 3
}

execute() {
  echo $*
  $*
}


# Verification de la presence de java
[ -f ${JAVA_HOME}/bin/java ] || fatal_error "Runtime java absent ${JAVA_HOME}/bin/java"


cd $JAVA_RPD
# Lancer / relancer process
execute ${JAVA_HOME}/bin/java -Dbatch=BatchDeleteDoctorContractInPID -server -Xmx${memory} ${JAVA17_ARG} -cp ${CLASSPATH_RPD}:${JAVA_RPD}/cod-0.0.1-SNAPSHOT.jar ${BATCH_PKG}.BatchDeleteDoctorContractInPID $*

exit 0
