#!/bin/bash

########TRAITEMENT DES FICHIERS PROVENANT DE LA REPLICATION GENERIQUE  #######
# --------------------------------------
# 02 Septembre  2020     Préparation en vue de l'ajout dans l'orchestration du 
#                        batch de réplication générique (REPIND-1894)
##############################################################################
DATE_DU_JOUR=`date '+%Y%m%d'`


# Fonction permettant de transferer un fichier de mouvement de la
# replication generique sur un flux
_code=""
_flux=""
codeToProcess() {
    echo "\n-------------------------"
    echo "gwqvi1.sh DWH ${_code} pour le flux ${_flux}..."

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
    gwqvi1.sh $EVAL_FILEMVT ${_flux}
    echo "Transfert effectue, code retour: $?"
}

# Main
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
if [ "$hostType" = "prd" ]
then

    ##########################################################
    # Transfert des flux de mouvement de la production via XFB  
    for CodeFlux in 0130:SIDX60 \
		    0140:SIDX61 \
		    0150:SIDX62 \
		    0160:SIDX75 \
		    0170:SIDX77 \
		    0310:SIDX83 \
		    0340:SIDX89 \
		    0400:SIDX59 \
		    0410:SIDX79 \
		    0420:SIDX81 \
		    0430:SIDX63 \
		    0480:SIDX67 \
		    0490:SIDX68 \
		    0840:SIDX70 \
		    0860:SIDX69 \
		    0910:SIDX73 \
		    0920:SIDX65 \
		    0930:SIDX66 \
		    0940:SIDX64 \
		    0950:SIDX74 \
		    0960:SIDX71 \
		    0970:SIDX85 \
		    0980:SIDX87	\
		    0810:SIDX90 \
		    0820:SIDX91 \
		    0830:SIDX92 \
		    0990:SIDX93
    do

       echo $CodeFlux
        _code=`echo $CodeFlux | awk 'BEGIN{FS=":"}{print $1}'`
        _flux=`echo $CodeFlux | awk 'BEGIN{FS=":"}{print $2'}`
       codeToProcess
	_code=""
	_flux=""
    done
fi
