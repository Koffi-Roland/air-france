#!/bin/bash

LINE_SIZE=599
date=${1?"date must be set"}
dirLogs=${2?"log directory must be set"}
agencies_DWH_file=${3?"DWH log filename must be set"}
agencies_BDC_file=${4?"BDC log filename must be set"}
execute_sql(){
	local SQL=${1?"SQL query must be set"}
	local FILE=${2?"File must be set"} 
	echo "
	SET TRIMOUT ON;
	SET TRIMSPOOL ON;
	set serveroutput off;
	set line $LINE_SIZE;
	SET LINESIZE $LINE_SIZE;
	set heading off;
	set pagesize 0;
	set heading off;
	set feedback off;
	set tab off;
	set termout OFF;
	spool $FILE append;
	$SQL
	exit;" > tmp_sql_query.sql
   sqlplus -S `secureSIC $SECURE_DATABASE_ACCESS_FILE_SIC READSIC` @tmp_sql_query.sql
   rm tmp_sql_query.sql
}
echo "$date" | tee $dirLogs/$agencies_DWH_file $dirLogs/$agencies_BDC_file
execute_sql "select GIN||';'||NUM_AGENCE||';'||ZC1||';'||TYPE_AGENCE||';'||RAISON_SOCIALE||';'||NOM_USUEL
||';'||DATE_OUVERTURE||';'||DATE_RADIATION||';'||DATE_FERMETURE||';'||TYPE_CONTRAT||';'||SOUS_TYPE_CONTRAT||';'||
DEBUT_VALIDITE||';'||FIN_VALIDITE||';'||RESULTAT||';'||MONNAIE||';'||GIN_FIRME||';'||ZC_FIRME||';'||APURGER
 from purge_agences where APURGER = 'O';" $dirLogs/$agencies_DWH_file
execute_sql "select distinct(GIN)||';'||APURGER from purge_agences where APURGER = 'O' order by (GIN);" $dirLogs/$agencies_BDC_file
cp $dirLogs/$agencies_DWH_file $BASE_FTP_DIR/UTL_FILE/
cp $dirLogs/$agencies_BDC_file $BASE_FTP_DIR/UTL_FILE/