#!/bin/sh

#
# shell d'execution du batch de replication
#
# codes applis / flux bipbip
# ISIS / RVISF3
# BDC / RCIMV2
# STAR / RCVGIN
# SYCAV / RCVSYC
# PQPFCE / RCVPQA
# PQPPTP / RCVPQB
# PQPFDF / RCVPQC
# PQPCAY / RCVPQD
# PQPRUN / RCVPQE
# PQPMF_FCE / RCVPMF
# COCONUT40 / RUVT40
# COCONUT46 / RUVT46
# RMC / RCVRMC
# SUPRA / ftp
# SURCOUF / ftp
########################
# Historique : 
# 12/11 : migration XFB des flux transmis via bipbip et ftp
#         OCEAN, COCONUT40, COCONUT46, RMC,SUPRA, SURCOUF, 
#         PQPFCE, PQPPTP,PQPFDF,PQPCAY, PQPRUN : appli n'existant plus ou transfert desactive. Suppression du code.

# Flags de transfert par appli ou global (1 Transfert effectif)
Extraction=1
Transfert=1
TransfertISIS=1
TransfertBDC=1
TransfertSTAR=1
TransfertSYCAV=1
CodeRetour2=0

# positionnement des variables pour produire les fichiers de replications
P_EXE=$BASE_EXE_DIR
P_EXE_RAP=$BASE_EXE_DIR/REPLICATION/RAP
P_BIN=$BASE_EXE_CPP_DIR
DATA=$BASE_DATA_DIR/REPLICATION/RAP
export DATA
DIR_REP_RAP_ENCOURS=$DATA/ENCOURS
DIR_REP_RAP_TRAIT=$DATA/TRAITEE
DIR_REP_RAP_TRANSF=$DATA/TRANSFEREE
TMP_DIR=/tmp
TRACE_FILE=$DATA/BatchReplication.trace
Liste_Emails_Reef="LDIF_REEF_TMA@airfrance.fr"

# recuperation de la date du jour
chaineDate=`/usr/bin/date '+m%md%dH%H'`
# recuperation des pids
thePID=$$

/usr/bin/date >>$TRACE_FILE

if [ `$BASE_TOOL_DIR/hostType` = "prd" ]
then
    production=1
else
    production=0
fi


########################
#    EXTRACTION
########################

# lancement du batch de replication
echo "*** REPLICATION DEBUT * SHELL PID = $thePID ***" >>$TRACE_FILE
echo "BatchReplication $*" >>$TRACE_FILE
if [ $Extraction -eq 1 ]
then
# creation du repertoire de travail
`cd $DIR_REP_RAP_ENCOURS; mkdir $thePID`
# traitement Replication
$P_BIN/BatchReplication $* -o $DIR_REP_RAP_ENCOURS/$thePID/replication
if [ $? -ne 0 ]
then
exit $?
fi

# Deplacement des fichiers crees par ce shell
`\mv $DIR_REP_RAP_ENCOURS/$thePID/replication* $DIR_REP_RAP_TRAIT`;
`cd $DIR_REP_RAP_ENCOURS; \rmdir $thePID`

else
echo "Lancement sans extraction: les fichiers presents dans $DIR_REP_RAP_TRAIT vont etre transferes ..." >>$TRACE_FILE
fi
echo "*** REPLICATION FIN   ***" >>$TRACE_FILE

/usr/bin/date >>$TRACE_FILE

#############################################################
#               TRANSFERT PAR APPLICATION
#############################################################

Fichiers=`cd $DIR_REP_RAP_TRAIT; ls replication*.txt`

