#!/bin/bash
# shell d'execution du batch de replication pour ISIS

WORK_DIR=$BASE_DATA_DIR/REPLICATION/ISI_BDC
BACKUP_DIR=$WORK_DIR/REPLICATION_TRAITEE
TRACE_FILE=$WORK_DIR/replicISI.trace

REPLIC_ISI_PERL=$BASE_EXE_DIR/REPLICATION/replicationIsisBdc.pl
REPLIC_ISI_PERL_DATA=$BASE_DATA_DIR/REPLICATION/ISI_BDC
FLUX_OUT=TVISRI

if [ "$hostType" = "prd" ]
then
  FLUX_OUT=RVISRI
fi

# recuperation de la date du jour
chaineDate=`date '+%d%m%Y'`

date | tee -a $TRACE_FILE
# lancement du batch de replication pour ISI
echo "*** REPLICATION ISI DEBUT ***"  | tee -a $TRACE_FILE

CodeRetour=0
$REPLIC_ISI_PERL -application ISI -fileDate $chaineDate
CodeRetour=$?
echo "*** REPLICATION ISI FIN   ***"  | tee -a $TRACE_FILE
date  | tee -a $TRACE_FILE

if [ $CodeRetour -ne 0 ]
then
    exit $CodeRetour
else
  CodeRetour=0
  $XFB/gwqvi1.sh $REPLIC_ISI_PERL_DATA/REPISI.DAT.$chaineDate $FLUX_OUT  |& tee -a  $TRACE_FILE
  CodeRetour=$?
    
  if [ $CodeRetour -ne 0 ]
  then
    echo "Transfert du fichier $REPLIC_ISI_PERL_DATA/REPISI.DAT.$chaineDate vers le flux $FLUX_OUT : KO (code retour <$CodeRetour>)"  | tee -a $TRACE_FILE
    exit 1
  else 
    echo "Transfert du fichier $REPLIC_ISI_PERL_DATA/REPISI.DAT.$chaineDate vers le flux $FLUX_OUT : OK"  | tee -a $TRACE_FILE
  fi
fi

mv $REPLIC_ISI_PERL_DATA/REPISI.DAT.$chaineDate $BACKUP_DIR  |& tee -a $TRACE_FILE
gzip -f $BACKUP_DIR/REPISI.DAT.$chaineDate 
echo "Fichiers REPISI.DAT.$chaineDate deplace sous $BACKUP_DIR et zippes"  | tee -a $TRACE_FILE
date | tee -a $TRACE_FILE
exit 0
