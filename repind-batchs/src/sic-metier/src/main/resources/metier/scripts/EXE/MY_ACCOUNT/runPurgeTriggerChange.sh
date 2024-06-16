#!/bin/bash
###############################################################################
# Shell de lancement de la purge Oracle des donnees des events.
###############################################################################

event_type=$1

if [ "$event_type" != "-f" ] && [ "$event_type" != "-i" ]
then
	echo "not acceptable argument"
	exit 1
fi
jour=`date '+%d'`
mois=`date '+%b%Y'`

PURGE_SQL_DIR=$BASE_SQL_DIR/MY_ACCOUNT
PURGE_LOG_DIR=$BASE_LOG_DIR/purge_trigger_change
PURGE_LOG_FILE=$PURGE_LOG_DIR/purge_events$event_type.log
[ -d $PURGE_LOG_DIR ] || mkdir -p "$PURGE_LOG_DIR"

PURGE_START_TIME=`date +"%d/%m/%y %H:%M:%S UTC"`
echo  >> $PURGE_LOG_FILE
echo ------------------------------------------------------------------- >> $PURGE_LOG_FILE
echo "[$PURGE_START_TIME] Purge de la table des events  : DEBUT" >> $PURGE_LOG_FILE
SCRIPT_SQL_PURGE=$PURGE_SQL_DIR/purge-events$event_type.sql

#
# Execution d'un script SQL.
# $1: script SQL a executer
#
execute_sql_script() 
{	
	sqlplus `secureSIC $SECURE_DATABASE_ACCESS_FILE_SIC READSIC` @"$SCRIPT_SQL_PURGE" $PURGE_LOG_DIR 2>&1 >> $PURGE_LOG_FILE
	if test $? -eq 0
	then
		echo "$1 : SUCCESS" >> $PURGE_LOG_FILE
	else
		echo "$1 : ERROR" >> $PURGE_LOG_FILE
		# Sortie de script
		exit 1
	fi
}

execute_sql_script "Purge de la table des events des enregistrements en status P."

PURGE_END_TIME=`date +"%d/%m/%y %H:%M:%S UTC"`
echo "[$PURGE_END_TIME] Purge de la table TRIGGER_CHANGE : FIN" >> $PURGE_LOG_FILE

exit 0