define datePurge='&1'
define countFileCloseAgency='&2'
define countFileRemoveAgency='&3'
define listGinToRemove='&4'


whenever sqlerror exit rollback;
whenever sqlerror exit failure;
whenever OSERROR exit rollback;

set serveroutput on;
set heading off;
set pagesize 0;
set line 130;
set feed off
set head off
set trimspool on
SET NEWPAGE 0
SET SPACE 0
SET LINESIZE 400
SET PAGESIZE 0
SET ECHO OFF
SET FEEDBACK OFF
SET HEADING OFF
SET ARRAYSIZE 500

spool &countFileCloseAgency;
SELECT 'ToClose: ' || COUNT (*) from AGENCE a, PERS_MORALE p
					 where a.SGIN=p.SGIN 
					 and p.SSTATUT='R'
					 and ADD_MONTHS(TO_DATE('&datePurge','ddmmyyyy'), -36 ) >= a.DDATE_RADIATION;
spool off;


spool &countFileRemoveAgency;
SELECT 'ToRemove: ' || COUNT (*) from PURGE_AGENCES pa where pa.APURGER='O';						 
spool off;

spool &listGinToRemove;
SELECT GIN from PURGE_AGENCES where APURGER='O';						 
spool off;

exit;