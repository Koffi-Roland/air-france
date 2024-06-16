#!/bin/sh

##############################################################
# Shell de lancement de la purge des firmes.
##############################################################

USAGE="Usage: $0 [-p PHASE_NUMBER] [-f](->FORCE_PHASE2) [-C] (->COMMIT_MODE) [-t] (->TRACE_MODE) [-l LOCAL_PATH]"

#
# Mise en place de l'environnement
#
# . $APPLICATION_RACINE/prod/bin/scripts/tools/setenv.sh
ALIMDATE=`date +"%d-%m-%Y"`

#Liste_Emails_Reef="LDIF_REEF_RARF_AMO@airfrance.fr"
Liste_Emails_Reef="GR_RARF_IT@airfrance.fr"

#to get the type of the machine (dev, prd, rct)
#hostType=`$BASE_TOOL_DIR/hostType`
if [ "$hostType" = "dev" ]
	then
	Liste_Emails_Reef="tdlinares-ext@airfrance.fr"
	SQLPLUSBASE=SIC_DEV
fi
if [ "$hostType" = "rct" ]
	then
	Liste_Emails_Reef="GR_RARF_IT@airfrance.fr"
	SQLPLUSBASE=SIC_RCT
fi
if [ "$hostType" = "prd" ]
	then
	Liste_Emails_Reef="GR_RARF_IT@airfrance.fr"
	SQLPLUSBASE=SIC_PRD
fi

PURGE_LOG_DIR=/app/REPFIRM/prod/logs/purge_firme
PURGE_LOG_FILE=$PURGE_LOG_DIR/purgeSIC_FIRME.log
[ -d $PURGE_LOG_DIR ] || mkdir -p "$PURGE_LOG_DIR"

# Parsing des options
while getopts p:fCtl: o
do	case "$o" in
	
	p)	PHASE_NUMBER=$OPTARG;;
	f)  FORCE_PHASE2="true";;
	C)	COMMIT_MODE="true";;
	t)	TRACE_MODE="true";;
	l)  LOCAL_PATH=$OPTARG;;
	h)  echo $USAGE
		exit 1;;
	[?])	echo $USAGE
		exit 1;;
	esac
done

# fixed Parameters 
NOT_PUBLIC_HOLIDAY="true"
WORKING_DAY="true"
FIRME_PURGE_DATA_DIR="/app/REPFIRM/data/PURGE"

echo "PHASE_NUMBER ["$PHASE_NUMBER"]"
echo "NOT_PUBLIC_HOLIDAY ["$NOT_PUBLIC_HOLIDAY"]"
echo "WORKING_DAY ["$WORKING_DAY"]"
echo "FIRME_PURGE_DATA_DIR ["$FIRME_PURGE_DATA_DIR"]"
echo "FORCE_PHASE2 ["$FORCE_PHASE2"]"
echo "COMMIT_MODE ["$COMMIT_MODE"]"
echo "TRACE_MODE ["$TRACE_MODE"]"
echo "LOCAL_PATH ["$LOCAL_PATH"]"

# call parameters java batch 
params=""
if [ "$PHASE_NUMBER" = "1" ] || [ "$PHASE_NUMBER" = "2" ]
then
	params="-phase $PHASE_NUMBER"
fi

if test "$FORCE_PHASE2" = "true"
then
	params="$params -p2Force"
fi

params="$params -p1NotPublicHoliday -p1WorkingDay -p1FileDateDirectory $FIRME_PURGE_DATA_DIR -p2Phase1FileDateDirectory $FIRME_PURGE_DATA_DIR"

if test "$COMMIT_MODE" = "true"
then 
	params="$params -C"
fi

if test "$TRACE_MODE" = "true"
then
	params="$params -t"
fi

#Positionnement des variables de la VM
JAVA_HOME=/exploit/java/oracle/1.8/jre

JAVA_SIC=/app/REPFIRM/prod/bin/java
#if [ "$hostType" = "dev" ]
#	then
	
	# Modif specifique pour test en dev
#	JAVA_SIC=/app/REPFIRM/prod/bin/javafirm
#fi
echo "JAVA_SIC ["$JAVA_SIC"]"

