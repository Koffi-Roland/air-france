#!/bin/bash

#############################################################################################
#                                                                                           #
# INVALIDATION DES SMS POUR CRMPUSH ET ROC.                                                 #
#                                                                                           #
#                                                                                           #
# L'invalidation des SMS pour CRMPUSH et ROC en fonction des codes erreurs stockes 		 	#
# dans la table REF_CODE_INVAL_PHONE		  												#
#                                                                                           #
#############################################################################################

#Liste_Emails_Reef="LDIF_REEF_RI_AMO@airfrance.fr,mail.sic.it@airfrance.fr"
Liste_Emails_Reef="mail.sic.it@airfrance.fr"

# Positionnement des variables propres au flux
DEPOS_DIR=$BASE_FTP_DIR
WORK_DIR=$BASE_DATA_DIR/TELECOMS/INVALIDATION
JAVA_DIR=$BASE_EXE_JAVA_DIR

###################################################
# Verification de l'environnement.
###################################################
if [ $hostType = "prd" ]
then
    FLUX_IN=(PKSMSR PKSMSC)
	Liste_Emails_Reef="LDIF_REEF_RI_AMO@airfrance.fr,mail.sic.it@airfrance.fr"
elif [ $hostType = "rct" ]
then
    FLUX_IN=(RKSMSR RKSMSC)
	Liste_Emails_Reef="mail.sic.it@airfrance.fr"
elif [ $hostType = "dev" ]
then
	# Flux factice pour test local
    FLUX_IN=(DKSMSR DKSMSC)
else
    echo "Pas de flux defini pour cet environnement : <$hostType>"
    exit 1
fi

for FLUX in "${FLUX_IN[@]}"
do
	# Creation du fichier trace
	TRACE_FILE=$WORK_DIR/$FLUX.trace

	# recuperer la liste des fichiers ACK transmis
  lstFiles=(`ls $DEPOS_DIR/ACK$FLUX-$FLUX_*`)
  date >>$TRACE_FILE
  echo "Nombre de fichiers reçus pour $FLUX : ${#lstFiles[@]}" >>$TRACE_FILE

  if [ "${#lstFiles[@]}" = 0 ]
  then
	  echo "No File $FLUX.* on standby"
	  echo "Aucun FICHIER D'ACQUITTEMENT transmis : PAS DE TRAITEMENT !" >>$TRACE_FILE
	  echo "------------------------------------------------------------" >>$TRACE_FILE
	  date >>$TRACE_FILE
	else
		# Date d'invalidation (commune avec le java)
		INVALDATE=`date +"%Y%m%d%H%M%S"`

		Titre_Mail="Invalidation SMS - $FLUX"
		if [ $hostType != "prd" ]
		then
    	Titre_Mail="[TEST] Invalidation SMS - $FLUX"
		fi

		Texte_Mail="*** THIS IS A SYSTEM GENERATED MESSAGE, PLEASE DO NOT REPLY TO THIS EMAIL ***

		See attached files :
		1. Attached file BatchInvalSMS_Synthesis_${INVALDATE}.csv contains the synthesis output for Batch Invalidation SMS.

		For further details, consult logs in $BASE_DATA_DIR/TELECOMS/INVALIDATION/ or please contact : LDIF_REEF_RI_AMO@airfrance.fr.\nThank you."

	  echo "Process $FLUX stream..."

		for myFileACK in $DEPOS_DIR/ACK$FLUX-$FLUX_*; do

	    [[ -e "$myFileACK" ]] || break

	    if [ -f $myFileACK ]; then
			  rm -f $myFileACK 2>>$TRACE_FILE
	    fi

	    # ex: ACKTR3URI-TR3URI_1234567 devient TR3URI_1234567
	    fileReceived=`basename $myFileACK |  sed -e "s/^\([^-]*\).//"`
	    myFile=$DEPOS_DIR/$fileReceived

	    if [ -f $myFile ]; then
	      echo "---------------> RECEPTION DU FICHIER $myFile" >>$TRACE_FILE

	      # deplacer le fichier dans le workdir.
	      mv -f $myFile $WORK_DIR/ 2>>$TRACE_FILE
	    fi

	    cd $WORK_DIR
	    mkdir -p $WORK_DIR/HISTO

	    # renommer avec la date du jour.
	    mv -f $fileReceived BB8InvalidationSMS_$fileReceived_$INVALDATE 2>>$TRACE_FILE
	    fileInvalidation=$WORK_DIR/BB8InvalidationSMS_$fileReceived_$INVALDATE
	    cp $fileInvalidation $WORK_DIR/HISTO/

	    # Appel Java
	    if [ -f $fileInvalidation ]; then
	        #executer le batch Invalidation SMS
	        echo "Batch InvalidationSMS launched\n";
	        echo "Demarrage du batch avec le fichier $fileInvalidation" >>$TRACE_FILE
	        $JAVA_DIR/BatchInvalidationSMS.sh -f $fileInvalidation -t info -C -force
	    fi

	    #Zip Et Envoi de la synthese par email
			if [ -f $WORK_DIR/BatchInvalidationSMS_Synthesis_$INVALDATE.txt ]; then
				echo "Envoi resultat par mail"
				zip $WORK_DIR/InvalSMS.zip $WORK_DIR/BatchInvalidationSMS_Synthesis_${INVALDATE}.txt
	    	echo -e "$Texte_Mail" | mailx -s "$Titre_Mail" -a $WORK_DIR/InvalSMS.zip $Liste_Emails_Reef
	    	rm $WORK_DIR/InvalSMS.zip
	    	mv $WORK_DIR/BatchInvalidationSMS_Synthesis_$INVALDATE.txt $WORK_DIR/HISTO/
			fi
		done
	fi
done