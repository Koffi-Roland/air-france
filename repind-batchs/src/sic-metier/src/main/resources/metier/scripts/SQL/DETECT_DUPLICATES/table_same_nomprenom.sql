-- get 1.000.000 distinct NOMPRENOM from a pool of candidates NOMPRENOM
BEGIN
EXECUTE IMMEDIATE 'CREATE TABLE DQ_MGE_SAME_NOMPRE AS
    select NOMPRENOM,nb
        from DQ_MGE_SAME_NOMPRE_CANDIDATES
    WHERE is_checked=0 and ROWNUM <= 1000000
    order by NOMPRENOM';
END;