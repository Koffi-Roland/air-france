#!/bin/bash

# ############################################################
# Shell de traitement des fichiers SEQUOIA (Contrats Agences)
# ############################################################

if [ "$hostType" = "dev" ]
then 
ListMailDICF="mail.sic.it@airfrance.fr"
else
ListMailDICF="LDIF_REEF_TMA@airfrance.fr"
fi
FichierRecu_SEQUOIA=SEQRAP.*
DIR_FTP_SEQUOIA=$BASE_FTP_DIR

if [ ! -d $BASE_DATA_DIR/AGENCES/SEQUOIA ]
then 
	mkdir -p $BASE_DATA_DIR/AGENCES/SEQUOIA
fi

cd $BASE_DATA_DIR/AGENCES/SEQUOIA
DIR_LAST_FILE="$BASE_DATA_DIR/AGENCES/SEQUOIA/LAST_FILE_LOADED"
dateTrace=`date +%d%m20%y`

# Declaration de la fonction de traitement des fichiers
TraiteFichier()
{
    if [ -f $DIR_FTP_SEQUOIA/$FichierRecu_SEQUOIA ]
    then
		rm -f $DIR_FTP_SEQUOIA/ACKSEQRAP*
    	echo "Fichiers SEQUOIA $FichierRecu_SEQUOIA recus." | tee Traces_SEQUOIA_$dateTrace.txt Mail.txt
    	if [ ! -d $DIR_FTP_SEQUOIA/HISTO ]
    	then 
			mkdir -p $DIR_FTP_SEQUOIA/HISTO
		fi

		if [ ! -d $DIR_LAST_FILE ]
    	then 
			mkdir -p $DIR_LAST_FILE
		fi

		cp $DIR_FTP_SEQUOIA/$FichierRecu_SEQUOIA $DIR_FTP_SEQUOIA/HISTO/
		echo "Gunzip sur le fichier recu"
		gunzip $DIR_FTP_SEQUOIA/$FichierRecu_SEQUOIA 
		cp $DIR_FTP_SEQUOIA/SEQRAP.* $DIR_FTP_SEQUOIA/SEQRAP_Dezippe.txt
		gzip $DIR_FTP_SEQUOIA/SEQRAP.*

		# Suppression des anciens fichiers de rapports + traces
		rm ./*.*
		mv $DIR_FTP_SEQUOIA/SEQRAP_Dezippe.txt SEQRAP_atraiter.txt

		except=$(comm -3  <(sort -k 1,12 SEQRAP_atraiter.txt | sort -k 14,23) <(sort -k 1,12 $DIR_LAST_FILE/SEQRAP_last.txt | sort -k 14,23))
		
		rm $DIR_FTP_SEQUOIA/$FichierRecu_SEQUOIA
		
		echo "On commit tous les 100 enregistrements pour optimiser le traitement Oracle" 
		echo "Lancement de BatchLienContratAgence">>Traces_SEQUOIA_$dateTrace.txt

		echo "On split le fichier en tranches de 20000">> Mail.txt
		split -l 20000 <(echo -e "$except") SEQRAP.enCours.    	
			
		# Liste les fichiers a traiter dans l'ORDRE CHRONOLOGIQUE
		lstFiles=`ls -tr SEQRAP.enCours.* `

		# Pour chaque fichier a traiter:
		indent_ack=1
		for myFile in $lstFiles;do
			echo "Commande : $BASE_EXE_CPP_DIR/BatchLienContratAgence -f $myFile -T SEQUOIA -C -n 1 -i $indent_ack>Traces_SEQUOIA_$indent_ack_$dateTrace.txt"
			$BASE_EXE_CPP_DIR/BatchLienContratAgence -f $myFile -T SEQUOIA -C -n 1 -i $indent_ack>Traces_SEQUOIA_$indent_ack_$dateTrace.txt &
			indent_ack=`expr $indent_ack + 1 `
			echo "Prochain numero de fichier : $indent_ack" 
		done

		# On attend que les instances des batchs se terminent toutes
		wait

		echo "Le traitement est termine" | tee -a Traces_SEQUOIA_$dateTrace.txt Mail.txt
		echo "Nombre de fichiers traites en parallele : $indent_ack">> Mail.txt

		mkdir TraitementSequoia$dateTrace
		cp *.* TraitementSequoia$dateTrace

		echo "Copie du dernier fichier passe dans le repertoire prevu pour"
		cp SEQRAP_atraiter.txt $DIR_LAST_FILE/SEQRAP_last.txt
		tar -czf TraitementSequoia$dateTrace.tar.gz TraitementSequoia$dateTrace
		\rm -Rf TraitementSequoia$dateTrace/
		mkdir -p HISTO
		mv *.gz HISTO/
    fi
}

echo "Traitement"
echo $DIR_FTP_SEQUOIA/$FichierRecu_SEQUOIA

# Verification de la presence des fichiers et traitement
if [ -f $DIR_FTP_SEQUOIA/$FichierRecu_SEQUOIA ]
then
    echo "Traitement SEQUOIA"
    TraiteFichier 
	cat Mail.txt | mailx -s "Chargement_Contrats_Agences_SEQUOIA" $ListMailDICF
    \rm Mail.txt > /dev/null
	cat MiseAJourContratSEQUOIAAgencesRejet* > MiseAJourContratSEQUOIAAgencesRejet_$dateTrace.txt
	find . -maxdepth 1 -type f -name "*.*" ! -name "MiseAJourContratSEQUOIAAgencesRejet_$dateTrace.txt" -exec rm {} \;
else
	echo "Pas de fichiers disponibles."
	echo "Aucun fichier SEQUOIA disponible">>Traces_SEQUOIA_$dateTrace.txt
	mail="Pas de fichier SEQUOIA recu aujourd'hui.\n"
	mail="$mail La base n'a donc pas été mise à jour.\n"
	mail="$mail Traitement termine. "
	echo -e $mail | mailx -s "Chargement_Contrats_Agences_SEQUOIA" $ListMailDICF
fi
