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
SELECT 'Total agents attached to ZV3 90 90 90 90: ' || COUNT (*) 
		FROM PERS_MORALE p, PM_ZONE pmz, ZONE_DECOUP zd, ZONE_VENTE zv
		WHERE p.STYPE = 'A'
		AND p.SSTATUT = 'A'
		AND p.SGIN = pmz.SGIN 
		AND pmz.SLIEN_PRIVILEGIE = 'O'
		AND pmz.IGIN_ZONE = zd.IGIN 
		AND pmz.DDATE_FERMETURE IS NULL
		AND zd.IGIN = zv.IGIN
		AND zv.ZV0 = '90'
		AND zv.ZV1 = '90'
		AND zv.ZV2 = '90'
		AND zv.ZV3 = '90';

spool off;

exit;