#!/bin/bash
#
# Shell de lancement du batch de mise ù jour des segmentations par defaut
# 
#############################################################

#################################
# Valorisation variables
#################################
DIR_DATA_FIRMES=$BASE_DATA_DIR/FIRMES
DIR_DATA_BATCH=$DIR_DATA_FIRMES/MAJSEGMENTATIONFIRMES
Batch=$BASE_EXE_CPP_DIR/BatchMajSegmentationFirmes

#################################################
# Purge des fichiers de report anciens de 15 jours
#################################################
echo "Purge a 15 jours du repertoire $DIR_DATA_BATCH"
test -f $DIR_DATA_BATCH/Report_*.* && find $DIR_DATA_BATCH/Report_*.* -mtime +15 -exec \rm {} \;

#######################################################
# Ici on rècupère les paramàtres ù la ligne de commande
#######################################################

USAGE="USAGE : $0 [-h] [-o <nom du fichier de report>] [-t] [-J] [-C <number>] [-b <date (JJMMAAAA)>]"
TEXTHELP="\n 
-h                    : affiche cette page d'aide\n
-J                    : lancement en traitement journalier sur les donnèes de la veille \n
-t                    : Mode trace (optionnel)\n
-C <nbLignesDuCommit> : Mode commit + Nombre de lignes pour faire les Commit, 100 par dèfaut\n
-b <JJMMAAAA>         : Date de debut de recherche des segmentations a initialiser par defaut (JJMMAAAA)\n
-o <nomrapport>       : Nom du Fichier de compte-rendu des modifications\n
"

Trace=0;
Commit=0;
NbLigCommit="";
Report=0
bDate=0;
OptionsBatch=""
DateReport=`/usr/bin/date '+%Y%m%d'`

while getopts hJtC:b:o: c
do
        case $c in
        h) echo $USAGE
           echo $TEXTHELP
           exit 0;;
	J) bDate=1
	   DateInput=`TZ=MET+24 date '+%d%m%Y'`;;
        t) Trace=1;;
	C) Commit=1
	   NbLigCommit=$OPTARG;;
	b) bDate=1
	   DateInput=$OPTARG;;
	o) Report=1
	   fileReport=$DIR_DATA_BATCH"/Report_"$OPTARG;;
        esac
done


#######################################################
# Construction de la ligne de commande
#######################################################

if [ $Report -eq 1 ]
then
    OptionsBatch=" -o "$fileReport"."$DateReport
fi

if [ $Trace -eq 1 ]
then
    OptionsBatch=$OptionsBatch" -t"
fi

if [ $Commit -eq 1 ]
then
    OptionsBatch=$OptionsBatch" -C "$NbLigCommit
fi
 
if [ $bDate -eq 1 ]
then
    OptionsBatch=$OptionsBatch" -b "$DateInput
fi

#######################################################
# Lancement du batch
#######################################################

#echo "$Batch $OptionsBatch"
$Batch $OptionsBatch

exit $?
