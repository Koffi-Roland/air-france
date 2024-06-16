#!/bin/sh

APP_WORK=$BASE_EXE_DIR/CLEAN_MEDIA
DATA_DIR=$BASE_DATA_DIR/CLEAN_MEDIA/EMAILS
EMAILDIR=$APP_WORK/EMAILS
EMAILDATADIR=$DATA_DIR/data
TASSEMENTDIR=$EMAILDIR/Tassement
TASSEMENTDATADIR=$EMAILDATADIR/TassementEmail
INVALIDATIONDIR=$EMAILDIR/InvalCarSpecial
INVALIDATIONDATADIR=$EMAILDATADIR/InvalCarSpecial
WEBPATTERNDIR=$EMAILDIR/WebPattern
WEBPATTERNDATADIR=$EMAILDATADIR/WebPattern


echo "Demarrage du shell d extraction des donnees"

jour=`date '+%d'`
mois=`date '+%b%Y'`

echo "##BATCH DE NETOYAGE DES Emails##"

echo "DEMARRAGE DES EXTRACTIONS et Traitement:"

echo "    - emails a tasser:\n"
sqlplus -s `secureSIC $SECURE_DATABASE_ACCESS_FILE_SIC READSIC` @$TASSEMENTDIR/select_email_tassement.sql > $TASSEMENTDATADIR/emails_tassement_$jour$mois.csv
echo "    - Fichier g�n�r� : emails_tassement_$jour$mois.csv "
echo "    - Fin extraction tassement"

echo "    - tassement emails:";
$TASSEMENTDIR/taser_b_emails.pl $TASSEMENTDATADIR/emails_tassement_$jour$mois.csv $1 $2 | tee $TASSEMENTDATADIR/emails_tassement_blank_$jour$mois.trace >> $DATA_DIR/Global_Clean_Emails_output_$jour$mois.trace
$TASSEMENTDIR/taser_tb_emails.pl $TASSEMENTDATADIR/emails_tassement_$jour$mois.csv $1 $2 | tee $TASSEMENTDATADIR/emails_tassement_tab_$jour$mois.trace >> $DATA_DIR/Global_Clean_Emails_output_$jour$mois.trace
echo "    - fin tassement emails";

echo "    - emails a netoyer et invalider:\n"
sqlplus -s `secureSIC $SECURE_DATABASE_ACCESS_FILE_SIC READSIC` @$INVALIDATIONDIR/select_emails_prd_ss_accens.sql > $INVALIDATIONDATADIR/emails_invalidation_$jour$mois.csv
echo "    - Fichier g�n�r� : emails_invalidation_$jour$mois.csv "
echo "    - Fin extraction invalidation"

echo "    - netoyage et invalidation emails:";
#touch $INVALIDATIONDATADIR/emails_invalidation_$jour$mois.trace
$INVALIDATIONDIR/rm_email_sp.pl -f $INVALIDATIONDATADIR/emails_invalidation_$jour$mois.csv $1 $2 | tee $INVALIDATIONDATADIR/emails_invalidation_$jour$mois.trace >> $DATA_DIR/Global_Clean_Emails_output_$jour$mois.trace
echo "    - fin netoyage emails";

echo "    - emails ne respectant pas le webpattern � invalider:"
sqlplus -s `secureSIC $SECURE_DATABASE_ACCESS_FILE_SIC READSIC` @$WEBPATTERNDIR/select_emails_prd_webpattern.sql > $WEBPATTERNDATADIR/emails_non_webpattern_$jour$mois.csv
echo "    - Fichier g�n�r� : emails_non_webpattern_$jour$mois.csv "
echo "    - Fin extraction webpattern"


echo "    - invalidation des emails ne respectant pas le webpattern:";
#touch $WEBPATTERNDATADIR/emails_non_webpattern_$jour$mois.trace
$WEBPATTERNDIR/emails_non_pattern_invalidation_v2.pl -f $WEBPATTERNDATADIR/emails_non_webpattern_$jour$mois.csv $1 $2 | tee $WEBPATTERNDATADIR/emails_non_webpattern_$jour$mois.trace >> $DATA_DIR/Global_Clean_Emails_output_$jour$mois.trace
echo "    - fin invalidation emails";

echo " ";