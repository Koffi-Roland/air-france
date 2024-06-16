#!/bin/bash

#Positionnement des variables de la VM
BATCH_PKG=com.airfrance.jraf.batch.individu
JAVA_HOME=/tech/java/openjdk/1.17
JAVA17_ARG="--add-opens=java.base/java.lang=ALL-UNNAMED --add-opens=java.management/javax.management.openmbean=ALL-UNNAMED --add-opens=java.management/javax.management=ALL-UNNAMED --add-opens=java.base/java.lang.reflect=ALL-UNNAMED"
CLASSPATH_SIC=${BASE_EXE_JAVA_DIR}/lib/*
memory=128m

execute()
{
	echo $*
	$*
}

# Lancement de la commande
# Le nom de la commande est immediatement suivi de l'option -Dbatch= pour permettre sa recherche
# dans le script stopBatch : unix limite le nombre de caracteres lors d'une recherche a 80 caracteres
#execute ${JAVA_HOME}/bin/java -Dbatch=${appli} -server -Xmx${memory} -jar ${JAVA_SIC}/sic-batch.jar $*
cd $BASE_EXE_JAVA_DIR
execute java -Dbatch=BatchNormalizedPhoneNumber -server -Xmx${memory} ${JAVA17_ARG} -cp ${CLASSPATH_SIC}:${BASE_EXE_JAVA_DIR}/Myaccount-batch-0.1.0-SNAPSHOT.jar ${BATCH_PKG}.BatchNormalizedPhoneNumber $*