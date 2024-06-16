#!/bin/bash
###############################################################################
# Envoi quotidien au DWH des fichiers de replication globaux,
# --------------------------------------
# 16 Juin 2007 :    Creation flux en recette pour les codes :
# 0100,0110,0121,0201,0210,0220,0230,0241,0251,0261,0330
# 16 Juillet 2007 : Creation flux en recette pour es codes :
# 0180, 0190, 0340, 0990 
# 16 Juillet 2007 : Creation flux en prod pour les codes :
# 0100,0110,0121,0180, 0190,0201,0210,0220,0230,0241,0251,0261,0330,0340, 0990
# 7 Novembre 2014 : Creation flux en recette pour les codes : 
# 0270, 0280 
# 13 Septembre 2016 : ajout des flux
#   - TALG01 et RALG01 pour le fichier 290 en recette et prod
#   - TALG02 et RALG02 pour le fichier 300 en recette et prod
#   - TALG03 et RALG03 pour le fichier 1014 en recette et prod
# 04 Novembre 2016 : ajout des flux
#   - TALG04 et RALG04 pour le fichier 2500 en recette et prod
#   - TALG05 et RALG05 pour le fichier 2600 en recette et prod
# 28 Octobre 2016 : ajout des flux
#   - TCPG01 et RCPG01 pour le fichier 1008 en recette et prod
#   - TCPG02 et RCPG02 pour le fichier 1009 en recette et prod
#   - TCPG03 et RCPG03 pour le fichier 1010 en recette et prod
#   - TCPG04 et RCPG04 pour le fichier 1011 en recette et prod
#   - TCPG05 et RCPG05 pour le fichier 1012 en recette et prod
#   - TCPG06 et RCPG06 pour le fichier 1013 en recette et prod
# 22 Novembre 2016 : ajout dex flux
#	- RDDG01 et TDDG01 pour le fichier 0101 en recette et prod
# 21 Mars : ajout dex flux
#	- RTRG01 et TTRG01 pour le fichier 0111 en recette et prod
# 28 Mars : modification des flux du 111
#	- Le 111 va etre envoye via flux du 110, soit RUVC09 et TUVC09 et 
#         on n'envoie plus le 110
# 16 Juin 2017 : ajout d'un nouveau flux
#       - PNDG01 et RNDG01 pour le fichier 1015 en recette et en prod
# 27 Mars 2019 : ajout des flux pour l'application RIPORTAL
# 	-le fichier 0160 va etre envoye via les flux RG0160 en recette et SG0160 en prod
#	-le fichier 0170 va etre envoye via les flux RG0170 et recette et SG0170 en prod
#	-le fichier 0930 va etre envoye via les flux RG0930 et recette et SG0930 en prod
#	-le fichier 0950 va etre envoye via les flux RG0950 et recette et SG0950 en prod
# 18 juillet 2019 : suppresion des flux pour fichiers prospects 2000, 2100, 2200, 2300, 2400, 2500 et 2600
# 	-le fichier 0127 va etre envoye via les flux RPRFEG en recette et PPRFEG en prod
#	-le fichier 0128 va etre envoye via les flux RPRFDG et recette et PPRFDG en prod
###############################################################################

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

    # Transfert du fichier
    gwqvi1.sh $EVAL_FILEGLOBAL ${_flux}
    echo "Transfert effectue, code retour: $?"
}

# Evaluation du repertoire de travail
DIR_REP_GEN_GLOBAL=$BASE_DATA_DIR/REPLICATION_GENERIQUE/GLOBAL/GLOBAL-$DATE_GLOBAL
EVAL_DIRNAME=`ls -d $DIR_REP_GEN_GLOBAL 2>/dev/null`

if [ ! -d "$EVAL_DIRNAME" ]
then
    echo "R�pertoire des fichiers globaux inexistant, $DIR_REP_GEN_GLOBAL"
    echo "Pas de transfert."
    exit
fi

