package com.afklm.repind.common.entity.preferences;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "MARKET_LANGUAGE")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MarketLanguageEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final Log log = LogFactory.getLog(MarketLanguageEntity.class);
    @Id
    @Column(
            name = "MARKET_LANGUAGE_ID",
            length = 12,
            nullable = false,
            unique = true,
            updatable = false
    )
    private Integer marketLanguageId;

    @Column(
            name = "COM_PREF_ID",
            length = 12,
            unique = true,
            updatable = false
    )
    private Long comPrefId;

    @Column(name = "MEDIA1",length = 1)
    private String media1;

    @Column(name = "CREATION_DATE",length = 6)
    private Timestamp dateCreation;

    @Column(name = "MEDIA2",length = 1)
    private String media2;

    @Column(name = "CREATION_SIGNATURE",length = 16,nullable = false)
    private String signatureCreation;

    @Column(name = "MEDIA3",length = 1)
    private String media3;

    @Column(name = "CREATION_SITE",length = 10,nullable = false)
    private String siteCreation;

    @Column(name = "MEDIA4",length = 1)
    private String media4;

    @Column(name = "DATE_OPTIN",length = 6)
    private Timestamp dateOptin;

    @Column(name = "MEDIA5",length = 1)
    private String media5;

    @Column(name = "LANGUAGE_CODE",length = 2)
    private String languageCode;

    @Column(name = "MARKET",length = 3,nullable = false)
    private String market;

    @Column(name = "MODIFICATION_DATE",length = 6)
    private Timestamp dateModification;

    @Column(name = "MODIFICATION_SIGNATURE",length = 16)
    private String signatureModification;

    @Column(name = "MODIFICATION_SITE",length = 10)
    private String siteModification;

    @Column(name = "OPTIN",length = 1,nullable = false)
    private String optin;
}
