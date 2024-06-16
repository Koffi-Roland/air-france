#!/bin/bash
#
# shell d'execution du batch de replication pour MYACCOUNT
#

WORK_DIR=$BASE_DATA_DIR/REPLICATION/MYACCOUNT
BACKUP_DIR=$WORK_DIR/REPLICATION_TRAITEE
TRACE_FILE=$WORK_DIR/replicMYACCOUNT.trace

REPLIC_MYACCOUNT_PERL=$BASE_EXE_DIR/REPLICATION/replicationMyAccount.pl
REPLIC_MYACCOUNT_MVT_DATA=$BASE_DATA_DIR/REPLICATION/MYACCOUNT/MVT

if [ "$hostType" = "prd" ]
then
    FLUXS_OUT[0]=PEDAT1
	FLUXS_OUT[1]=PEXAC1
fi

# recuperation de la date du jour
chaineDate=`date '+%d%m%Y'`

date >>$TRACE_FILE
# lancement du batch de replication journaliere : MVT
echo "*** REPLICATION MYACCOUNT DEBUT ***" >>$TRACE_FILE

CodeRetour=0
$REPLIC_MYACCOUNT_PERL -option MVT
CodeRetour=$?
echo "*** REPLICATION MYACCOUNT FIN   ***" >>$TRACE_FILE
date >>$TRACE_FILE


if [ $CodeRetour -ne 0 ]
then
    echo "Echec sur replication myaccount"
    exit $CodeRetour
elif [ "$hostType" = "prd" ]
then
    for FLUX_OUT in ${FLUXS_OUT[@]}
	do
		echo "Succes de la replication myaccount"
		CurrentCodeRetour=0
		
		gwqvi1.sh $REPLIC_MYACCOUNT_MVT_DATA/MVT-$chaineDate $FLUX_OUT 2>> $TRACE_FILE
		CurrentCodeRetour=$?
    
		if [ $CurrentCodeRetour -ne 0 ]
		then
		echo "Transfert du fichier $REPLIC_MYACCOUNT_MVT_DATA/MVT-$chaineDate vers le flux $FLUX_OUT : KO (code retour <$CodeRetour>)" >>$TRACE_FILE
		CodeRetour=$CurrentCodeRetour
		else 
		echo "Transfert du fichier $REPLIC_MYACCOUNT_MVT_DATA/MVT-$chaineDate vers le flux $FLUX_OUT : OK" >>$TRACE_FILE
		fi 
	done
	
	if [ $CodeRetour -ne 0 ]
	then
		echo "Un des deux flux KO"
		exit 1
	fi
else
    echo "Pas de transfert"
fi

echo "Compression du fichier"
gzip -f $REPLIC_MYACCOUNT_MVT_DATA/MVT-$chaineDate
echo "Fichier compresse"

date >>$TRACE_FILE

exit 0
