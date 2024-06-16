LOAD DATA
TRUNCATE
INTO TABLE SIC2.TMP_SOC_ID
-- WHEN (0) = '............'
FIELDS TERMINATED BY "," OPTIONALLY ENCLOSED BY '"'
TRAILING NULLCOLS
(
        ID                              SEQUENCE (MAX,1),
        GIN                             CHAR(12),
        SOCIAL_ID               CHAR(500),
        PNM_AIRLINE     CHAR(500),
        PNM_NAME        CHAR(500),
        MEDIA_TYPE      CHAR(100)
)
