-- for every duplicated nomprenom get all their identifiers, theirs contract, status_individus
BEGIN
    EXECUTE IMMEDIATE 'CREATE TABLE DQ_MGE_SAME_NOMPRE_GIN AS
        select distinct ia.SGIN, NOMPRENOM,
            ia.SSTATUT_INDIVIDU,
            COALESCE((select distinct 1 from SIC2.ROLE_CONTRATS rc where rc.SGIN = ia.SGIN and rc.SETAT = ''C''), 0) as have_contract
        from DQ_MGE_SAME_NOMPRE
            inner join SIC2.INDIVIDUS_ALL ia on ia.SNOM || ia.SPRENOM = NOMPRENOM
        where rownum < 10000000000 and
            SSTATUT_INDIVIDU in (''V'', ''P'')
            AND not exists(select 1 from SIC2.BUSINESS_ROLE br where br.SGIN_IND = ia.SGIN and br.STYPE = ''G'') order by NOMPRENOM';
END;