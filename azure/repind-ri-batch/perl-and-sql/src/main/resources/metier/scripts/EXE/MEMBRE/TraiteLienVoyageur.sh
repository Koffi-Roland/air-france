#!/bin/bash

# ############################################################
# Shell de traitement des fichiers Alimentation Voyageurs
# ############################################################

Type=${1?"Enter type. 'DWH' or 'BDC'"}
ListMailDICF="LDIF_REEF_TMA@airfrance.fr,LDIF_REEF_RI_AMO@airfrance.fr"
#ListMailDICF="dimartini-ext@airfrance.fr"
if [ "$Type" = "BDC" ]; then
	FichierRecu=RTURF1.*
	FluxIN=RTURF1
elif [ "$Type" = "DWH" ]; then
	FichierRecu=DWHVOY.txt
else
	echo "unacceptable type"
	exit 1
fi

DIR_DATA=$BASE_DATA_DIR/MEMBRE
dateTrace=`/usr/bin/date +%d%m20%y`

cd $DIR_DATA

# Declaration de la fonction de traitement des fichiers
TraiteFichier()
{
	echo "Fichiers "$Type" $FichierRecu recus." | tee $DIR_DATA/Traces_"$Type"_$dateTrace.txt
	
	tr -d '\015' <$BASE_FTP_DIR/$FichierRecu >$DIR_DATA/"$Type"VOY_atraiter.txt
	echo "Lancement de BatchAlimVoyageurFirme" | tee -a $DIR_DATA/Traces_"$Type"_$dateTrace.txt

	split -l 50000 "$Type"VOY_atraiter.txt _
	for i in $(ls _*)
	do
		$BASE_EXE_CPP_DIR/BatchAlimVoyageurFirme -f $i -T $Type -C -n 1 -o $i &
	done
	wait 

	rm _*
	# Concatnation des fichiers rapports et rejets
	cat MiseAJourLien"$Type"PersonneMoraleRejet_* > MiseAJourLien"$Type"PersonneMoraleRejet.txt
	cat MiseAJourLien"$Type"PersonneMoraleRapport_* > MiseAJourLien"$Type"PersonneMoraleRapport.txt
	# Nettoyage des fichiers
	rm MiseAJourLien"$Type"PersonneMoraleRejet_*
	rm MiseAJourLien"$Type"PersonneMoraleRapport_*

	echo "Le traitement est termine" | tee -a $DIR_DATA/Traces_"$Type"_$dateTrace.txt
	MAIL=$(cat $DIR_DATA/Traces_"$Type"_$dateTrace.txt)
	
	$BASE_EXE_DIR/MEMBRE/updateLienMembreVoyageurUnique.pl > $DIR_DATA/TracesUpdateLienMembreVoyageurUnique$dateTrace.txt
	$BASE_EXE_DIR/MEMBRE/removeDoublonsLiensMembre.pl > $DIR_DATA/TracesRemoveLienMembreVoyageur$dateTrace.txt
	
	if [ "$Type" = "BDC" ]; then
		rm -f $BASE_FTP_DIR/"ACK"$FluxIN"-"$FichierRecu 2> /dev/null
	fi
	
}

#PurgeLien()
#{
	# Supprimer les commentaires des 2 lignes suivantes pour reactiver les purges des liens
	#$BASE_EXE_DIR/MEMBRE/updateLienMembreVoyageurUnique.pl > $BASE_DATA_DIR/MEMBRE/TracesUpdateLienMembreVoyageurUnique$dateTrace.txt
	#$BASE_EXE_DIR/MEMBRE/removeDoublonsLiensMembre.pl > $BASE_DATA_DIR/MEMBRE/TracesRemoveLienMembreVoyageur$dateTrace.txt
	
	######################################################################################
	# DEV ANNULE . AMO INCAPABLES DE SE METTRE D'ACCORD                                  #
	######################################################################################
	#if [ `$BASE_TOOL_DIR/hostType` = "prd" ]  
	#then
		#sqlplus sic2/sic2 @$BASE_SQL_DIR/MEMBRE/PurgeLogiqueMembreVoyageur.sql
	#else
		#sqlplus sic2/rctsic @$BASE_SQL_DIR/MEMBRE/PurgeLogiqueMembreVoyageur.sql
	#fi
	#if [ -f $BASE_DATA_DIR/MEMBRE/PURGE/FichierMembrePurgeLogique* ]
	#then
		#cp $BASE_DATA_DIR/MEMBRE/PURGE/FichierMembrePurgeLogiqueOKAvecDateFinPerimee.txt $BASE_DATA_DIR/MEMBRE/PURGE/HISTO/FichierMembrePurgeLogiqueOKAvecDateFinPerimee$dateTrace.txt
		#cp $BASE_DATA_DIR/MEMBRE/PURGE/FichierMembrePurgeLogiqueOKSansDateFin.txt $BASE_DATA_DIR/MEMBRE/PURGE/FichierMembrePurgeLogiqueOKSansDateFin$dateTrace.txt
	#fi
	######################################################################################
	# POUR REACTIVER LA PURGE DES LIENS DE + DE 2 ANS, DECOMMENTER CES LIGNES            #
	######################################################################################
#}

if [ ! -f $BASE_FTP_DIR/$FichierRecu ]
then
	echo "Pas de fichiers disponibles."
	echo "Aucun fichier "$Type" disponible">>$DIR_DATA/Traces_"$Type"_$dateTrace.txt
	exit
fi

echo "Traitement "$Type
rm $DIR_DATA/*.*

TraiteFichier 

MAIL="$MAIL \n\n Nombre total de liens membre recus    : "$(grep -v '^$' $BASE_FTP_DIR/$FichierRecu | wc -l)
MAIL="$MAIL \n\n Nombre total de liens membre traites  : "$(grep ouverture $DIR_DATA/MiseAJourLien"$Type"PersonneMoraleRapport.txt |  wc -l)
MAIL="$MAIL \n\n Nombre total de liens membre rejettes : "$(grep -v '^$' $DIR_DATA/MiseAJourLien"$Type"PersonneMoraleRejet.txt | wc -l)

tar cvfz HISTO/Traitement"$Type"$dateTrace.tar.gz $BASE_FTP_DIR/$FichierRecu MiseAJourLien"$Type"PersonneMoraleRejet.txt MiseAJourLien"$Type"PersonneMoraleRapport.txt
echo -e "$MAIL" | mailx -s Chargement_Lien_Voyageurs_Firmes_Agences_"$Type" -a HISTO/Traitement"$Type"$dateTrace.tar.gz $ListMailDICF

rm $DIR_DATA/*.*
