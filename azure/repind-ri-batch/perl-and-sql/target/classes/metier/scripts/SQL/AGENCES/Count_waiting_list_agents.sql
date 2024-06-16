define countQAgentsFile='&1'

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

spool &countQAgentsFile;
SELECT 'Total agents in waiting list: ' || COUNT (*) from PERS_MORALE p
                                         where p.STYPE='A' 
                                         and p.SSTATUT='Q';
spool off;

exit;
