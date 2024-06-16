#!/bin/sh

. $APPLICATION_RACINE/prod/bin/scripts/tools/setenv.sh

# Fichier shell de rattrapage des r?plications ISIS-BDC : Firmes et Agences

#Lancement du rattrapage
echo "DEBUT Lancement du Batch de r?plication pour rattrapage ISI_BDC"
#$BASE_EXE_DIR/REPLICATION/RAP/BatchReplication.sh -a BDC -n 100 -F 31121998000000
echo "Reprise Batch normal"
 $BASE_EXE_DIR/REPLICATION/RAP/BatchReplication.sh -a BDC -n 10000  
echo "FIN Lancement du Batch de r?plication pour rattrapage ISI_BDC"


# Fonctionnement normal :
# $BASE_EXE_DIR/REPLICATION/RAP/BatchReplication.sh -a BDC -n 100
