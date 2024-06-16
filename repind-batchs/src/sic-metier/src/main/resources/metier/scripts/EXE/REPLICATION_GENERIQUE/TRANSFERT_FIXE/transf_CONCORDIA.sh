#!/bin/sh
# Param 1 : nom absolu du repertoire (date) de stockage des MVT 
#     les fichiers de Mvt y sont zippes. 

# Repertoire d'execution et de stockage des fichiers avant transfert
LocalDir="$BASE_EXE_DIR/REPLICATION_GENERIQUE/TRANSFERT_FIXE"
# Nom du repertoire de Mouvement date (= Racine des fichiers de mouvement)
BaseName=`basename $1`
DayDir=$BaseName
DataDir=`dirname $1`
echo "Nouveau r�pertoire contenant le shell envoyant les flux : $ShellFlux"

echo "#########################################"
echo "##   TRAITEMENT FICHIER 0160 Agence    ##"
echo "#########################################"

if [ -s $DataDir/$DayDir/$BaseName.0160.txt.gz ] 
then
  echo "Fichier Existant : $DataDir/$DayDir/$BaseName.0160.txt.gz";
  echo "Copie vers le repertoire de transfert" 
  echo "cp "$DataDir/$DayDir/$BaseName.0160.txt.gz" "$BASE_DATA_DIR
  cp $DataDir/$DayDir/$BaseName.0160.txt.gz $BASE_DATA_DIR
  echo "cd "$BASE_DATA_DIR
  cd $BASE_DATA_DIR
  echo "gzip -d "$BaseName.0160.txt.gz
  gzip -d $BaseName.0160.txt.gz
  echo "Renommage du fichier avant envoi"
  echo "mv "$BaseName.0160.txt "DREFINT_SAPDPS100_EDREF01"
  mv $BaseName.0160.txt DREFINT_SAPDPS100_EDREF01
 
  # Transfert BipBip vers CONCORDIA
  echo "gwqvi1.sh DREFINT_SAPDPS100_EDREF01 TT0160"
  gwqvi1.sh DREFINT_SAPDPS100_EDREF01 TT0160 DREFINT_SAPUAT100_EDREF01
  echo "Suppression du fichier"
  \rm -f DREFINT_SAPDPS100_EDREF01
else
 echo "Fichier Introuvable: $DataDir/$DayDir/$BaseName.0160.txt.gz";
fi


echo "#########################################"
echo "##   TRAITEMENT FICHIER 0410           ##"
echo "#########################################"

if [ -s $DataDir/$DayDir/$BaseName.0410.txt.gz ] 
then
  echo "Fichier Existant : $DataDir/$DayDir/$BaseName.0410.txt.gz";
  echo "Copie vers le repertoire de transfert"
  cp $DataDir/$DayDir/$BaseName.0410.txt.gz $BASE_DATA_DIR
  cd $BASE_DATA_DIR
  echo "gzip" 
  gzip -d $BaseName.0410.txt.gz
  echo "Renommage du fichier avant envoi"
  mv $BaseName.0410.txt DREFINT_SAPDPS100_EDREF05

  # Transfert BipBip vers CONCORDIA
  gwqvi1.sh DREFINT_SAPDPS100_EDREF05 TT0410 DREFINT_SAPUAT100_EDREF05
  echo "Suppression du fichier"
  \rm -f DREFINT_SAPDPS100_EDREF05
else
 echo "Fichier Introuvable: $DataDir/$DayDir/$BaseName.0410.txt.gz";
fi


echo "#########################################"
echo "##   TRAITEMENT FICHIER 0400           ##"
echo "#########################################"