########################################
# Detection PRODUCTION ou RECETTE
if [ $hostType = "prd" ]
then
    ####################################################
    # Transfert des flux globaux de la prod via XFB
    for CodeFlux in 0100:RUVCO8 \
		    0101:RDDG01 \
		    0111:RUVCO9 \
		    0121:RUVCP0 \
		    0123:DWCG1P \
		    0124:DWCG2P \
		    0125:PMYAC4 \
		    0126:PPROS7 \
		    0127:PPRFEG \
			0128:PPRFDG \
		    0180:RUVCR0 \
		    0190:RUVCR1 \
		    0201:RUVCO7 \
		    0210:RUVCP1 \
		    0220:RUVCP2 \
		    0230:RUVCP3 \
		    0241:RUVCP4 \
		    0251:RUVCP5 \
		    0261:RUVCP6 \
		    0270:PEXID3 \
 		    0280:PEXID4 \
 		    0290:RALG01 \
 		    0300:RALG02 \
 		    0320:PMYAC3 \
		    0330:RUVCP7 \
		    0340:RUVCR2 \
		    0990:RUVCR3 \
		    1008:RCPG01 \
		    1009:RCPG02 \
		    1010:RCPG03 \
		    1011:RCPG04 \
		    1012:RCPG05 \
 		    1013:RCPG06 \
 		    1014:RALG03 \
		    1015:PNDG01 \
    	    2000:PPROS8 \
		    2100:PPROS9 \
		    2200:PPRO10 \
		    2300:PPRO11 \
		    2400:PPRO12 \
		    2500:RALG04 \
		    2600:RALG05 \
			0160:SG0160 \
			0170:SG0170 \
			0930:SG0930 \
			0950:SG0950
    do

#    	    2000:PPROS8 \
#		    2100:PPROS9 \
#		    2200:PPRO10 \
#		    2300:PPRO11 \
#		    2400:PPRO12 \
#		    2500:RALG04 \
#		    2600:RALG05 \
    
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
    for CodeFlux in 0100:TUVCO8 \
		    0101:TDDG01 \
		    0111:TUVCO9 \
		    0121:TUVCP0 \
		    0123:DWCG1T \
		    0124:DWCG2T \
		    0125:RMYAC4 \
		    0126:RPROS7 \
		    0127:RPRFEG \
		    0128:RPRFDG \
		    0180:TUVCR0 \
		    0190:TUVCR1 \
		    0201:TUVCO7 \
		    0210:TUVCP1 \
		    0220:TUVCP2 \
		    0230:TUVCP3 \
		    0241:TUVCP4 \
		    0251:TUVCP5 \
		    0261:TUVCP6 \
		    0270:REXID3 \
		    0280:REXID4 \
		    0290:TALG01 \
		    0300:TALG02 \
		    0320:RMYAC3 \
		    0330:TUVCP7 \
		    0340:TUVCR2 \
		    0990:TUVCR3 \
		    1008:TCPG01 \
		    1009:TCPG02 \
		    1010:TCPG03 \
		    1011:TCPG04 \
		    1012:TCPG05 \
		    1013:TCPG06 \
		    1014:TALG03 \
		    1015:RNDG01 \
		    2000:RPROS8 \
		    2100:RPROS9 \
		    2200:RPRO10 \
		    2300:RPRO11 \
		    2400:RPRO12 \
		    2500:TALG04 \
		    2600:TALG05 \
			0160:RG0160 \
			0170:RG0170 \
			0930:RG0930 \
			0950:RG0950
    do

#		    2000:RPROS8 \
#		    2100:RPROS9 \
#		    2200:RPRO10 \
#		    2300:RPRO11 \
#		    2400:RPRO12 \
#		    2500:TALG04 \
#		    2600:TALG05 \
    
        echo $CodeFlux
        _code=`echo $CodeFlux | awk 'BEGIN{FS=":"}{print $1}'`
        _flux=`echo $CodeFlux | awk 'BEGIN{FS=":"}{print $2}'`
	codeToProcess
	_code=""
	_flux=""
    done 
fi
