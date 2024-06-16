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

column date_column new_value today_var
select to_char(sysdate,'yyyymmdd') date_column
from dual
/

spool &1/kpi_PostalFlag&today_var;


/* KPI 1 : The volume of addresses used for mailling by this application */
prompt KPI 1 - The volume of addresses used for mailling by this application

prompt APPLI;NBADR

select scode_app_send || '; ' || count(*) from adr_post where scode_app_send is not null group by scode_app_send;


/* KPI 2 : The volume and rate of invalid addresses with a flag "postal sending" and an invalidation cause = A */
prompt
prompt KPI 2 - The volume and rate of invalid addresses with a flag Postal Sending and an invalidation cause = A

prompt CodeAppli;CodePays;NbAdr;nbAdrInvalA;Rate

select a.codAppli || ';' || a.codPays || ';' || a.nbAdr || ';' || decode(b.nbInvalA, NULL, 0, b.nbInvalA) || ';' || decode(b.nbInvalA, NULL, 0, decode (a.nbAdr, 0, 0, round(b.nbInvalA / a.nbAdr * 100,2))) from
(select scode_app_send codAppli, scode_pays codPays, count(*) nbAdr from adr_post where scode_app_send is not null group by scode_app_send, scode_pays) a,
(select scode_app_send codAppli, scode_pays codPays, count(*) nbInvalA from adr_post where sstatut_medium='I' and scode_app_send is not null and stype_invalidite='A' group by scode_app_send, scode_pays) b
where a.codAppli=b.codAppli(+) and a.codPays=b.codPays(+);


/* KPI 3 : The volume of invalid addresses with a flag "postal sending" and an invalidation cause = B */
prompt
prompt KPI 3 - The volume and rate of invalid addresses with a flag Postal Sending and an invalidation cause = B

prompt CodeAppli;CodePays;NbAdr;ndAdrInvalB;Rate

select a.codAppli || ';' || a.codPays || ';' || a.nbAdr || ';' || decode(b.nbInvalB, NULL, 0, b.nbInvalB) || ';' || decode(b.nbInvalB, NULL, 0, decode (a.nbAdr, 0, 0, round(b.nbInvalB / a.nbAdr * 100,2))) from
(select scode_app_send codAppli, scode_pays codPays, count(*) nbAdr from adr_post where scode_app_send is not null group by scode_app_send, scode_pays) a,
(select scode_app_send codAppli, scode_pays codPays, count(*) nbInvalB from adr_post where sstatut_medium='I' and scode_app_send is not null and stype_invalidite='B' group by scode_app_send, scode_pays) b
where a.codAppli=b.codAppli(+) and a.codPays=b.codPays(+);


spool off;

exit;