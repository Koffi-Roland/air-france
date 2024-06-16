#!/bin/bash
# Positionnement des variables de la VM 
BATCH_PKG=com.airfrance.jraf.batch.agence 
CLASSPATH_SIC=${BASE_EXE_JAVA_DIR}/lib/*
memory=1024m 

execute() 
{ 
	echo $* 
	$* 
} 

# Lancement de la commande 
# Le nom de la commande est immediatement suivi de l'option - Dbatch = pour permettre sa recherche 
# dans le script stopBatch : unix limite le nombre de caracteres lors d'une recherche a 80 caracteres 
#execute ${JAVA_HOME}/bin/java - Dbatch =${ appli } -server -Xmx ${memory} -jar ${JAVA_SIC}/ sic -batch.jar $* 
cd $BASE_EXE_JAVA_DIR

# Lancer / relancer process
execute java -Dbatch=BatchAlimentationBSP -server -Xmx${memory} -cp ${CLASSPATH_SIC}:${BASE_EXE_JAVA_DIR}/Myaccount-batch-0.0.1-SNAPSHOT.jar ${BATCH_PKG}.BatchAlimentationBSP $* 
 
