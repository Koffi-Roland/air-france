--  recupère les numéros de télephone et gins des NOMPRENOM dupliqués
BEGIN
    EXECUTE IMMEDIATE '
    CREATE TABLE DQ_MGE_TELECOM_GIN_NO_DUP AS
        select distinct tel.SNORM_INTER_PHONE_NUMBER, tel.SGIN
        from DQ_MGE_SAME_NOMPRE_GIN np inner join SIC2.TELECOMS tel on np.SGIN = tel.SGIN
        where tel.SSTATUT_MEDIUM in (''V'', ''I'') AND tel.SNORM_INTER_PHONE_NUMBER is not null';
END;