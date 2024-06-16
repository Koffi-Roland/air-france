#!/bin/bash
#
# Shell de lancement du batch d'invalidation des adresses barecodes AMEX
# 
#############################################################

#################################
# Valorisation variables
#################################

DIR_EXE_BATCH=$BASE_EXE_CPP_DIR

#to get the type of the machine (dev, prd, rct)
Liste_Emails_Reef="LDIF_REEF_TMA@airfrance.fr,LDIF_REEF_RI_AMO@airfrance.fr"
Titre_Mail="Rapport Chargement Invalid Adr Barcode AMEX"
FORMAT_FILE_AUTO=PINVA3_*
if [ "$hostType" = "dev" ]
	then
	echo "Mode dev"
	Liste_Emails_Reef="LDIF_REEF_TMA@airfrance.fr"
	Titre_Mail="[DEV] Rapport Chargement Invalid Adr Barcode AMEX"
	FORMAT_FILE_AUTO=RINVA3_*
fi
if [ "$hostType" = "rct" ]
	then
	echo "Mode recette"
	Liste_Emails_Reef="LDIF_REEF_TMA@airfrance.fr"
	Titre_Mail="[RCT] Rapport Chargement Invalid Adr Barcode AMEX"
	FORMAT_FILE_AUTO=RINVA3_*
fi

# Repertoire des traces
DIR_DATA=$BASE_DATA_DIR
DIR_DATA_BATCH=$DIR_DATA/ADR_POST/INVALIDBARECODE
DIR_DATA_INPUT=$BASE_FTP_DIR
FILE_EXTENTION=
TAR_NAME=BARCODE_AMEX
Texte_Mail="Resultat du passage du batch d'invalidation des adresses barcode AMEX"

Batch=$DIR_EXE_BATCH/BatchAdrInvalidBarecode

###################################
# Test if Data directory ever exist
###################################
test -f $DIR_DATA_BATCH
RET=$?
if [ $RET -eq 1 ]
then
  mkdir -p $DIR_DATA_BATCH
fi

##################################################
# Purge des fichiers de report anciens de 15 jours
##################################################

echo "Purge a 15 jours du repertoire $DIR_DATA_BATCH"

test -f $DIR_DATA_BATCH/$TAR_NAME_*.tar.gz && find $DIR_DATA_BATCH/$TAR_NAME_*.tar.gz -mtime +15 -exec \rm {} \;

########################################################
# Ici on r�cup�re les param�tres de la ligne de commande
########################################################

USAGE="USAGE : $0 [-f <file_name>] [-h] [-t] [-C <number>] [-x] [-z]"
TEXTHELP="\n 
-h                    : affiche cette page d'aide\n
-f <file_name>        : The input file name (by default search in /app/SIC/ftp)
-t                    : Mode trace (optionnel)\n
-C <nbRowToCommit>    : Mode commit + Nombre de lignes a valider en base de donn�e (COMMIT)\n
-x                    : No mail sending\n
-z                    : No zip files\n
"

OptionsBatch=""
# Input parameters
fileName="0";
NbRowCommit="";
mailSending="1";
zipFiles="1";

while getopts hf:tC:xz c
do
    case $c in
        h) echo $USAGE
           echo $TEXTHELP
           exit 0;;
	f) fileName=$OPTARG
	   echo $fileName
	   dos2unix $fileName $fileName
           OptionsBatch=$OptionsBatch" -f "$fileName;;
        t) OptionsBatch=$OptionsBatch" -t";;
	C) NbRowCommit=$OPTARG
	   OptionsBatch=$OptionsBatch" -C "$NbRowCommit;;
	x) mailSending="0";;
	z) zipFiles="0";;
	?) echo $USAGE
           echo $TEXTHELP
           exit 2;;
    esac
done

