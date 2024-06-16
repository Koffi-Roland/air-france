#!/bin/bash
###############################################################################
# envoi quotidien au DWH et au RIPORTAL des fichiers de replication par mouvement,
# pour les codes 100, 110, 210, 220, 230,
# sur les flux pelican RUVCO1..5
# --------------------------------------
# 27 Mai  2004     Ajout flux pour code 0400
# 14 Juin 2004     Ajout flux pour codes 0130 0140 0150
# 21 Juin 2004     Refactoring du code
#                  Utilisation d'une fonction + tests des fichiers
# 20 Aout 2004     Ajout flux pour codes 0430 0940
# 05 Novembre 2004 Ajout flux pour codes 0920 0930
# 13 janvier 2005  Ajout flux pour code 0201
# 20 mai 2005      Ajout flux pour code 0120 0910 950
# 16 aout 2005     Ajout flux pour code 0160 0170 0410 0420
# 15 Juin 2007     Ajout flux en recette pour les codes 0100, 0201, 0210, 0220, 0230, 0330, et,
#                  0121, 0180, 0190, 0241, 0251, 0261, 0340, 0990
# 16 Juillet 2007 : Ajout flux en recette et prod  pour les codes 0121, 0180, 0190, 0241,
#		    0251, 0261, 0340, 0990, 0991, 0992, 0993, 0994, 0995, 0996
# 27 Aout : migration de tous les flux bipbip restant vers xfb
# 31 mai 2013 : ajout flux pour fichiers prospects 2000, 2100, 2200, 2300 et 2400
#               ainsi que fichier individu 0126
# 14 Novembre 2014: ajout flux REXID1,REXID2,PEXID1 et PEXID2 (fichiers 270 et 280)
# Ajout des flux RCPAYS et PCPAYS pour le fichier 1007
# 13 Septembre 2016 : ajout des flux
#   - TALM01 et RALM01 pour le fichier 290 en recette et prod
#   - TALM02 et RALM02 pour le fichier 300 en recette et prod
#   - TALM03 et RALM03 pour le fichier 1014 en recette et prod
# 04 Novembre 2016: ajout des flux
#   - TALM04 et RALM04 pour le fichier 2500 en recette et prod
#   - TALM05 et RALM05 pour le fichier 2600 en recette et prod
# 28 Octobre 2016 : ajout des flux
#   - TCPM01 et RCPM01 pour le fichier 1008 en recette et prod
#   - TCPM02 et RCPM02 pour le fichier 1009 en recette et prod
#   - TCPM03 et RCPM03 pour le fichier 1010 en recette et prod
#   - TCPM04 et RCPM04 pour le fichier 1011 en recette et prod
#   - TCPM05 et RCPM05 pour le fichier 1012 en recette et prod
#   - TCPM06 et RCPM06 pour le fichier 1013 en recette et prod
# 22 Novembre 2016 : ajout des flux
#	- RDDM01 et TDDM01 pour le fichier 0101 en recette et prod
# 21 Mars 2017 : ajout des flux
#	- RTRM01 et TTRM01 pour le fichier 0101 en recette et prod
# 28 Mars 2017 : modification des flux
#	- le 111 va etre envoye via les flux du 110, soit RUVC02 et TUVC02 et 
#         on n'envoie plus le 110
# 16 Juin 2017 : ajout d'un nouveau flux
#       - PNDM01 et RNDM01 pour le fichier 1015 en recette et en prod
# 27 Mars 2019 : ajout des flux pour l'application RIPORTAL
# 	-le fichier 0160 va etre envoye via les flux RIDX75 en recette et SIDX75 en prod
#	-le fichier 0170 va etre envoye via les flux RIDX77 et recette et SIDX77 en prod
#	-le fichier 0930 va etre envoye via les flux RM0930 et recette et SIDX66 en prod
#	-le fichier 0950 va etre envoye via les flux RM0950 et recette et SIDX74 en prod
# 18 juillet 2019 : suppresion des flux pour fichiers prospects 2000, 2100, 2200, 2300, 2400, 2500 et 2600
# 	-le fichier 0127 va etre envoye via les flux RPREFE en recette et PPREFE en prod
#	-le fichier 0128 va etre envoye via les flux RPREFD et recette et PPREFD en prod
###############################################################################

# Fonction utilisant les nouveaux transfert interpel
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

######
# Main
DATE_DU_JOUR=`date '+%Y%m%d'`

# Evaluation du repertoire de travail
DIR_REP_GEN_MVT=$BASE_DATA_DIR/REPLICATION_GENERIQUE/MVT/MVT-$DATE_DU_JOUR????
EVAL_DIRNAME=`ls -d $DIR_REP_GEN_MVT 2>/dev/null`

if [ ! -d "$EVAL_DIRNAME" ]
then
    echo "R?pertoire des fichiers de mouvement inexistant, $DIR_REP_GEN_MVT"
    echo "Pas de transfert."
    exit
