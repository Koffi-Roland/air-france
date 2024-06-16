#!/bin/bash
#############################################################################################
#                                                                                           #
# INVALIDATION DES EMAILS DE LA R3 DE CRMPUSH                                               #
#                                                                                           #
#     Dans le cadre de la release R3 de CRMPush, on conduit le process d invalidation       #
#     Ajout de l'invalidation d'email pour la R1 de CRMPUSH                                 #
#                                                                                           #
# L'invalidation des emails se fait via le lancement du batch InvalidationEmail             #
# REPIND-412 : Separation des fichiers a traiter et des fichiers de traces entre CRM et FBS #
# BUG : Lecture des fichiers apres deplacement pour prendre le dernier copie "ls -t"        #
#                                                                                           #
#############################################################################################

INVALDATE=`date +"%Y%m%d%H%M%S"`

# Positionnement des variables propres au flux
DEPOS_DIR=$BASE_FTP_DIR
WORK_DIR=$BASE_DATA_DIR/QUALIF_ADRESSES/CRMPUSH_R3
BATCH_DIR=$BASE_EXE_DIR/QUALIF_ADRESSES/CRMPUSH_R3

# Detection de l'environnement
if [ $hostType = "prd" ]
then
    FLUX_IN=RR3URI
    FLUX_IN_R1=PKEMLC
    Liste_Emails_Reef="LDIF_REEF_RI_AMO@airfrance.fr,mail.sic.it@airfrance.fr,LDIF_CRMPUSHR3_IMO@airfrance.fr"

elif [ $hostType = "rct" ]
then
    FLUX_IN=TR3URI
    FLUX_IN_R1=RKEMLC
    Liste_Emails_Reef="mail.sic.it@airfrance.fr"

#flux bidon - pour tester
elif [ $hostType = "dev" ]
then
    FLUX_IN=DR3URI
    FLUX_IN_R1=DKEMLC
    Liste_Emails_Reef="lotomponiony@airfrance.fr"
else
    echo "Pas de flux defini pour cet environnement : <$hostType>"
    exit 1
fi

# fonction commune de process
process(){
	FLUX=$1
	TYPE=$2
	
	TRACE_FILE=$WORK_DIR/$FLUX.trace
	
	# teste la presence d'un fichier d'acquittement ACK_FILE
	echo "Search file "$DEPOS_DIR/ACK$FLUX"-"$FLUX"_*"
	lstFiles=(`ls $DEPOS_DIR/ACK$FLUX-$FLUX_*`)
	if [ "${#lstFiles[@]}" = 0 ]
	then
	    echo "No File $FLUX.* on standby "$DEPOS_DIR"/ACK"$FLUX"-"$FLUX"_*"
	    echo "Aucun FICHIER D'ACQUITTEMENT transmis : PAS DE TRAITEMENT !" >>$TRACE_FILE
	    echo "------------------------------------------------------------" >>$TRACE_FILE
	    date >>$TRACE_FILE
	else
	    echo "Treatment $FLUX"
	    # recupere la liste des fichiers ACK transmis
	    echo "List of "$DEPOS_DIR"/ACK"$FLUX"-"$FLUX"_*"
	    lstFiles=`ls $DEPOS_DIR/ACK$FLUX"-"$FLUX"_"*`
	
	    for myFileACK in $lstFiles; do
	
	        if [ -f $myFileACK ]; then
	            echo "Delete of "$myFileACK
	            \rm -f $myFileACK 2>>$TRACE_FILE
	        fi
	
	        # ex: ACKTR3URI-TR3URI_1234567 becomes TR3URI_1234567
	        myFile=`basename $myFileACK |  sed -e "s/^\([^-]*\).//"`
	        myFile=$DEPOS_DIR/$myFile
	        echo "List of "$myFile
	
	        if [ -f $myFile ]; then
	            echo "---------------> RECEPTION DU FICHIER $myFile" >>$TRACE_FILE
	            date >>$TRACE_FILE
	
	            # deplacement du fichier recu dans le repertoire de travail
	            echo "Move "$myFile" to "$WORK_DIR/
	            \mv -f $myFile $WORK_DIR/. 2>>$TRACE_FILE
	        fi
	    done
	
	    # get the last file to treat in $WORK_DIR directory
	    echo "File "$WORK_DIR/$FLUX"_*"
	    echo "Line "$file
	    file=`ls -t $WORK_DIR/$FLUX"_"*|head -1`
	    cd $WORK_DIR
	    # rename file to treat with current date
	    \mv -f $file BB8FBSPushR3_$TYPE_${INVALDATE} 2>>$TRACE_FILE
	    fileInvalidation=$WORK_DIR/BB8FBSPushR3_$TYPE_${INVALDATE}
	    echo "Treat :"$fileInvalidation
	    if [ -f $fileInvalidation ]; then
	        #executer le batch Invalidation email
	        echo "Batch InvalidationEmail launched for $TYPE\n";
	        $BATCH_DIR/BatchInvalidationEmail.sh -f $fileInvalidation -t info -force -s $TYPE
	    fi
	
	    Titre_Mail="Invalidation Email - $TYPE"
	    Texte_Mail="*** THIS IS A SYSTEM GENERATED MESSAGE, PLEASE DO NOT REPLY TO THIS EMAIL ***
	
	    See attached files :
	    1. Attached file BatchInvalEmail_Synthesis_${INVALDATE}.csv contains the synthesis output for Batch Invalidation Email.
	
	
	    For further details, consult logs in $WORK_DIR or please contact : LDIF_REEF_RI_AMO@airfrance.fr.\nThank you."
	
	    if [ -f $WORK_DIR/BatchInvalidationEmail_Synthesis_${INVALDATE}.txt ]; then
	    	echo "Envoi resultat par mail"
	        zip $WORK_DIR/InvalEmail.zip $WORK_DIR/BatchInvalidationEmail_Synthesis_${INVALDATE}.txt
	        echo -e "$Texte_Mail" | mailx -s "$Titre_Mail" -a $WORK_DIR/InvalEmail.zip  $Liste_Emails_Reef
			\rm $WORK_DIR/InvalEmail.zip
			ERROR=0
	    fi
	fi
}

process $FLUX_IN FBSP
process $FLUX_IN_R1 CRMP
exit $ERROR