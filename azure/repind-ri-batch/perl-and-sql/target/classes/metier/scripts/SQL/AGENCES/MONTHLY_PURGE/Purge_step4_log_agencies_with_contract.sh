#!/bin/bash
LINE_SIZE=599
apurger=${1?"a purger must be set"}
dirLogs=${2?"log directory must be set"}
agenciesWithContract=${3?"agenciesWithContract must be set"}
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
gins=$(execute_sql "SELECT GIN from PURGE_AGENCES where TYPE_CONTRAT is not null;")
while read -r gin; do
	[ -z "$gin" ] && continue
	execute_sql "UPDATE PURGE_AGENCES SET APURGER='$apurger' where GIN='$gin';"
done <<< "$gins"
execute_sql_to_file "SELECT DISTINCT GIN from PURGE_AGENCES where APURGER='N';" 
cp $dirLogs/$agenciesWithContract $BASE_FTP_DIR/UTL_FILE/