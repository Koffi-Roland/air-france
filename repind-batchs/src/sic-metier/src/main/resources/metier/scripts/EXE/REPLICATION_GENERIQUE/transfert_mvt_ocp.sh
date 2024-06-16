#!/bin/sh
###############################################################################
# envoi quotidien a OCP des fichiers de replication par mouvement,
# pour les codes 110, 210, 230,
# sur les flux pelican ROCPO1..4
# --------------------------------------
# 12 Mai  2016     Ajout flux pour code 0110 0210 0230
###############################################################################

# Fonction utilisant les nouveaux transfert interpel
codeToProcess() {
    echo "\n-------------------------"
    echo "gwqvi1.sh OCP ${_code} pour le flux ${_flux}..."

    # Evaluation du fichier de mouvement
    FILEMVT=${EVAL_DIRNAME}/MVT-$DATE_DU_JOUR????.${_code}.txt.gz
    EVAL_FILEMVT=`ls $FILEMVT 2>/dev/null`

    if [ ! -f "$EVAL_FILEMVT" ]
    then
        echo "Fichier inexistant, $FILEMVT"
        echo "Pas de transfert."
        return
    fi

    # Transfert du fichier
    gwqvi1.sh $EVAL_FILEMVT ${_flux} MVT-$DATE_DU_JOUR"0100".${_code}.txt.gz
    echo "Transfert effectue, code retour: $?"
}

######
# Main
DATE_DU_JOUR=`date '+%Y%m%d'`

# Evaluation du repertoire de travail
DIR_REP_GEN_MVT=$BASE_DATA_DIR/REPLICATION_GENERIQUE/MVT/MVT-$DATE_DU_JOUR????
EVAL_DIRNAME=`ls -d $DIR_REP_GEN_MVT 2>/dev/null`

if [ ! -d "$EVAL_DIRNAME" ]
then
    echo "R�pertoire des fichiers de mouvement inexistant, $DIR_REP_GEN_MVT"
    echo "Pas de transfert."
    exit
fi
########################################
# Detection PRODUCTION ou RECETTE
if [ $hostType = "rct" ]
then

    ##########################################################
    # Transfert des flux de mouvement de la production via XFB
    for CodeFlux in 0110:ROCPO1 \
        0230:ROCPO3 \
        0320:ROCPO4
    do
        echo $CodeFlux
        _code=`echo $CodeFlux | awk 'BEGIN{FS=":"}{print $1}'`
        _flux=`echo $CodeFlux | awk 'BEGIN{FS=":"}{print $2'}`
        codeToProcess
	_code=""
	_flux=""
    done
  
elif [ $hostType = "prd" ]
then
    #######################################################
    # Transfert des flux de mouvement de la recette via XFB
    for CodeFlux in 0110:POCPO1 \
		0230:POCPO3 \
		0320:POCPO4
                    
    do
        echo $CodeFlux
        _code=`echo $CodeFlux | awk 'BEGIN{FS=":"}{print $1}'`
        _flux=`echo $CodeFlux | awk 'BEGIN{FS=":"}{print $2'}`
	codeToProcess
	_code=""
	_flux=""
    done
fi



