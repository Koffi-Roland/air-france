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


SELECT
        em.sain ||';'||
        em.sgin||';'||
        em.semail||';'||
        em.SSTATUT_MEDIUM||';'||
        em.scode_medium||';'||
        to_char(em.DDATE_MODIFICATION,'DD/MM/YYYY HH24:MI:SS')||';'||
        em.SSIGNATURE_MODIFICATION
FROM SIC2.emails em
WHERE em.sgin is not null AND em.sstatut_medium='V' and
    (
        (em.semail IS NOT NULL AND NOT REGEXP_LIKE(em.semail, '^[_a-zA-Z0-9\-]+(\.[_a-zA-Z0-9\-]+)*@[a-zA-Z0-9\-]+(\.[a-zA-Z0-9\-]+)*\.[a-zA-Z]{2,6}$'))
        );
PROMPT
exit;
/