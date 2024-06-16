#!/bin/bash
# Positionnement des variables de la VM 
JAVA_HOME=/tech/java/openjdk/1.17
JAVA_SIC=/app/$FS_DIR/prod/bin/java
JAVA17_ARG="--add-opens=java.base/java.lang=ALL-UNNAMED --add-opens=java.management/javax.management.openmbean=ALL-UNNAMED --add-opens=java.management/javax.management=ALL-UNNAMED --add-opens=java.base/java.lang.reflect=ALL-UNNAMED"
BATCH_PKG=com.afklm.batch.event.individu
CLASSPATH_SIC=${JAVA_SIC}/lib/*
memory=3072m 

fatal_error() 
{ 
	echo " Erreur fatale :" $* 
	exit 3 
} 

execute() 
{ 
	echo $* 
	$* 
} 

# Verification de la presence de java 
[ -f ${JAVA_HOME}/bin/java ] || fatal_error "Runtime java absent ${JAVA_HOME}/bin/java" 

cd $JAVA_SIC
# Lancer / relancer process
execute ${JAVA_HOME}/bin/java ${JAVA17_ARG} -Dbatch=BatchIndividualUpdateNotification -server -Xmx${memory} -cp ${CLASSPATH_SIC}:${JAVA_SIC}/event-individu-0.0.1-SNAPSHOT.jar ${BATCH_PKG}.BatchIndividualUpdateNotification $*
 
exit 0
