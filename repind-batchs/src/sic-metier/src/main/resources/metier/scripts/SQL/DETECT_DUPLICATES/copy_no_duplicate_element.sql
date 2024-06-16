BEGIN
    insert into DQ_NP_TELECOM_EMAIL_NODUP ("NOMPRENOM", "ELEMENT_DUPLICATE", "SGINS", "NB_CONTRACT", "NB_GINS")
    select NOMPRENOM, ELEMENT_DUPLICATE, SGINS, nb_contract, nb_gins
    from DQ_NOMPRENOM_EMAIL_OR_TEL
    where is_duplicate = 0;

    COMMIT;
END;