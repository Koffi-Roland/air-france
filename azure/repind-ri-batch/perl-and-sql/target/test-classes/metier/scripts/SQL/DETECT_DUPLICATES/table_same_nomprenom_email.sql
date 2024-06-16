/*
 - regroupe les gins dupliqués by (NOMPRENOM + email) dans une seule ligne séparés par des virgules
 - rtrim(xmlagg(xmlelement(x, np.SGIN || ',').EXTRACT(' // text() ') ORDER BY ROWNUM).getClobVal(), ',') AS "SGINS"
            -> is used because this field can have more than 4000Bytes(max_string_size)
 - after merge of all duplicates we can use only varchar2 with this query:
            LISTAGG(np.SGIN, '', '') WITHIN GROUP (ORDER BY ROWNUM) "SGINS"
 */
BEGIN
    EXECUTE IMMEDIATE '
    CREATE TABLE DQ_MGE_SAME_NOMPRE_EMAIL_GIN AS
        select NOMPRENOM,
            emails.SEMAIL as EMAIL,
            rtrim(xmlagg(xmlelement(x, np.SGIN || '','').EXTRACT('' // text() '') ORDER BY ROWNUM).getClobVal(), '','') AS "SGINS",
            SUM(np.have_contract) as nb_contract,
            count(*) as nb_gins
        from DQ_MGE_SAME_NOMPRE_GIN np
            inner join DQ_MGE_EMAIL_GIN_NO_DUP emails on emails.SGIN = np.SGIN
        group by NOMPRENOM, emails.SEMAIL having SUM(np.have_contract) <= 1 AND count(*) > 1 order by NOMPRENOM';
END;