for fichier in ${Fichiers} ;
do
  codeAppli=`echo $fichier | sed "s/^\([^.]*\)\.\([^.]*\)\.\(.*\)/\2/"`
  #extention=`echo $fichier | sed "s/\.\([^.]*\)$/\1/"`
  #echo $codeAppli $extention >>$TRACE_FILE

  if [ $codeAppli = BDC ]
    ### 1 seul fichier est extrait pour les 2 Applis ISIS et BDC; Il comporte BDC dans son nom
    ### Transfert PELICAN pour les applications ISIS / BDC
    then
    $P_EXE/FormatLigne.sh $DIR_REP_RAP_TRAIT/$fichier 1199
    if [ $TransfertISIS -eq 1 -a $Transfert -eq 1 ]
      then
      echo "Transfert du fichier $DIR_REP_RAP_TRAIT/$fichier vers le flux RVISF3 pour ISIS" >>$TRACE_FILE
      if [ $production -eq 1 ]
        then
	     CodeRetour=0
	     gwqvi1.sh $DIR_REP_RAP_TRAIT/$fichier RVISF3
	     CodeRetour=$?
	     if [ $CodeRetour -ne 0 ]
	     then
		echo "Transfert du fichier $DIR_REP_RAP_TRAIT/$fichier vers le flux RVISF3 : KO (code retour <$CodeRetour>)" >>$TRACE_FILE
		CodeRetour2=$CodeRetour
	     else 
		echo "Transfert du fichier $DIR_REP_RAP_TRAIT/$fichier vers le flux RVISF3 : OK" >>$TRACE_FILE
	     fi
      fi
    fi
    if [ $TransfertBDC -eq 1 -a $Transfert -eq 1 ]
      then
      # Suppression contacts Agence pour BDC
      sed "/^07.\{13\}A/ d"  $DIR_REP_RAP_TRAIT/$fichier > $TMP_DIR/$fichier
      echo "Transfert du fichier $TMP_DIR/$fichier vers le flux RCIMV2 pour BDC" >>$TRACE_FILE
      if [ $production -eq 1 ]
        then
	     CodeRetour=0
	     gwqvi1.sh $TMP_DIR/$fichier RCIMV2
	     CodeRetour=$?
	     if [ $CodeRetour -ne 0 ]
	     then
		echo "Transfert du fichier $TMP_DIR/$fichier vers le flux RCIMV2 : KO (code retour <$CodeRetour>)" >>$TRACE_FILE
		CodeRetour2=$CodeRetour
	     else 
		echo "Transfert du fichier $TMP_DIR/$fichier vers le flux RCIMV2 : OK" >>$TRACE_FILE
	     fi
      fi
      \rm $TMP_DIR/$fichier
    fi
  ########################
  elif [ $codeAppli = STAR ]
    ### Transfert PELICAN pour l'application STAR
    then
    echo "Transfert du fichier $DIR_REP_RAP_TRAIT/$fichier vers le flux RCVGIN" >>$TRACE_FILE
    if [ $TransfertSTAR -eq 1  -a $Transfert -eq 1 ]
      then
      if [ $production -eq 1 ]
        then
	  CodeRetour=0
          gwqvi1.sh $DIR_REP_RAP_TRAIT/$fichier RCVGIN
	  CodeRetour=$?
	  if [ $CodeRetour -ne 0 ]
	  then
	    echo "Transfert du fichier $DIR_REP_RAP_TRAIT/$fichier vers le flux RCVGIN : KO (code retour <$CodeRetour>)" >>$TRACE_FILE
	    CodeRetour2=$CodeRetour
	  else 
	    echo "Transfert du fichier $DIR_REP_RAP_TRAIT/$fichier vers le flux RCVGIN : OK" >>$TRACE_FILE
	  fi
      fi
    fi
  ########################
  elif [ $codeAppli = SYCAV ]
    ### Transfert PELICAN pour l'application SYCAV
    then
    # Creation du Header
    WcResult=`wc -l $DIR_REP_RAP_TRAIT/$fichier`
    NbLignes=`echo $WcResult | sed "s/^\(.*\)\ \(.*\)/\1/"`
    echo "deb" >>$TRACE_FILE
    echo $NbLignes >>$TRACE_FILE
    echo "fin" >>$TRACE_FILE
    echo "00 REPLICATION $chaineDate de $NbLignes lignes data" > $DIR_REP_RAP_TRAIT/$fichier.head
    $P_EXE/FormatLigne.sh $DIR_REP_RAP_TRAIT/$fichier.head 929
    cat $DIR_REP_RAP_TRAIT/$fichier.head  $DIR_REP_RAP_TRAIT/$fichier >  $TMP_DIR/$fichier.tmp
    \rm $DIR_REP_RAP_TRAIT/$fichier.head
    \mv $TMP_DIR/$fichier.tmp $DIR_REP_RAP_TRAIT/$fichier
    echo "Transfert $DIR_REP_RAP_TRAIT/$fichier vers le flux RCVSYC" >>$TRACE_FILE
    if [ $TransfertSYCAV -eq 1 -a $Transfert -eq 1 ]
      then
      # transfert pour le Marche AMERICAIN
      if [ $production -eq 1 ]
        then
	CodeRetour=0
	gwqvi1.sh $DIR_REP_RAP_TRAIT/$fichier RCVSYC
	CodeRetour=$?
	if [ $CodeRetour -ne 0 ]
	then
	    echo "Transfert du fichier $DIR_REP_RAP_TRAIT/$fichier vers le flux RCVSYC : KO (code retour <$CodeRetour>)" >>$TRACE_FILE
	    CodeRetour2=$CodeRetour
	else 
	    echo "Transfert du fichier $DIR_REP_RAP_TRAIT/$fichier vers le flux RCVSYC : OK" >>$TRACE_FILE
	fi
      fi
    fi
  ########################
  else
    echo "Transfert $fichier : Application Inconnue !!!" >>$TRACE_FILE
  fi

  ################################################
  # COMPRESSION ET CLASSEMENT DU FICHIER TRANSFERE
  ################################################
  echo "Classement et compression pour l'application $codeAppli" >>$TRACE_FILE
  \mv $DIR_REP_RAP_TRAIT/$fichier $DIR_REP_RAP_TRANSF/$fichier.$chaineDate
  compress -f $DIR_REP_RAP_TRANSF/$fichier.$chaineDate

  #########################
  # MISE A DISPO MARCHE AME
  #########################
  if [ $codeAppli = SYCAV ]
  then
    # copie du fichier (hebdo) SYCAV zipper pour mise a dispo du Marche AME (Bertrand Delcaire)
    \cp $DIR_REP_RAP_TRANSF/$fichier.$chaineDate.* $BASE_DATA_DIR/AGENCES/SYCAV
    # copie pour Herve
    \rm $BASE_FTP_DIR/OUT/SYCAV/$fichier.Z
    ln -s $DIR_REP_RAP_TRANSF/$fichier.$chaineDate.Z $BASE_FTP_DIR/OUT/SYCAV/$fichier.Z
  fi
  if [ $codeAppli = STAR ]
  then
    # copie pour Herve
    \rm $BASE_FTP_DIR/OUT/STAR/$fichier.Z
    ln -s $DIR_REP_RAP_TRANSF/$fichier.$chaineDate.Z $BASE_FTP_DIR/OUT/STAR/$fichier.Z
  fi
done;

if [ $CodeRetour2 -ne 0 ]
then
    echo "Sortie en erreur suite un probleme de transfert... " >>$TRACE_FILE
    echo "Sortie en erreur suite un probleme de transfert... Consulter le fichier de trace $TRACE_FILE." 
    exit 1
fi