BATCH_PKG=com.airfrance.jraf.batch.firme
CLASSPATH_SIC=${JAVA_SIC}/lib/*
memory=512m

# format mail phase1
send_email_phase1()
{
	# variable jour YYYYMMDD
	jour=`date +"%Y%m%d"`

	ResBatch="$FIRME_PURGE_DATA_DIR/ResBatchPURGE_FIRME_PHASE1.txt"
	Titre_Mail="Companies Purge Phase 1"
	Texte_Mail="*** THIS IS A SYSTEM GENERATED MESSAGE, PLEASE DO NOT REPLY TO THIS EMAIL ***

	See attached files :
		1. Attached file Phase1_ServicesPurge.rapport contains the summary for Services Purge.
		2. Attached file Phase1_ServicesPurge.traces contains details of execution to the Services Purge.
		3. Attached file Phase1_ServicesPurge.rejet contains the list of all services that couldn't be purged.

		4. Attached file Phase1_MergedFirms.rapport contains the summary of the transfer into cancellation zones of merged firms.
		5. Attached file Phase1_MergedFirms.traces contains details of execution of the transfer into cancellation zones of merged firms.
		6. Attached file Phase1_MergedFirms.rejet contains the list of all merged firms that couldn't be transfered into a cancellation zone.

		7. Attached file Phase1_ClosedFirms.rapport contains the summary of the transfer into cancellation zones of closed firms.
		8. Attached file Phase1_ClosedFirms.traces contains details of execution of the transfer into cancellation zones of closed firms.
		9. Attached file Phase1_ClosedFirms.rejet contains the list of all closed firms that couldn't be transfered into a cancellation zone.

		10. Attached file Phase1_ActPendFirms.rapport contains the summary of the transfer into cancellation zones of active or pending firms.
		11. Attached file Phase1_ActPendFirms.traces contains details of execution of the transfer into cancellation zones of active or pending firms.
		12. Attached file Phase1_ActPendFirms.rejet contains the list of all active or pending firms that couldn't be transfered into a cancellation zone.

	For further details, consult logs in /app/REPFIRM/prod/logs/purge_firme/ or please contact : GR_RARF_IT@airfrance.fr
	Thank you."
	
	echo "Envoi resultat Phase 1 par email"

	cd $FIRME_PURGE_DATA_DIR
	zip $FIRME_PURGE_DATA_DIR/PurgeFirmePhase1$jour.zip Phase1_ServicesPurge.rapport Phase1_ServicesPurge.traces Phase1_ServicesPurge.rejet Phase1_MergedFirms.rapport Phase1_MergedFirms.traces Phase1_MergedFirms.rejet Phase1_ClosedFirms.rapport Phase1_ClosedFirms.traces Phase1_ClosedFirms.rejet Phase1_ActPendFirms.rapport Phase1_ActPendFirms.traces Phase1_ActPendFirms.rejet
	  
	# Renommage pour archive des logs apres zip
	for logFile in Phase1_* ; do mv ${logFile} ${logFile}_${jour}; done
		
	echo "$Texte_Mail" > $ResBatch
#	(cat $ResBatch; uuencode $FIRME_PURGE_DATA_DIR/PurgeFirmePhase1$jour.zip attachement.zip) | mailx -s "$Titre_Mail" $Liste_Emails_Reef
	cat $ResBatch | mailx -s "$Titre_Mail" -a $FIRME_PURGE_DATA_DIR/PurgeFirmePhase1$jour.zip $Liste_Emails_Reef

	rm $FIRME_PURGE_DATA_DIR/PurgeFirmePhase1$jour.zip
	rm $FIRME_PURGE_DATA_DIR/ResBatchPURGE_FIRME_PHASE1.txt
}

# format mail phase2
send_email_phase2()
{
	# variable jour YYYYMMDD
	jour=`date +"%Y%m%d"`
		
	ResBatch="$FIRME_PURGE_DATA_DIR/ResBatchPURGE_FIRME_PHASE2.txt"
	Titre_Mail="Companies Purge Phase 2"
	Texte_Mail="*** THIS IS A SYSTEM GENERATED MESSAGE, PLEASE DO NOT REPLY TO THIS EMAIL ***

	See attached files :
		1. Attached file Phase2_FirmsPurge.rapport contains the summary for purge of firms.
		2. Attached file Phase2_FirmsPurge.traces contains details of execution for purge of firms.
		3. Attached file Phase2_FirmsPurge.rejet contains the list of closed and merged firms being in a unique cancellation zone but wich couldn't be purged.

		4. Attached file Phase2_EnterprisePurge.rapport contains the summary of the transfer into the purge of enterprises.
		5. Attached file Phase2_EnterprisePurge.traces contains details of execution of the transfer into the purge of enterprises.
		6. Attached file Phase2_EnterprisePurge.rejet the contains the list of enterprise that couldn't be purged.

	For further details, consult logs in /app/REPFIRM/prod/logs/purge_firme/ or please contact : GR_RARF_IT@airfrance.fr
	Thank you."
	
	echo "Envoi resultat Phase 2 par email"

	cd $FIRME_PURGE_DATA_DIR
	zip $FIRME_PURGE_DATA_DIR/PurgeFirmePhase2$jour.zip Phase2_FirmsPurge.rapport Phase2_FirmsPurge.traces Phase2_FirmsPurge.rejet Phase2_EnterprisePurge.rapport Phase2_EnterprisePurge.traces Phase2_EnterprisePurge.rejet
	
	# Renommage pour archive des logs apres zip
	for logFile in Phase2_* ; do mv ${logFile} ${logFile}_${jour}; done
	
	echo "$Texte_Mail" > $ResBatch
#	(cat $ResBatch; uuencode $FIRME_PURGE_DATA_DIR/PurgeFirmePhase2$jour.zip attachement.zip) | mailx -s "$Titre_Mail" $Liste_Emails_Reef
	cat $ResBatch | mailx -s "$Titre_Mail" -a $FIRME_PURGE_DATA_DIR/PurgeFirmePhase2$jour.zip $Liste_Emails_Reef

	rm $FIRME_PURGE_DATA_DIR/PurgeFirmePhase2$jour.zip
	rm $FIRME_PURGE_DATA_DIR/ResBatchPURGE_FIRME_PHASE2.txt
}


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

#
# Lancement de la purge 
#
PURGE_START_TIME=`date +"%d/%m/%y %H:%M:%S UTC"`

echo  >> $PURGE_LOG_FILE
echo ------------------------------------------------------------------- >> $PURGE_LOG_FILE
echo "[$PURGE_START_TIME] Purge des firmes : DEBUT phase " $PHASE_NUMBER >> $PURGE_LOG_FILE


# Lancement de la commande
# Le nom de la commande est immediatement suivi de l'option -Dbatch= pour permettre sa recherche
# dans le script stopBatch : unix limite le nombre de caracteres lors d'une recherche a 80 caracteres 
#execute ${JAVA_HOME}/bin/java -Dbatch=${appli} -server -Xmx${memory} -jar ${JAVA_SIC}/FirmBatch.jar $* 
#cd $JAVA_SIC
execute ${JAVA_HOME}/bin/java -Dbatch=BatchPurgeFirme -server -Xmx${memory} -cp ${CLASSPATH_SIC}:${JAVA_SIC}/Myaccount-batch-0.1.0-SNAPSHOT.jar ${BATCH_PKG}.BatchPurgeFirme $params

# batch return code
status=$?
echo "batch return code : "$status

#
# Fin de la purge FIRME
#
PURGE_END_TIME=`date +"%d/%m/%y %H:%M:%S UTC"`
echo "[$PURGE_END_TIME] Purge des FIRMES : FIN" >> $PURGE_LOG_FILE

# Si la purge a eu lieu envoie par mail les fichiers de trace/rejet/rapport correspondant
if test "$status" = "0"
then
	if test -f "$FIRME_PURGE_DATA_DIR/Phase1_ClosedFirms.rapport"
	then
		send_email_phase1 "succeed"
	elif test -f "$FIRME_PURGE_DATA_DIR/Phase2_FirmsPurge.rapport"
	then 
		send_email_phase2 "succeed"
	fi
fi

exit 0