#########################################################
# Test pour Maestro la presence du fichier dans le rep IN
# Si oui, on le d�place dans le repertoire BATCH
#########################################################
if [ $fileName = "0" ]
then
  echo "No option -f"
  test -f $DIR_DATA_INPUT/$FORMAT_FILE_AUTO
  RET2=$?
    if [ $RET2 -eq 0 ]
    then
      echo "File on standby"
      mv -f $DIR_DATA_INPUT/$FORMAT_FILE_AUTO $DIR_DATA_BATCH
      for fileName in `ls $DIR_DATA_BATCH/$FORMAT_FILE_AUTO`
      do
	dos2unix $fileName $fileName
        baseFileName="0"
        date="0"
        appli="0"
        OptionsBatchAuto=""
        OptionsBatchAuto=$OptionsBatch" -f "$fileName
        baseFileName=`basename "$fileName"`
        echo "---\nFile: $baseFileName."${FILE_EXTENTION}

	#########################################################
	# Traitement des fichiers de sortie
	#########################################################

	rejectFile=$baseFileName${FILE_EXTENTION}.reject
	reportFile=$baseFileName${FILE_EXTENTION}.report
	repriseFile=$baseFileName${FILE_EXTENTION}.reprise
	dateJour=`date '+%Y%m%d_%H%M%S'`
	tarFile=$TAR_NAME"_"$dateJour".tar.gz"

	#######################################################
	# Lancement du batch
	#######################################################
	echo "$Batch $OptionsBatchAuto"
	$Batch $OptionsBatchAuto
	retBatch=$?

	# ZIP du fichier IN
	if [ $retBatch -eq 0 ]
	then
	    if [ $mailSending = "1" ]
	    then
		echo "$Texte_Mail" > ResBatchBarcode.txt
		echo "Fichier traite: $fileName" >> ResBatchBarcode.txt
		echo >> ResBatchBarcode.txt
		tail -25 $DIR_DATA_BATCH/$reportFile >> ResBatchBarcode.txt
		AlertMess=`tail -3 ResBatchBarcode.txt | head -1 | awk '{print $3}'`
		$BASE_EXE_DIR/TransfertMail.sh ResBatchBarcode.txt $Liste_Emails_Reef "$AlertMess $Titre_Mail"
		\rm ResBatchBarcode.txt
	    fi
	    if [ $zipFiles = "1" ]
	    then
		echo "ZIP Files..."
		filesToTar="$fileName $DIR_DATA_BATCH/$rejectFile $DIR_DATA_BATCH/$reportFile"
		test -f $DIR_DATA_BATCH/$repriseFile
		RET3=$?
		if [ $RET3 -eq 0 ]
		then
		    filesToTar=$filesToTar" $DIR_DATA_BATCH/$repriseFile"
		fi
		tar cvf - $filesToTar |gzip > $DIR_DATA_BATCH/$tarFile
		rm -f $filesToTar
	    fi
	fi
      done
    else
      echo "No File on standby"
      exit 0
    fi
else
    # file given as argument

    #########################################################
    # Traitement des fichiers de sortie
    #########################################################
    baseFileName=`basename "$fileName"`
    rejectFile=$fileName.reject
    reportFile=$fileName.report
    repriseFile=$fileName.reprise
    dateJour=`date '+%Y%m%d_%H%M%S'`
    tarFile=$TAR_NAME"_"$dateJour".tar.gz"

    #######################################################
    # Lancement du batch
    #######################################################
    echo "$Batch $OptionsBatch"
    $Batch $OptionsBatch
    retBatch=$?

    # ZIP du fichier IN
    if [ $retBatch -eq 0 ]
    then
	if [ $mailSending = "1" ]
	then
	    echo "$Texte_Mail" > ResBatchBarcode.txt
	    echo "Fichier traite: $fileName" >> ResBatchBarcode.txt
	    echo >> ResBatchBarcode.txt
	    tail -25 $reportFile >> ResBatchBarcode.txt
	    AlertMess=`tail -3 ResBatchBarcode.txt | head -1 | awk '{print $3}'`
	    $BASE_EXE_DIR/TransfertMail.sh ResBatchBarcode.txt $Liste_Emails_Reef "$AlertMess $Titre_Mail"
	    \rm ResBatchBarcode.txt
	fi
	if [ $zipFiles = "1" ]
	then
	    echo "ZIP Files..."
	    filesToTar="$fileName $rejectFile $reportFile"
	    test -f $repriseFile
	    RET3=$?
	    if [ $RET3 -eq 0 ]
	    then
		filesToTar=$filesToTar" $repriseFile"
	    fi
	    tar cvf - $filesToTar |gzip > ${DIR_DATA_BATCH}/${tarFile}
	    rm -f $filesToTar
	fi
    fi
fi
    
exit $retBatch
