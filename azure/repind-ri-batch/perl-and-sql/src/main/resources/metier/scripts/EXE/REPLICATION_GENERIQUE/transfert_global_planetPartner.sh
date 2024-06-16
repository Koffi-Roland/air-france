#!/bin/bash
###############################################################################
# Envoi quotidien à Planet Partners des fichiers de replication globaux:
# 0160
# 0210
# 0400
# 0410
# 0420
# 0480
# 0490
# 0920
# 0930 
# 0950
################################################################################

USAGE="Usage: $0 date_global\n\
Example:\n\
\t$0 200412171900"

if [ $# -ne 1 ]
then
    echo "Wrong number of command-line arguments. $#\n"
    echo $USAGE
    exit 1
fi

# Main
DATE_GLOBAL=$1

_code=""
_flux=""

############################################################
# Fonction utilisant les nouveaux transfert interpel via XFB
codeToProcess() {
    echo "\n-------------------------"
    echo "gwqvi1.sh DWH ${_code} pour le flux ${_flux}..."
   
    # Evaluation du fichier global
    FILEGLOBAL=${EVAL_DIRNAME}/GLOBAL-$DATE_GLOBAL.${_code}.txt.gz
    EVAL_FILEGLOBAL=`ls $FILEGLOBAL 2>/dev/null`

    if [ ! -f "$EVAL_FILEGLOBAL" ]
    then
        echo "Fichier inexistant, $FILEGLOBAL"
        echo "Pas de transfert."
        return
    fi

    baseFileName=`basename "$EVAL_FILEGLOBAL"`

    # Transfert du fichier
    gwqvi1.sh $EVAL_FILEGLOBAL ${_flux} $baseFileName
    echo "Transfert effectue, code retour: $?"
}

# Evaluation du repertoire de travail
DIR_REP_GEN_GLOBAL=$BASE_DATA_DIR/REPLICATION_GENERIQUE/GLOBAL/GLOBAL-$DATE_GLOBAL
EVAL_DIRNAME=`ls -d $DIR_REP_GEN_GLOBAL 2>/dev/null`

if [ ! -d "$EVAL_DIRNAME" ]
then
    echo "Repertoire des fichiers globaux inexistant, $DIR_REP_GEN_GLOBAL"
    echo "Pas de transfert."
    exit
fi

########################################
# Detection PRODUCTION ou RECETTE
if [ $hostType = "prd" ]
then
    ####################################################
    # Transfert des flux globaux de la prod via XFB
    for CodeFlux in 0160:PRAPAR \
		    0210:PRAPAR \
		    0400:PRAPAR \
		    0410:PRAPAR \
		    0420:PRAPAR \
		    0480:PRAPAR \
		    0490:PRAPAR \
		    0920:PRAPAR \
 		    0930:PRAPAR \
 		    0950:PRAPAR
    do
        echo $CodeFlux
        _code=`echo $CodeFlux | awk 'BEGIN{FS=":"}{print $1}'`
        _flux=`echo $CodeFlux | awk 'BEGIN{FS=":"}{print $2}'`
	codeToProcess
	_code=""
	_flux=""
    done 

elif [ $hostType = "rct" ]
then
    ####################################################
    # Transfert des flux globaux de la recette via XFB
    for CodeFlux in 0160:RRAPAR \
		    0210:RRAPAR \
		    0400:RRAPAR \
		    0410:RRAPAR \
		    0420:RRAPAR \
		    0480:RRAPAR \
		    0490:RRAPAR \
		    0920:RRAPAR \
 		    0930:RRAPAR \
 		    0950:RRAPAR
    do
        echo $CodeFlux
        _code=`echo $CodeFlux | awk 'BEGIN{FS=":"}{print $1}'`
        _flux=`echo $CodeFlux | awk 'BEGIN{FS=":"}{print $2}'`
	codeToProcess
	_code=""
	_flux=""
    done 
fi