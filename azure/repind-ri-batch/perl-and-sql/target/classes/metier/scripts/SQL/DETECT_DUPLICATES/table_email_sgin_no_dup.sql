BEGIN
    EXECUTE IMMEDIATE '
    CREATE TABLE DQ_MGE_EMAIL_GIN_NO_DUP AS
        select distinct emails.SEMAIL, emails.SGIN
        from DQ_MGE_SAME_NOMPRE_GIN np inner join SIC2.EMAILS emails on emails.SGIN = np.SGIN
        where emails.SSTATUT_MEDIUM in (''V'', ''I'')';
END;