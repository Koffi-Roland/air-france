#!/bin/bash
# Positionnement des variables de la VM 
JAVA_HOME=/exploit/java/oracle/1.8/jre
JAVA_SIC=/app/$FS_DIR/prod/bin/java
BATCH_PKG=com.airfrance.jraf.batch.event.firme 
CLASSPATH_SIC=${JAVA_SIC}/lib/*
memory=2048m 

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
execute ${JAVA_HOME}/bin/java -Dbatch=BatchFirmAgencyUpdateNotification -server -Xmx${memory} -cp ${CLASSPATH_SIC}:${JAVA_SIC}/Myaccount-batch-0.1.0-SNAPSHOT.jar ${BATCH_PKG}.BatchFirmAgencyUpdateNotification $* 
 