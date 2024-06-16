#!/bin/bash

# Positionnement des variables de la VM
JAVA_RPD=/app/REPIND/prod/bin/tcsic/java
JAVA_HOME=/tech/java/openjdk/1.17
JAVA17_ARG="--add-opens=java.base/java.lang=ALL-UNNAMED --add-opens=java.management/javax.management.openmbean=ALL-UNNAMED --add-opens=java.management/javax.management=ALL-UNNAMED --add-opens=java.base/java.lang.reflect=ALL-UNNAMED"

# to be changed according the package
BATCH_PKG=com.afklm.batch.invalidation.email
CLASSPATH_RPD=${JAVA_RPD}/lib/*
memory=512m

fatal_error()
{
	echo "Erreur fatale :" $*
	exit 3
}

execute()
{
	echo $*
	$*
}
	
# Verification de la presence de java pour invoquer le batch
[ -f ${JAVA_HOME}/bin/java ] || fatal_error "Runtime java absent ${JAVA_HOME}/bin/java"

# Lancement de la commande
# Le nom de la commande est immediatement suivi de l'option -Dbatch= pour permettre sa recherche
# dans le script stopBatch : unix limite le nombre de caracteres lors d'une recherche a 80 caracteres
#execute ${JAVA_HOME}/bin/java -Dbatch=${appli} -server -Xmx${memory} -jar ${JAVA_SIC}/sic-batch.jar $* en passant tous les arguments au batch java depuis le sh
cd $JAVA_RPD
execute ${JAVA_HOME}/bin/java ${JAVA17_ARG} -Dbatch=BatchInvalidationEmail -server -Xmx${memory} -cp ${CLASSPATH_RPD}:${JAVA_RPD}/invalidation-myaccount-email-0.0.1-SNAPSHOT.jar ${BATCH_PKG}.BatchInvalidationEmail $*
