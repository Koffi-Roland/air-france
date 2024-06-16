#!/bin/bash
#
# Shell de lancement du script SQL des KPI sur ADR_POST
# 
#############################################################
#################################
# Valorisation variables
#################################

SQL_DIR=$BASE_SQL_DIR/ADR_POST

# Repertoire des traces
DIR_DATA=$BASE_DATA_DIR
DIR_DATA_OUT=$DIR_DATA/ADR_POST
dateJour=`/bin/date '+%Y%m%d'`
resultFile=$DIR_DATA_OUT/kpi_PostalFlag$dateJour.lst

Liste_Emails_Reef="LDIF_REEF_TMA@airfrance.fr,LDIF_REEF_RI_AMO@airfrance.fr"
Titre_Mail="Rapport KPI Adresses Postales"
Texte_Mail="Resultat de la recherche des KPI sur les Adresses Postales\n"


###################################
# Test if Data directory ever exist
###################################
test -f $DIR_DATA_OUT
RET=$?
if [ $RET -eq 1 ]
then
  mkdir -p $DIR_DATA_OUT
fi

##################################################
# Purge des fichiers de report anciens de 15 jours
##################################################

echo "Purge a 360 jours du repertoire $DIR_DATA_OUT"

test -f $DIR_DATA_OUT/kpi_*.lst && find $DIR_DATA_OUT/kpi_*.lst -mtime +360 -exec \rm {} \;

########################################################
# Ici on rècupàre les paramàtres de la ligne de commande
########################################################

USAGE="USAGE : $0 [-h] [-x] [-n]"
TEXTHELP="\n 
-h                    : affiche cette page d'aide\n
-x                    : No mail sending\n
-n                    : No MONTH mode\n
"

mailSending="1";
monthMode="1";

while getopts hxn c
do
    case $c in
        h) echo $USAGE
           echo $TEXTHELP
           exit 0;;
	x) mailSending="0";;
	n) monthMode="0";;
	?) echo $USAGE
           echo $TEXTHELP
           exit 2;;
    esac
done

# Block KPI for only one time per month

# Send only on report per month
currentMonth=`/bin/date '+%m'`

# Search the month of the last file produiced
lastFileName=`ls -ltr $DIR_DATA_OUT/kpi_PostalFlag*.lst | tail -1 | awk ' BEGIN {FS=" "} { print $9}'`
fileName=`basename $lastFileName`
lastFileMonth=`expr "$fileName" : 'kpi_PostalFlag....\(..\)...lst'`
if [ -z "$VAR" ]
then
	lastFileMonth=-1
fi

# Compare the two months
if [ "$currentMonth" -ne "$lastFileMonth" ] || [ "$monthMode" -eq "0" ]
then
    ########################################################
    # Exec SQL KPI file
    ########################################################
    sqlplus `secureSIC $SECURE_DATABASE_ACCESS_FILE_SIC READSIC` @$SQL_DIR/kpi_AdrPostalFlag.sql $DIR_DATA_OUT
    if [ $? -ne 0 ]
    then
		echo "Erreur pendant la recherche des KPI"
		exit 1
    fi

    ########################################################
    # Email sending
    ########################################################
    if [ "$mailSending" = "1" ] && [ "$hostType" = "prd" ]
    then
		result=$(cat $resultFile)
		Texte_Mail="$Texte_Mail\n$result"
		echo -e "$Texte_Mail" | mailx -s "$Titre_Mail" $Liste_Emails_Reef
    else
		Liste_Emails_Reef="mail.sic.it@airfrance.fr"
		result=$(cat $resultFile)
                Texte_Mail="$Texte_Mail\n$result"
                echo -e "$Texte_Mail" | mailx -s "$Titre_Mail" $Liste_Emails_Reef
    fi
else
    # Nothing to do
    echo "Nothing to do report ever send this month"
fi

exit 0
