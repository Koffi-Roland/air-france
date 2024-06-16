#!/bin/bash
JAVA_HOME=/tech/java/openjdk/1.17
JAVA_RPD=/app/REPIND/prod/bin/tcsic/java
JAVA17_ARG="--add-opens=java.base/java.lang=ALL-UNNAMED --add-opens=java.management/javax.management.openmbean=ALL-UNNAMED --add-opens=java.management/javax.management=ALL-UNNAMED --add-opens=java.base/java.lang.reflect=ALL-UNNAMED"
BATCH_PKG=com.airfrance.batch.cleanTravelDoc
CLASSPATH_SIC=${JAVA_RPD}/lib/*
memory=2048m

execute()
{
	echo $*
	$*
}

# Verification de la presence de java
[ -f ${JAVA_HOME}/bin/java ] || fatal_error "Runtime java absent ${JAVA_HOME}/bin/java"

	cd $JAVA_RPD

# Lancer / relancer process
	execute ${JAVA_HOME}/bin/java ${JAVA17_ARG} -Dbatch=BatchCleanTravelDoc -server -Xmx${memory} -cp ${CLASSPATH_SIC}:${JAVA_RPD}/sic-metier-root-0.0.1-SNAPSHOT.jar ${BATCH_PKG}.BatchCleanTravelDoc

exit 0
