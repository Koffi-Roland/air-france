#!/bin/bash
#Ce script met à jour la BDD en ajoutant une préférence de communication de type FB_ESS pour les individus
#ne l'ayant pas alors qu'il dispose d'un contrat FB.

#Appel et execution du script d'invalidation
sqlplus `secureSIC $SECURE_DATABASE_ACCESS_FILE_SIC READSIC` @$BASE_SQL_DIR/COM_PREF/addFbEssComPref.sql
exit 0