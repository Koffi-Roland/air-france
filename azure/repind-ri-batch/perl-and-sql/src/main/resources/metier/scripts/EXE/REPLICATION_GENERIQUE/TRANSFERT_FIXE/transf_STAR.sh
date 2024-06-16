#!/bin/sh
# Param 1 : nom absolu du repertoire (date) de stockage des MVT 
#     les fichiers de Mvt y sont zippes. 

# Repertoire d'execution et de stockage des fichiers avant transfert
LocalDir="$BASE_EXE_DIR/REPLICATION_GENERIQUE/TRANSFERT_FIXE"
# Nom du repertoire de Mouvement date (= Racine des fichiers de mouvement)
BaseName=`basename $1`
DayDir=$BaseName
# Repertoire d'archivage des MVTs
DataDir=`dirname $1`
#DataDir="$BASE_DATA_DIR/REPLICATION_GENERIQUE/TRANSFERT_FIXE"
ShellFlux="/exploit/xfb"
echo "Nouveau r�pertoire contenant le shell envoyant les flux : $ShellFlux"

echo "#########################################"
echo "##   TRAITEMENT FICHIER 0160 Agence    ##"
echo "#########################################"

if [ -s $DataDir/$DayDir/$BaseName.0160.txt.gz ] 
then
  echo "Fichier Existant : $DataDir/$DayDir/$BaseName.0160.txt.gz";
  gunzip -c $DataDir/$DayDir/$BaseName.0160.txt.gz > $BASE_DATA_DIR/$BaseName.0160.txt
 
  # Transfert BipBip vers STAR
  $ShellFlux/gwqvi1.sh $BASE_DATA_DIR/$BaseName.0160.txt ST0160
  \rm $BASE_DATA_DIR/$BaseName.0160.txt
else
 echo "Fichier Introuvable: $DataDir/$DayDir/$BaseName.0160.txt.gz";
fi


echo "#########################################"
echo "##   TRAITEMENT FICHIER 0930           ##"
echo "#########################################"

if [ -s $DataDir/$DayDir/$BaseName.0930.txt.gz ] 
then
  echo "Fichier Existant : $DataDir/$DayDir/$BaseName.0930.txt.gz";
  gunzip -c $DataDir/$DayDir/$BaseName.0930.txt.gz > $BASE_DATA_DIR/$BaseName.0930.txt

  # Transfert BipBip vers STAR
  $ShellFlux/gwqvi1.sh $BASE_DATA_DIR/$BaseName.0930.txt ST0930
  \rm $BASE_DATA_DIR/$BaseName.0930.txt
else
 echo "Fichier Introuvable: $DataDir/$DayDir/$BaseName.0930.txt.gz";
fi


echo "#########################################"
echo "##   TRAITEMENT FICHIER 0410           ##"
echo "#########################################"

if [ -s $DataDir/$DayDir/$BaseName.0410.txt.gz ] 
then
  echo "Fichier Existant : $DataDir/$DayDir/$BaseName.0410.txt.gz";
  gunzip -c $DataDir/$DayDir/$BaseName.0410.txt.gz > $BASE_DATA_DIR/$BaseName.0410.txt

  # Transfert BipBip vers STAR
  $ShellFlux/gwqvi1.sh $BASE_DATA_DIR/$BaseName.0410.txt ST0410
  \rm $BASE_DATA_DIR/$BaseName.0410.txt
else
 echo "Fichier Introuvable: $DataDir/$DayDir/$BaseName.0410.txt.gz";
fi

echo "#########################################"
echo "##   TRAITEMENT FICHIER 0400           ##"
echo "#########################################"

if [ -s $DataDir/$DayDir/$BaseName.0400.txt.gz ] 
then
  echo "Fichier Existant : $DataDir/$DayDir/$BaseName.0400.txt.gz";
  gunzip -c $DataDir/$DayDir/$BaseName.0400.txt.gz > $BASE_DATA_DIR/$BaseName.0400.txt

  # Transfert BipBip vers STAR
  $ShellFlux/gwqvi1.sh $BASE_DATA_DIR/$BaseName.0400.txt ST0400
  \rm $BASE_DATA_DIR/$BaseName.0400.txt
else
 echo "Fichier Introuvable: $DataDir/$DayDir/$BaseName.0400.txt.gz";
fi

echo "#########################################"
echo "##   TRAITEMENT FICHIER 0210           ##"
echo "#########################################"

if [ -s $DataDir/$DayDir/$BaseName.0210.txt.gz ] 
then
  echo "Fichier Existant : $DataDir/$DayDir/$BaseName.0210.txt.gz";
  gunzip -c $DataDir/$DayDir/$BaseName.0210.txt.gz > $BASE_DATA_DIR/$BaseName.0210.txt

  # Transfert BipBip vers STAR
  $ShellFlux/gwqvi1.sh $BASE_DATA_DIR/$BaseName.0210.txt ST0210
  \rm $BASE_DATA_DIR/$BaseName.0210.txt
else
 echo "Fichier Introuvable: $DataDir/$DayDir/$BaseName.0210.txt.gz";
fi

echo "#########################################"
echo "##   TRAITEMENT FICHIER 0212           ##"
echo "#########################################"

if [ -s $DataDir/$DayDir/$BaseName.0212.txt.gz ] 
then
  echo "Fichier Existant : $DataDir/$DayDir/$BaseName.0212.txt.gz";
  gunzip -c $DataDir/$DayDir/$BaseName.0212.txt.gz > $BASE_DATA_DIR/$BaseName.0212.txt

  # Transfert vers STAR
  $ShellFlux/gwqvi1.sh $BASE_DATA_DIR/$BaseName.0212.txt ST0212
  \rm $BASE_DATA_DIR/$BaseName.0212.txt
else
 echo "Fichier Introuvable: $DataDir/$DayDir/$BaseName.0212.txt.gz";
fi

echo "#########################################"
echo "##   TRAITEMENT FICHIER 0950           ##"
echo "#########################################"

if [ -s $DataDir/$DayDir/$BaseName.0950.txt.gz ] 
then
  echo "Fichier Existant : $DataDir/$DayDir/$BaseName.0950.txt.gz";
  gunzip -c $DataDir/$DayDir/$BaseName.0950.txt.gz > $BASE_DATA_DIR/$BaseName.0950.txt

  # Transfert BipBip vers STAR
  $ShellFlux/gwqvi1.sh $BASE_DATA_DIR/$BaseName.0950.txt ST0950
  \rm $BASE_DATA_DIR/$BaseName.0950.txt
else
 echo "Fichier Introuvable: $DataDir/$DayDir/$BaseName.0950.txt.gz";
fi
