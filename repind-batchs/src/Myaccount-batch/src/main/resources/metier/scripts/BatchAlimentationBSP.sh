#!/bin/sh 

# Positionnement des variables de la VM 
JAVA_HOME=/tech/java/openjdk/1.17
JAVA17_ARG="--add-opens=java.base/java.lang=ALL-UNNAMED --add-opens=java.management/javax.management.openmbean=ALL-UNNAMED --add-opens=java.management/javax.management=ALL-UNNAMED --add-opens=java.base/java.lang.reflect=ALL-UNNAMED"
JAVA_RAP=/app/RAP/prod/bin/java
WORK_DIR=/app/RAP/data/BSP 
BATCH_PKG=com.airfrance.jraf.batch.agence 
CLASSPATH_RAP=${JAVA_RAP}/lib/*
memory=1024m 
FTP_DIR=/app/RAP/ftp
args=""

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

# Lancement de la commande 
# Le nom de la commande est immediatement suivi de l'option - Dbatch = pour permettre sa recherche 
# dans le script stopBatch : unix limite le nombre de caracteres lors d'une recherche a 80 caracteres 
#execute ${JAVA_HOME}/bin/java - Dbatch =${ appli } -server -Xmx ${memory} -jar ${JAVA_RAP}/ sic -batch.jar $* 
cd $JAVA_RAP

# Creer le repertoire de travail si absent
mkdir -p $WORK_DIR

# Creer le repertoire pour stocker les fichiers traites
mkdir -p ${FTP_DIR}/BSP/traites

lstFiles=`ls ${FTP_DIR}/{057AF,074KL}* 2>>/dev/null`

# Boucler sur tous les fichiers
for bspFile in $lstFiles; do
	# Preciser le fichier a traiter dans les params
	params="-f $bspFile $args"
	
	# Lancer / relancer process pour chaque fichier
	execute ${JAVA_HOME}/bin/java -Dbatch=BatchAlimentationBSP ${JAVA17_ARG} -server -Xmx${memory} -cp ${CLASSPATH_RAP}:${JAVA_RAP}/Myaccount-batch-0.1.0-SNAPSHOT.jar ${BATCH_PKG}.BatchAlimentationBSP $params
	
	# Deplacer le fichier dans le repertoire des fichiers traites
	mv $bspFile ${FTP_DIR}/BSP/traites/
done

exit 0