fi
########################################
# Detection PRODUCTION ou RECETTE

if [ $hostType = "prd" ]
then

    ##########################################################
    # Transfert des flux de mouvement de la production via XFB
    for CodeFlux in 0100:RUVCO1 \
					0101:RDDM01 \
					0111:RUVCO2 \
					0121:RUVCQ1 \
					0123:DWCM1P \
					0124:DWCM2P \
					0125:PMYAC2 \
					0126:PPROS1 \
					0127:PPREFE \
					0128:PPREFD \
					0160:SIDX76 \
					0170:SIDX78 \
					0180:RUVCQ2 \
					0190:RUVCQ8 \
					0201:RUVCO6 \
					0210:RUVCO3 \
					0220:RUVCO4 \
					0230:RUVCO5 \
					0241:RUVCQ3 \
					0251:RUVCQ4 \
					0261:RUVCQ5 \
					0270:PEXID1 \
					0280:PEXID2 \
					0290:RALM01 \
					0300:RALM02 \
					0320:PMYAC1 \
					0330:RUVCP8 \
					0340:RUVCQ6 \
					0410:SIDX80 \
					0420:SIDX82 \
					0990:RUVCQ7 \
					0991:SIDW55 \
					0992:SIDW56 \
					0993:SIDW57 \
					0994:SIDW58 \
					0995:SIDW59 \
					0996:SIDW60 \
					1007:PCPAYS \
					1008:RCPM01 \
					1009:RCPM02 \
					1010:RCPM03 \
					1011:RCPM04 \
					1012:RCPM05 \
					1013:RCPM06 \
					1014:RALM03 \
					1015:PNDM01 \
					2000:PPROS2 \
					2100:PPROS3 \
					2200:PPROS4 \
					2300:PPROS5 \
					2400:PPROS6 \
					2500:RALM04 \
					2600:RALM05 \
					
    do

#		    		0127:PPREFE \
#		    		0128:PPREFD \
	
#					2000:PPROS2 \
#					2100:PPROS3 \
#					2200:PPROS4 \
#					2300:PPROS5 \
#					2400:PPROS6 \
#					2500:RALM04 \
#					2600:RALM05 \
    
        echo $CodeFlux
        _code=`echo $CodeFlux | awk 'BEGIN{FS=":"}{print $1}'`
        _flux=`echo $CodeFlux | awk 'BEGIN{FS=":"}{print $2'}`
        codeToProcess
	_code=""
	_flux=""
    done
  
elif [ $hostType = "rct" ]
then
    #######################################################
    # Transfert des flux de mouvement de la recette via XFB
    for CodeFlux in 0100:TUVCO1 \
					0101:TDDM01 \
					0111:TUVCO2 \
					0121:TUVCQ1 \
					0123:DWCM1T \
					0124:DWCM2T \
					0125:RMYAC2 \
					0126:RPROS1 \
					0127:RPREFE \
					0128:RPREFD \
					0180:TUVCQ2 \
					0190:TUVCQ8 \
					0201:TUVCO6 \
					0210:TUVCO3 \
					0220:TUVCO4 \
					0230:TUVCO5 \
					0241:TUVCQ3 \
					0251:TUVCQ4 \
					0261:TUVCQ5 \
					0270:REXID1 \
					0280:REXID2 \
					0290:TALM01 \
					0300:TALM02 \
					0320:RMYAC1 \
					0330:TUVCP8 \
					0340:TUVCQ6 \
					0990:TUVCQ7 \
					0991:TIDW55 \
					0992:TIDW56 \
					0993:TIDW57 \
					0994:TIDW58 \
					0995:TIDW59 \
					0996:TIDW60 \
					1007:RCPAYS \
					1008:TCPM01 \
					1009:TCPM02 \
					1010:TCPM03 \
					1011:TCPM04 \
					1012:TCPM05 \
					1013:TCPM06 \
					1014:TALM03 \
					1015:RNDM01 \
 					2000:RPROS2 \
					2100:RPROS3 \
					2200:RPROS4 \
					2300:RPROS5 \
					2400:RPROS6 \
					2500:TALM04 \
					2600:TALM05 \
					0160:RIDX75 \
					0170:RIDX77 \
					0930:RM0930 \
					0950:RM0950
    do

#		    		0127:RPREFE \
#		    		0128:RPREFD \
	
# 					2000:RPROS2 \
#					2100:RPROS3 \
#					2200:RPROS4 \
#					2300:RPROS5 \
#					2400:RPROS6 \
#					2500:TALM04 \
#					2600:TALM05 \
    
        echo $CodeFlux
        _code=`echo $CodeFlux | awk 'BEGIN{FS=":"}{print $1}'`
        _flux=`echo $CodeFlux | awk 'BEGIN{FS=":"}{print $2'}`
	codeToProcess
	_code=""
	_flux=""
    done
fi
