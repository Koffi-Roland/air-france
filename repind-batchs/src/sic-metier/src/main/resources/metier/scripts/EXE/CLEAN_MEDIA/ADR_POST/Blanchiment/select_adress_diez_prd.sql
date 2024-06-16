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


select ADR.sain ||';'||
       adr.sgin ||';'||
       um.SCODE_APPLICATION||';'||
       adr.sstatut_medium||';'||
       adr.scode_medium||';'||
       adr.sno_et_rue||';'||
       adr.scomplement_adresse||';'||
       adr.slocalite||';'||
       adr.sville||';'||
       adr.scode_province||';'||
       adr.scode_province||';'||
       adr.sraison_sociale||';'||
       to_char(adr.ddate_modification,'DD/MM/YYYY HH24:MI:SS')||';'||
       adr.ssignature_modification||';'||
       adr.ssite_modification
from sic2.adr_post adr , sic2.usage_mediums um where adr.sgin is not null and adr.sstatut_medium !='X'
                                                 and  adr.sain = um.sain_adr and um.SCODE_APPLICATION='ISI'
                                                 and (
                adr.SNO_ET_RUE like '%' || CHR(35)|| '%'
            or adr.SCOMPLEMENT_ADRESSE like '%' || CHR(35)|| '%'
            or adr.SLOCALITE like '%' || CHR(35)|| '%'
            or adr.SCODE_POSTAL like '%' || CHR(35)|| '%'
            or adr.SCODE_PROVINCE like '%' || CHR(35)|| '%'
            or adr.SRAISON_SOCIALE like '%' || CHR(35)|| '%'
            or adr.SVILLE like '%' || CHR(35)|| '%'
        );
PROMPT
exit;
/