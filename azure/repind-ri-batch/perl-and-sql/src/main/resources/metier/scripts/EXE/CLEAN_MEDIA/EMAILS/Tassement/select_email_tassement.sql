set serveroutput on;
set heading off;
set pagesize 0;
set line 130;
set feed off;
set head off;
set trimspool on;
SET NEWPAGE 0;
SET SPACE 0;
SET LINESIZE 500;
SET PAGESIZE 0;
SET ECHO OFF;
SET FEEDBACK OFF;
SET HEADING OFF;
SET ARRAYSIZE 500;

select SAIN ||';'|| SGIN ||';'|| SEMAIL
from emails where sgin is not null and (semail like '% %' or semail like '%'||CHR(9)||'%')
              and sstatut_medium='V';
PROMPT
exit;
/