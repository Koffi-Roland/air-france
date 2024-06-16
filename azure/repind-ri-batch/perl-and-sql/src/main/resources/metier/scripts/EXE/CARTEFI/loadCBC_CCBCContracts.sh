#!/bin/bash
####################################################################################
# Shell de lancement du batch de load des contrats CBC/CCBC
####################################################################################

# positionnement des variables propres au flux
FLUX_IN=RTLCT1
FLUX_OUT=RQLCT1
WORK_DIR=$BASE_DATA_DIR/CARTEFI/LOAD_CBC
RESULT_DIR=$WORK_DIR/RESULTATS
BACKUP_DIR=$WORK_DIR/BACKUP
dateJour=`/usr/bin/date '+%Y%m%d_%H%M%S'`

# detection production ou recette
if [ "$hostType" = "prd" ] 
then
    FLUX_IN=PTLCT1
    FLUX_OUT=PQLCT1
else
    FLUX_IN=RTLCT1
    FLUX_OUT=RQLCT1 
fi

# on recupere la liste des fichiers a traiter
ls $BASE_FTP_DIR/ACK$FLUX_IN"-"$FLUX_IN"_"*
if test $? -ne 0 
then 
  echo "PAS DE FICHIER D'ACQUITTEMENT : PAS DE TRAITEMENT !" 
  echo "---------------------------------------------------" 
  exit 0
fi

# recupere la liste des fichiers ACK transmis
lstFiles=`ls $BASE_FTP_DIR/ACK$FLUX_IN"-"$FLUX_IN"_"*`

# Purge des fichiers de report anciens de 15 jours
##################################################

# boucle de traitement pour chaque fichier de donnees contrats CBC/CCBC present
###############################################################################
for myFileACK in $lstFiles; do

	if [ -f $myFileACK ];
	then 
		\rm -f $myFileACK |& tee -a $TRACE_FILE 
	fi

	myFile=`basename $myFileACK |  sed -e "s/^\([^-]*\).//"`
	TRACE_FILE=$WORK_DIR/"$myFile"_"$dateJour".trace
	echo "TRACE FILE : $TRACE_FILE"
	/usr/bin/date |& tee -a $TRACE_FILE


	myFile=$BASE_FTP_DIR/$myFile
	if [ ! -f $myFile ]; then
		echo "LE FICHIER DE DONNEES $myFile N A PAS ETE LIVRE AVEC L'ACK !"  |& tee -a $TRACE_FILE
		exit 1
	fi   

	echo "---------------> RECEPTION DU FICHIER $myFile" |& tee -a $TRACE_FILE

	echo "Purge a 15 jours du repertoire $RESULT_DIR" |& tee -a $TRACE_FILE
	test -f $RESULT_DIR/$myFile* -mtime +15 -exec \rm {} \;

	echo "Purge a 15 jours du repertoire $BACKUP_DIR"  |& tee -a $TRACE_FILE
	test -f $BACKUP_DIR/$myFile* -mtime +15 -exec \rm {} \;

	# deplacement du fichier recu dans le repertoire de travail
	mv $myFile $WORK_DIR/.  |& tee -a $TRACE_FILE
	myFile=`/usr/bin/basename $myFile`

	echo "DEBUT BATCH de load des contrats CBC/CCBC"  |& tee -a $TRACE_FILE
	cd $WORK_DIR
	$BASE_EXE_CPP_DIR/BatchLoadContracts -i $WORK_DIR/$myFile -C  |& tee -a $TRACE_FILE
	echo "FIN BATCH de load des contrats CBC/CCBC"  |& tee -a $TRACE_FILE

	# deplacement des fichiers
	if [ -s $WORK_DIR/$myFile.out.txt ]
	then
		echo "Deplacement du fichier resultat"  |& tee -a $TRACE_FILE
		\mv $WORK_DIR/$myFile.out.txt $RESULT_DIR/$myFile.out.txt  |& tee -a $TRACE_FILE
	fi
	if [ -f $WORK_DIR/$myFile.out.txt ]
	then
		echo "On efface le fichier de resultats vide"  |& tee -a  $TRACE_FILE
		\rm $WORK_DIR/$myFile.out.txt  |& tee -a $TRACE_FILE
	fi
	if [ -f $WORK_DIR/$myFile.reprise.txt ]
	then
		echo "Deplacement du fichier de reprise"  |& tee -a $TRACE_FILE
		\mv $WORK_DIR/$myFile.reprise.txt $RESULT_DIR/$myFile.reprise.txt  |& tee -a $TRACE_FILE
	fi
	echo "Deplacement du fichier d'input"  |& tee -a $TRACE_FILE
	\mv $WORK_DIR/$myFile $BACKUP_DIR/$myFile  |& tee -a $TRACE_FILE

	echo "Compression du fichier d'input"  |& tee -a $TRACE_FILE
	\gzip $BACKUP_DIR/$myFile  |& tee -a $TRACE_FILE

	if [ -s $RESULT_DIR/$myFile.out.txt ]
	then
		# envoi par pelican des resultats    
		echo "ENVOI DES RESULTATS"  |& tee -a $TRACE_FILE
		$XFB/gwqvi1.sh $RESULT_DIR/$myFile.out.txt $FLUX_OUT |& tee -a $TRACE_FILE
	fi

	if [ -s $RESULT_DIR/$myFile.out.txt ]
	then
		echo "Compression du fichier resultat non vide"  |& tee -a $TRACE_FILE
		\gzip $RESULT_DIR/$myFile.out.txt  |& tee -a $TRACE_FILE
	fi
	/usr/bin/date  |& tee -a $TRACE_FILE
done
