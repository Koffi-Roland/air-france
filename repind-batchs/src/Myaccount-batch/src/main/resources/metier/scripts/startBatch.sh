#!/bin/sh

#Positionnement des variables de la VM
JAVA_HOME=/tech/java/openjdk/1.17
JAVA_SIC=/app/REPIND/prod/bin/java
CLASSPATH=.:./lib/*
OPT="-server -Xms256m -Xmx512m --add-opens=java.base/java.lang=ALL-UNNAMED --add-opens=java.management/javax.management.openmbean=ALL-UNNAMED --add-opens=java.management/javax.management=ALL-UNNAMED --add-opens=java.base/java.lang.reflect=ALL-UNNAMED"

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

# Test du nombre d'arguments
[ $# -eq 0 ] && fatal_error "Nombre d'arguments incorrect"
appli=$1
shift

# Verification de la presence de fichier jar
[ -f ${JAVA_SIC}/${appli}.jar ] || fatal_error "Fichier jar ${appli}.jar absent"

# Verification de la presence de java
[ -f ${JAVA_HOME}/bin/java ] || fatal_error "Runtime java absent ${JAVA_HOME}/bin/java"

# Lancement de la commande
# Le nom de la commande est immediatement suivi de l'option -Dbatch= pour permettre sa recherche
# dans le script stopBatch : unix limite le nombre de caracteres lors d'une recherche a 80 caracteres 
execute ${JAVA_HOME}/bin/java ${OPT} -cp $CLASSPATH -Dbatch=${appli} -jar ${JAVA_SIC}/${appli}.jar $* 
