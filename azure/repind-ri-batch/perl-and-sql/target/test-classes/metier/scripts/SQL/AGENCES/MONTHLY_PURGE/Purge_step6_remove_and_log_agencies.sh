#!/bin/bash
LINE_SIZE=599
date=${1?"a purger must be set"}
dirLogs=${2?"log directory must be set"}
agenciesRemovedFile=${3?"agenciesWithContract must be set"}
execute_sql(){
	local SQL=${1?"SQL query must be set"}
	echo "
	set line $LINE_SIZE;
	SET LINESIZE $LINE_SIZE;
	SET TRIMOUT ON;
	SET TRIMSPOOL ON;
	set serveroutput off;
	set heading off;
	set pagesize 0;
	set heading off;
	set feedback off;
	set tab off;
	$SQL
	exit;" > tmp_sql_query.sql
   sqlplus -S `secureSIC $SECURE_DATABASE_ACCESS_FILE_SIC READSIC` @tmp_sql_query.sql
   rm tmp_sql_query.sql
}
execute_sql_to_file(){
	local SQL=${1?"SQL query must be set"}
	execute_sql "
	set termout OFF;
	spool $dirLogs/$agenciesWithContract;
	$SQL"
}
touch $dirLogs/$agenciesRemovedFile
gins=$(execute_sql "SELECT GIN FROM purge_agences WHERE NUM_AGENCE IS NOT NULL AND APURGER='O' ORDER BY(GIN);")
while read -r gin; do
	[ -z "$gin" ] && continue
	echo "$gin" | tee -a $dirLogs/$agenciesRemovedFile
     execute_sql "EXEC DELETE_CASCADE('PERS_MORALE','SGIN=''$gin''');"
done <<< "$gins"
