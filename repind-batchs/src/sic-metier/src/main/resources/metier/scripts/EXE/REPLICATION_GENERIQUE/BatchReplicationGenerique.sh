#!/bin/bash

#
#     SCRIPT PRINCIPAL de lancement en parallele de l'extraction
#        des fichiers generiques ( global / mouvements ) , suivi des
#        sous scripts shell (extension .rg) pour effectuer le dispatch
#        et mettre en place l'environnement d'exceution
#
#   Chapitres :
#      INITIALISATIONS
#      CONTROLES
#      EXTRACTION
#      MAJ_BD                :  maj derniere date de traitement en BD
#      DISPATCH
#
#####################################################################
#
#   ATTENTION
#   #########
#   - CONTROLE : respecter l'ordre des options (voir $USAGE)
#       les parametres pertinents sont stockes dans $_lstParams
#       option -o donne la racine des noms de fichier utilise
#       -C (tous les codes) est incompatible avec -c (par type)
#       les options suivant le -- seront directement transmises sans controle
#   - EXTRACTION :
#   - DISPATCH : les fichiers generes regroupent certaines lignes
#       exemple : Agence + Segmentation + ...
#       il faut les separer afin de faire un fichier par type de ligne
#   - MAJ_BD : une seule Maj de la date de traitement en Base donc ,
#       elle doit etre faite dans ce script
#       lorsque tous les traitements paralleles sont termines
#
###############################################################
#
# 12 Oct 2001 : Version initiale
# 30 Nov 2001 : DISPATCH
# mai 2004    : Refonte
#               Gestion d'un mode dispatch
#               Gestion d'un environnement de test
#               Split des fichiers volumineux (> 1,8Go)
# mars 2005    : Refonte suite passage � la replic en utl_file
#
#####################################################################

DEBUG="FALSE"         # flag de Debug

#--------------------#
#-- INITIALISATION --#
#--------------------#
echo "\n### INITIALISATION ###"

##################################################
### Declaration et Initialisation de Variables ###

USAGE="Usage: $0 -h | -C | -c Code -c Code ... [ -m ] [ -s AviFile ] [ -o FileOUT ] [-E EnvFile ] -- [ -dmnN.. ] \n\
ATTN les options avant le '--' sont interpretees par le shell, celles apres sont transmises au batch C++ \n\
Codes : 0110 , 0120 , 0130 , ... , 0210 , ... , 0310 , ... , 0410 , ... , 0910 , ...\n\
Usage: $0 -h | -D DispatchFile [-E EnvFile ]\n\
Date pour le batch C++ : format=YYYYMMDD ; null=19500101\n\
-m\tPour mise a jour de la date de replication en base\n\
-D\tMode de reprise (sans extraction des donnees) charge le script shell de definition des variables d'environnement du dispatch\n\
-E\tScript shell de definition de variables d'environnement"

# Variables environnement & constantes
PID=$$
_dateDuJour=`date '+%Y%m%d%H%M'`

REP_BIN="$BASE_EXE_CPP_DIR"
REP_RG="$BASE_EXE_DIR/REPLICATION_GENERIQUE"
REP_UF="$BASE_EXE_DIR/REPLIC_UTL_FILE"
REP_DATA="$BASE_DATA_DIR/REPLICATION_GENERIQUE"
REP_ENCOURS="$REP_DATA/ENCOURS"
REP_DISPATCH="$REP_DATA/DISPATCH"
REP_TRACE="$BASE_DIR/TRACES"


SEP=":#:"
_TRMT_OK="0090${SEP}TRAITEMENT_OK${SEP}"


# TO DO : Demander au binaire de fournir la liste des codes � traiter
# valeurs par defaut
CODE_LST="0110 0120 0130 0140 0150 0160 0210 0220 0230 0310 0320 0330 0410 0420 0430 0490 0910 0950"

# Pre-initialisation variable
_AllCodes="FALSE"     # Flag -C pour une extraction de tous les codes
_ByCode="FALSE"       # Flag -c pour une extraction code � code
_MajBD="FALSE"        # Flag -m pour maj date d'extraction en base

_isDispatch="FALSE"   # Flag -D pour le mode de reprise sans extraction de donnees
_FileDispatch=""      # Script shell de variables d'environnement (permet de faire les dispatch sans extraire les donnees)