if [ -s $DataDir/$DayDir/$BaseName.0400.txt.gz ] 
then
  echo "Fichier Existant : $DataDir/$DayDir/$BaseName.0400.txt.gz";
  echo "Copie vers le repertoire de transfert"
  cp $DataDir/$DayDir/$BaseName.0400.txt.gz $BASE_DATA_DIR
  cd $BASE_DATA_DIR
  echo "gzip"
  gzip -d $BaseName.0400.txt.gz 
  echo "Renommage du fichier avant envoi"
  mv $BaseName.0400.txt DREFINT_SAPDPS100_EDREF04

  # Transfert BipBip vers CONCORDIA
  gwqvi1.sh DREFINT_SAPDPS100_EDREF04 TT0400 DREFINT_SAPUAT100_EDREF04
  echo "Suppression du fichier"
  \rm -f DREFINT_SAPDPS100_EDREF04
else
 echo "Fichier Introuvable: $DataDir/$DayDir/$BaseName.0400.txt.gz";
fi


echo "#########################################"
echo "##   TRAITEMENT FICHIER 0210           ##"
echo "#########################################"

if [ -s $DataDir/$DayDir/$BaseName.0210.txt.gz ] 
then
  echo "Fichier Existant : $DataDir/$DayDir/$BaseName.0210.txt.gz";
  echo "Copie vers le repertoire de transfert"
  cp $DataDir/$DayDir/$BaseName.0210.txt.gz $BASE_DATA_DIR
  cd $BASE_DATA_DIR
  echo "gzip"
  gzip -d $BaseName.0210.txt.gz 
  echo "Renommage du fichier avant envoi"
  mv $BaseName.0210.txt DREFINT_SAPDPS100_EDREF02

  # Transfert BipBip vers CONCORDIA
  gwqvi1.sh DREFINT_SAPDPS100_EDREF02 TT0210 DREFINT_SAPUAT100_EDREF02
  echo "Suppression du fichier"
  \rm -f DREFINT_SAPDPS100_EDREF02
else
 echo "Fichier Introuvable: $DataDir/$DayDir/$BaseName.0210.txt.gz";
fi


echo "#########################################"
echo "##   TRAITEMENT FICHIER 0930           ##"
echo "#########################################"

if [ -s $DataDir/$DayDir/$BaseName.0930.txt.gz ] 
then
  echo "Fichier Existant : $DataDir/$DayDir/$BaseName.0930.txt.gz";
  echo "Copie vers le repertoire de transfert"
  cp $DataDir/$DayDir/$BaseName.0930.txt.gz $BASE_DATA_DIR
  cd $BASE_DATA_DIR
  echo "gzip"
  gzip -d $BaseName.0930.txt.gz 
  echo "Renommage du fichier avant envoi"
  mv $BaseName.0930.txt DREFINT_SAPDPS100_EDREF10

  # Transfert BipBip vers CONCORDIA
  gwqvi1.sh DREFINT_SAPDPS100_EDREF10 TT0930 DREFINT_SAPUAT100_EDREF10
  echo "Suppression du fichier"
  \rm -f DREFINT_SAPDPS100_EDREF10
else
 echo "Fichier Introuvable: $DataDir/$DayDir/$BaseName.0930.txt.gz";
fi


echo "#########################################"
echo "##   TRAITEMENT FICHIER 0950           ##"
echo "#########################################"

if [ -s $DataDir/$DayDir/$BaseName.0950.txt.gz ] 
then
  echo "Fichier Existant : $DataDir/$DayDir/$BaseName.0950.txt.gz";
  echo "Copie vers le repertoire de transfert"
  cp $DataDir/$DayDir/$BaseName.0950.txt.gz $BASE_DATA_DIR
  cd $BASE_DATA_DIR
  echo "gzip"
  gzip -d $BaseName.0950.txt.gz 
  echo "Renommage du fichier avant envoi"
  mv $BaseName.0950.txt DREFINT_SAPDPS100_EDREF11

  # Transfert BipBip vers CONCORDIA
  gwqvi1.sh DREFINT_SAPDPS100_EDREF11 TT0950 DREFINT_SAPUAT100_EDREF11
  echo "Suppression du fichier"
  \rm -f DREFINT_SAPDPS100_EDREF11
else
 echo "Fichier Introuvable: $DataDir/$DayDir/$BaseName.0950.txt.gz";
fi


