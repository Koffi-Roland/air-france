#!/bin/bash
date=${1?"a purger must be set"}
fileTrace=${2?"log directory must be set"}
execute_sql(){
	local SQL=${1?"SQL query must be set"}
	echo "
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
echo "Date du traitement de purge : $date" | tee $fileTrace/Extraction_AGENCES_PURGEES_RA2.txt
gins=$(execute_sql "select sgin as GIN from agence where SAGENCE_RA2='Y' and stype='9' 
and sgin not in  (select o.sgin from office_id o) order by (sgin);")

while read -r gin; do
	[ -z "$gin" ] && continue
	echo "$gin" | tee -a $fileTrace/Extraction_AGENCES_PURGEES_RA2.txt
    execute_sql "EXEC DELETE_CASCADE_PERS_MORALE('SGIN=''$gin''');"
done <<< "$gins"
cp $fileTrace/Extraction_AGENCES_PURGEES_RA2.txt $BASE_FTP_DIR/UTL_FILE/