_isFileOut="FALSE"
_FileOut=""           # Path/Racine des fichiers de sortie
_lstCode=""           # Codes a extraires passes en entre

_isBinaryParam="FALSE"
_lstParam=""          # Variable de stockage des parametres autre que les codes a passer au binaire

_isFileEnv="FALSE"
_FileEnv=""           # Script shell de variables d'environnement (permet de faire des tests)

_isFileAvi="FALSE"
_FileAvi=""           # Fichier AVI utilise par le scheduler MAESTRO
_isGlobal="FALSE"


### FIN declaration de Variables ###
####################################


###   Parametres
# ATTN pas de controle des options se trouvant apres le --
# Ce sont les options pour le binaire
#
# Plusieurs types d'options
# Options seulement pour le shell:
#    -h  Usage du script shell
#    -C  Utilisation de TOUS les codes
#    -m  Pour mise a jour de la date de replication en base
#    -s  Fichier AVI utilise par le scheduler MAESTRO, ce fichier permet
#        le declenchement du shell a date fixe
#    -D  Mode dispatch
#        Ce mode permet de lancer le dispatch sans relancer le binaire.
#        Il n'y a pas d'extraction des donnees de la base.
#        Le script shell en parametre contient les variables
#        d'environnement necessaires au traitement de reprise.
#        RG_DISPATCH_PID      : Nom du repertoire d'extraction
#        RG_DISPATCH_DIRNAME  : Path du repertoire qui servira a l'archivage
#                               des fichiers dispatches.
#        RG_DISPATCH_FILENAME : Racine des noms de fichiers de donnees
#                               d'origine.
#
#        exemple de path/nom de fichier de donnees :
#        PID : Nom du repertoire d'extraction
#        YYYYMMDDHHMI : date et heure de debut du traitement
#        .../EXE/REPLICATION_GENERIQUE/ENCOURS/[PID]/GLOBAL-[YYYYMMDDHHMI].txt
#        .../EXE/REPLICATION_GENERIQUE/ENCOURS/[PID]/MVT-[YYYYMMDDHHMI].txt
#    -E  Script shell de variables d'environnement
# Options pour le shell et le binaire: -c et -o
#    -c  [shell]    Permet de construire la liste de codes a utiliser
#        [binaire]  Un seul code a la fois
#    -o  [shell]    Path utilise pour archiver les fichiers dispatches
#        [binaire]  nom (racine) du fichier d'extraction de donnees

set -- `getopt hCs:mc:o:D:E: $*`
if [ $? != 0 ]
then
    echo $USAGE
    exit 0
fi
for i in $*
do
    case $i in
    -h) echo $USAGE; exit 1 ;;
    -C) _AllCodes="TRUE"; shift ;;
    -m) _MajBD="TRUE"; shift ;;
    -c) _ByCode="TRUE"; _lstCode="$_lstCode $2"; shift 2 ;;
    -D) _isDispatch="TRUE"; _FileDispatch="$2"; shift 2;;
    -o) _isFileOut="TRUE"; _FileOut="$2"; shift 2;;
    -s) _isFileAvi="TRUE"; _FileAvi="$2"; shift 2;;
    -E) _isFileEnv="TRUE"; _FileEnv="$2"; shift 2;;
    --) shift
        if [ -n "$*" ]
        then
	    echo "Des parametres pour le binaire"
	    _isBinaryParam="TRUE"
	    _lstParam="$_lstParam $* "
        fi
        break ;;
    esac
done
      echo "_lstParam = $_lstParam"
for i in $_lstParam
do
    case $i in
    -d) _isGLOBAL="TRUE"; shift 2;;
    -n) shift 2;;
    esac
done
      echo "_isGLOBAL = $_isGLOBAL"


# Declenchement sur fichier AVI
if [ "${_FileAvi:=NIX}" != NIX ]
then
  # Test existence fichier Avi
  if [ -f "$_FileAvi" ]
  then
    \rm $_FileAvi
  else
    echo "Pas de fichier Avi $_FileAvi"
    exit 0 # zero pour ne pas creer des abend dans maestro tous les jours
  fi
fi


if [ "${_isGLOBAL}" = "TRUE" ]
then
  $REP_RG/manageReplicationSic.pl -g
else
  $REP_RG/manageReplicationSic.pl -d
fi





