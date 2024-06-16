#!/bin/bash
LINE_SIZE=599
datePurge=${1?"date purge must be set"}
dirLogs=${2?"log directory must be set"}
agenciesClosedLogs=${3?"log filename must be set"}
oneGin=${4?"one gin must be set"}
numOfRow=${5?"number of row must be set"}
oneIata=${6?"one iata must be set"}
execute_sql(){
	local SQL=${1?"SQL query must be set"}
	echo "
	SET TRIMOUT ON;
	set line $LINE_SIZE;
	SET LINESIZE $LINE_SIZE;
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

if (( oneGin != -1 )); then
	gins=$(execute_sql "SELECT a.SGIN from AGENCE a, PERS_MORALE p where a.SGIN=p.SGIN and a.SGIN='$oneGin' and p.SSTATUT='R' 
	and ADD_MONTHS(TO_DATE('$datePurge','ddmmyyyy'), -36 ) >= a.DDATE_RADIATION;")
elif (( oneIata != -1 )); then
	gins=$(execute_sql  "SELECT a.SGIN from AGENCE a, PERS_MORALE p, NUMERO_IDENT ni where a.SGIN=p.SGIN and ni.SGIN=a.SGIN and ni.SNUMERO='$oneIata' and p.SSTATUT='R'
	 and ADD_MONTHS(TO_DATE('$datePurge','ddmmyyyy'), -36 ) >= a.DDATE_RADIATION;")
elif (( numOfRow != -1 )); then 
	gins=$(execute_sql  "SELECT a.SGIN from AGENCE a, PERS_MORALE p where a.SGIN=p.SGIN and rowNum<'$numOfRow' and p.SSTATUT='R' 
	and ADD_MONTHS(TO_DATE('$datePurge','ddmmyyyy'), -36 ) >= a.DDATE_RADIATION;")
else 
	gins=$(execute_sql  "SELECT a.SGIN from AGENCE a, PERS_MORALE p where a.SGIN=p.SGIN and p.SSTATUT='R'
	and ADD_MONTHS(TO_DATE('$datePurge','ddmmyyyy'), -36 ) >= a.DDATE_RADIATION;")
fi

touch $dirLogs/$agenciesClosedLogs
while read -r gin; do
	[ -z "$gin" ] && continue
	execute_sql "UPDATE AGENCE SET DDATE_FIN=ADD_MONTHS(DDATE_RADIATION, 36)  where SGIN='$gin';"
	execute_sql "UPDATE PERS_MORALE SET SSTATUT='X', DDATE_MODIFICATION=SYSDATE, SSIGNATURE_MODIFICATION='PURGE_$datePurge' where SGIN='$gin';"
	echo "GIN: $gin" | tee -a $dirLogs/$agenciesClosedLogs
done <<< "$gins"
cp $dirLogs/$agenciesClosedLogs $BASE_FTP_DIR/UTL_FILE/