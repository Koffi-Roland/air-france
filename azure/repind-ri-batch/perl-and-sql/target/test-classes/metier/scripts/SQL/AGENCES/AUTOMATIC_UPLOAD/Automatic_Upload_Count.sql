define email='&1'
define countEmail='&2'

set serveroutput off
set heading off
set pagesize 0
set line 130
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

spool &countEmail;
SELECT 'Valid: ' || COUNT (*) from AUTHORIZED_EMAIL where EMAIL='&email';
spool off;

exit;