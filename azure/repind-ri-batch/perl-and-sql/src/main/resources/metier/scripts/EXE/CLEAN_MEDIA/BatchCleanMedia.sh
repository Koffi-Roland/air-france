#!/bin/sh

APP_REF=$BASE_EXE_DIR/EXPLOIT/INDICATEURS
APP_REF_DATA=$BASE_DATA_DIR/CLEAMEDIA/
APP_REF_SQL=$BASE_EXE_DIR/EXPLOIT/INDICATEURS/SQL
DIR_EXE=$BASE_EXE_DIR
APP_WORK=$BASE_EXE_DIR/CLEAN_MEDIA
DATA_DIR=$BASE_DATA_DIR/CLEAN_MEDIA
EMAIL_DATA_DIR=$DATA_DIR/EMAILS
ListMailDICF="LDIF_REEF_TMA@airfrance.fr"
EMAILDIR=$APP_WORK/EMAILS
EMAILDATADIR=$EMAIL_DATA_DIR/data
TASSEMENTDIR=$EMAILDIR/Tassement
TASSEMENTDATADIR=$EMAILDATADIR/TassementEmail
INVALIDATIONDIR=$EMAILDIR/InvalCarSpecial
INVALIDATIONDATADIR=$EMAILDATADIR/InvalCarSpecial
WEBPATTERNDIR=$EMAILDIR/WebPattern
WEBPATTERNDATADIR=$EMAILDATADIR/WebPattern
ADRPOSTDIR=$APP_WORK/ADR_POST
ADR_POST_DATA_DIR=$DATA_DIR/ADR_POST
ADRPOSTDATADIR=$ADR_POST_DATA_DIR/data
DESACCENTUATIONDIR=$ADRPOSTDIR/Desaccentuation
DESACCENTUATIONDATADIR=$ADRPOSTDATADIR/Desaccentuation
INVALCARSPECIALDIR=$ADRPOSTDIR/InvalCarSpecial
INVALCARSPECIALDATADIR=$ADRPOSTDATADIR/InvalCarSpecial
BLANCHIMENTDIR=$ADRPOSTDIR/Blanchiment
BLANCHIMENTDATADIR=$ADRPOSTDATADIR/Blanchiment

Titre_Mail="Extracting mails treated containing blank or special characters"
Texte_Mail="*** THIS IS A SYSTEM GENERATED MESSAGE, PLEASE DO NOT REPLY TO THIS EMAIL ***

See attached files :
        1. Attached file email*.csv are the input coming from Extractions Cleaning Media, that includes all the data befor treatment.
        2. Attached file email*.trace are the output of the cleaning email execution .
        3. Attached file adr_post*.csv are the input coming from Extractions Cleaning Media, that includes all the data befor treatment.
        4. Attached file adr_post*.trace are the output of the cleaning postal adress execution.
        5. Attached file Global_Clean*.trace are the output of the all cleaning batch execution.

For further details, please contact : LDIF_REEF_TMA@airfrance.fr or mail.sic.it

Thank you."
ResBatch="$DATA_DIR/ResBatchCleanMedia.txt"
Liste_Emails_Reef="mail.sic.it@airfrance.fr"
#to get the type of the machine (dev, prd, rct)
if [ "$hostType" = "dev" ]
		then
        Liste_Emails_Reef="lotomponiony@airfrance.fr"
fi
if [ "$hostType" = "rct" ]
        then
        Liste_Emails_Reef="mail.sic.it@airfrance.fr"
fi

jour=`date '+%d'`
mois=`date '+%b%Y'`


#Execution of emails treatment
echo "Execution cleaning email"
$EMAILDIR/BatchCleanEmails.sh $1 $2 > $EMAIL_DATA_DIR/Global_Clean_Emails_output_$jour$mois.trace
echo " Cleaning email termin�, trace dans /app/REPIND/data/ClEAN_MEDIA/EMAILS/Global_Clean_Emails_output_$jour$mois.trace"
#Execution of adr_post treatment
echo "Execution cleaning adr_post"
$ADRPOSTDIR/BatchCleanAdrPost.sh $1 $2 > $ADR_POST_DATA_DIR/Global_Clean_Adr_Post_output_$jour$mois.trace
echo " Cleaning adresses postales termin�, trace dans /app/REPIND/data/ClEAN_MEDIA/ADR_POST/Global_Clean_Adr_Post_output_$jour$mois.trace"

#ZIP email
echo "Envoi r�sultat par email"
zip $DATA_DIR/CleanMedia$jour$mois.zip $TASSEMENTDATADIR/emails_tassement_$jour$mois.csv $INVALIDATIONDATADIR/emails_invalidation_$jour$mois.csv $WEBPATTERNDATADIR/emails_non_webpattern_$jour$mois.csv $TASSEMENTDATADIR/emails_tassement_blank_$jour$mois.trace $TASSEMENTDATADIR/emails_tassement_tab_$jour$mois.trace $INVALIDATIONDATADIR/emails_invalidation_$jour$mois.trace $WEBPATTERNDATADIR/emails_non_webpattern_$jour$mois.trace $DESACCENTUATIONDATADIR/adr_post_acc_$jour$mois.csv $INVALCARSPECIALDATADIR/adr_post_ISI_$jour$mois.csv $INVALCARSPECIALDATADIR/adr_post_MYA_$jour$mois.csv $DESACCENTUATIONDATADIR/adr_post_acc_$jour$mois.trace $INVALCARSPECIALDATADIR/adr_post_ISI_$jour$mois.trace $INVALCARSPECIALDATADIR/adr_post_MYA_$jour$mois.trace $EMAIL_DATA_DIR/Global_Clean_Emails_output_$jour$mois.trace $ADR_POST_DATA_DIR/Global_Clean_Adr_Post_output_$jour$mois.trace $BLANCHIMENTDATADIR/adr_post_diez_$jour$mois.csv $BLANCHIMENTDATADIR/adr_post_diez_$jour$mois.trace
echo "$Texte_Mail" > $ResBatch
cat $ResBatch | mailx -s "$Titre_Mail" -a $DATA_DIR/CleanMedia$jour$mois.zip $Liste_Emails_Reef

rm $DATA_DIR/CleanMedia$jour$mois.zip
rm $DATA_DIR/ResBatchCleanMedia.txt