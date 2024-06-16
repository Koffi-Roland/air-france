 #!/bin/sh

APP_REF=$BASE_EXE_DIR/EXPLOIT/INDICATEURS
APP_REF_DATA=$BASE_DATA_DIR/CLEAMEDIA/
APP_REF_SQL=$BASE_EXE_DIR/EXPLOIT/INDICATEURS/SQL
DIR_EXE=$BASE_EXE_DIR
APP_WORK=$BASE_EXE_DIR/CLEAN_MEDIA
DATA_DIR=$BASE_DATA_DIR/CLEAN_MEDIA/ADR_POST
ListMailDICF="LDIF_REEF_TMA@airfrance.fr"
ADRPOSTDIR=$APP_WORK/ADR_POST
ADRPOSTDATADIR=$DATA_DIR/data
DESACCENTUATIONDIR=$ADRPOSTDIR/Desaccentuation
DESACCENTUATIONDATADIR=$ADRPOSTDATADIR/Desaccentuation
INVALCARSPECIALDIR=$ADRPOSTDIR/InvalCarSpecial
INVALCARSPECIALDATADIR=$ADRPOSTDATADIR/InvalCarSpecial
BLANCHIMENTDIR=$ADRPOSTDIR/Blanchiment
BLANCHIMENTDATADIR=$ADRPOSTDATADIR/Blanchiment


echo "Demarrage du shell d extraction des donnees des adresses postale"

jour=`date '+%d'`
mois=`date '+%b%Y'`

echo "##BATCH DE NETOYAGE DES ADR POST##"

echo "DEMARRAGE DES EXTRACTIONS et Traitement:"

echo "    - adr post  desaccentuer:"
sqlplus -s `secureSIC $SECURE_DATABASE_ACCESS_FILE_SIC READSIC` @$DESACCENTUATIONDIR/select_adress_acc_prd.sql > $DESACCENTUATIONDATADIR/adr_post_acc_$jour$mois.csv
echo "      Fichier giniri : adr_post_acc_$jour$mois.csv"
echo "    - Fin extraction accens"

echo "    - desaccentuation adr post:";
$DESACCENTUATIONDIR/rm_sp_adr_acc.pl -f $DESACCENTUATIONDATADIR/adr_post_acc_$jour$mois.csv $1 $2 | tee $DESACCENTUATIONDATADIR/adr_post_acc_$jour$mois.trace >> $DATA_DIR/Global_Clean_Adr_Post_output_$jour$mois.trace
echo "    - fin desaccentuation adr post";

echo "    - adr post contenant des diez  blanchir:"
sqlplus -s `secureSIC $SECURE_DATABASE_ACCESS_FILE_SIC READSIC` @$BLANCHIMENTDIR/select_adress_diez_prd.sql > $BLANCHIMENTDATADIR/adr_post_diez_$jour$mois.csv
echo "      Fichier giniri : adr_post_diez_$jour$mois.csv"
echo "    - Fin extraction diez"

echo "    - blanchiment adr post:";
$BLANCHIMENTDIR/rm_sp_adr_diez.pl -f $BLANCHIMENTDATADIR/adr_post_diez_$jour$mois.csv $1 $2 | tee $BLANCHIMENTDATADIR/adr_post_diez_$jour$mois.trace >> $DATA_DIR/Global_Clean_Adr_Post_output_$jour$mois.trace
echo "    - fin blanchiment adr post";


echo "    - adr post ISI  nettoyer:"
sqlplus -s `secureSIC $SECURE_DATABASE_ACCESS_FILE_SIC READSIC` @$INVALCARSPECIALDIR/select_adress_sacc_prd.sql > $INVALCARSPECIALDATADIR/adr_post_ISI_$jour$mois.csv
echo "      Fichier genere : adr_post_ISI_$jour$mois.csv"

#echo "    - adr post MYA  nettoyer:"
#sqlplus -s `secureSIC $SECURE_DATABASE_ACCESS_FILE_SIC READSIC` @$INVALCARSPECIALDIR/select_adress_mya_sum_prd.sql > $INVALCARSPECIALDATADIR/adr_post_MYA_$jour$mois.csv
#echo "      Fichier giniri : adr_post_MYA_$jour$mois.csv"
echo "    - Fin extraction des caracthres spiciaux"


echo "    - d'invalidation adr post:";
#touch $INVALCARSPECIALDATADIR/adr_post_ISI_$jour$mois.trace
$INVALCARSPECIALDIR/rm_sp_adr_sacc.pl -f $INVALCARSPECIALDATADIR/adr_post_ISI_$jour$mois.csv $1 $2 | tee $INVALCARSPECIALDATADIR/adr_post_ISI_$jour$mois.trace >> $DATA_DIR/Global_Clean_Adr_Post_output_$jour$mois.trace

touch $INVALCARSPECIALDATADIR/adr_post_MYA_$jour$mois.trace
#$INVALCARSPECIALDIR/rm_sp_adr_mya.pl -f $INVALCARSPECIALDATADIR/adr_post_MYA_$jour$mois.csv $1 $2 | tee $INVALCARSPECIALDATADIR/adr_post_MYA_$jour$mois.trace >> $DATA_DIR/Global_Clean_Adr_Post_output_$jour$mois.trace
echo "    - fin d'invalidation adr post";