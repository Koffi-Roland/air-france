BEGIN
    insert into DQ_NP_TELECOM_EMAIL_NODUP ("NOMPRENOM", "ELEMENT_DUPLICATE", "SGINS", "NB_CONTRACT", "NB_GINS")
    select NOMPRENOM,
           SNORM_INTER_PHONE_NUMBER || ',' || EMAIL,
           case
               when TEL_NB_GINS > EMAIL_NB_GINS THEN TEL_SGINS
               else EMAIL_SGINS
               end as SGINS
            ,
           case
               when TEL_NB_GINS > EMAIL_NB_GINS THEN TEL_NB_CONTRACT
               else EMAIL_NB_CONTRACT
               end as NB_CONTRACT,
           case
               when TEL_NB_GINS > EMAIL_NB_GINS THEN TEL_NB_GINS
               else EMAIL_NB_GINS end as NB_GINS
    from DQ_NOMPRENOM_TELECOM_EMAIL
    where is_duplicate = 0;

    COMMIT;
END;