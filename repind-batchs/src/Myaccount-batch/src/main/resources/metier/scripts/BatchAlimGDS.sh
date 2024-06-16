#!/bin/sh

#Positionnement des variables de la VM
JAVA_HOME=/tech/java/openjdk/1.17
JAVA17_ARG="--add-opens=java.base/java.lang=ALL-UNNAMED --add-opens=java.management/javax.management.openmbean=ALL-UNNAMED --add-opens=java.management/javax.management=ALL-UNNAMED --add-opens=java.base/java.lang.reflect=ALL-UNNAMED"
JAVA_SIC=/app/RAP/prod/bin/java
BATCH_PKG=com.airfrance.jraf.batch.agence
CLASSPATH_SIC=${JAVA_SIC}/lib/*
memory=128m

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


# Verification de la presence de java
[ -f ${JAVA_HOME}/bin/java ] || fatal_error "Runtime java absent ${JAVA_HOME}/bin/java"

# Lancement de la commande
# Le nom de la commande est immediatement suivi de l'option -Dbatch= pour permettre sa recherche
# dans le script stopBatch : unix limite le nombre de caracteres lors d'une recherche a 80 caracteres 
#execute ${JAVA_HOME}/bin/java -Dbatch=${appli} -server -Xmx${memory} -jar ${JAVA_SIC}/sic-batch.jar $* 
cd $JAVA_SIC
execute ${JAVA_HOME}/bin/java -Dbatch=BatchAlimentationGDS ${JAVA17_ARG} -server -Xmx${memory} -cp ${CLASSPATH_SIC}:${JAVA_SIC}/Myaccount-batch-0.1.0-SNAPSHOT.jar ${BATCH_PKG}.BatchAlimentationGDS $*
