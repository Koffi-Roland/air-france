-- in some cases SGINS is more than 4000Bytes so CLOB datatype can handle these cases
BEGIN
    EXECUTE IMMEDIATE 'CREATE TABLE DQ_NOMPRENOM_TELECOM_EMAIL_TMP AS
    select ROWNUM as ID, npt.NOMPRENOM, SNORM_INTER_PHONE_NUMBER, npt.SGINS as TEL_SGINS, npt.nb_contract as TEL_NB_CONTRACT, npt.nb_gins as TEL_NB_GINS, EMAIL, npe.SGINS as EMAIL_SGINS, npe.nb_contract as EMAIL_NB_CONTRACT, npe.nb_gins as EMAIL_NB_GINS
    from DQ_MGE_SAME_NOMPRE_TELECOM_GIN npt inner join DQ_MGE_SAME_NOMPRE_EMAIL_GIN npe on npt.NOMPRENOM = npe.NOMPRENOM';

    EXECUTE IMMEDIATE 'CREATE TABLE DQ_NOMPRENOM_TELECOM_EMAIL AS
    select ID, NOMPRENOM, SNORM_INTER_PHONE_NUMBER, TEL_SGINS, TEL_NB_CONTRACT, TEL_NB_GINS, EMAIL, EMAIL_SGINS, EMAIL_NB_CONTRACT, EMAIL_NB_GINS,
           COALESCE((select distinct 1 from DQ_NOMPRENOM_TELECOM_EMAIL_TMP tmp2 where tmp1.ID != tmp2.ID and tmp1.NOMPRENOM = tmp2.NOMPRENOM), 0) as is_duplicate
    from DQ_NOMPRENOM_TELECOM_EMAIL_TMP tmp1';

    EXECUTE IMMEDIATE 'CREATE TABLE DQ_NP_TELECOM_EMAIL_NODUP
                         (	"NOMPRENOM" VARCHAR2(4000 BYTE),
                              "ELEMENT_DUPLICATE" VARCHAR2(4000 BYTE),
                              "SGINS" CLOB,
                              "NB_CONTRACT" NUMBER,
                              "NB_GINS" NUMBER,
                              CONSTRAINT ND_NP_PK PRIMARY KEY (NOMPRENOM)
                         )';
END;