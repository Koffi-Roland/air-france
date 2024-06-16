package com.afklm.repind.common.entity.preferences;

import com.afklm.repind.common.entity.individual.Individu;
import jakarta.persistence.*;
import lombok.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "COMMUNICATION_PREFERENCES")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommunicationPreferencesEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Log log = LogFactory.getLog(CommunicationPreferencesEntity.class);
    @Id
    @Column(
            name = "COM_PREF_ID",
            length = 12,
            nullable = false,
            unique = true,
            updatable = false
    )
    private Long comPrefId;

    @Column(name = "ACCOUNT_IDENTIFIER",length = 8,updatable = false)
    private String accountIdentifier;

    @Column(name = "COM_GROUP_TYPE",length = 7)
    private String comGroupType;

    @Column(name = "COM_TYPE",length = 7)
    private String comType;

    @Column(name = "SUBSCRIBE",length = 1)
    private String subscribe;

    @Column(name = "MEDIA1",length = 1)
    private String media1;

    @Column(name = "DOMAIN",length = 7)
    private String domain;

    @Column(name = "MEDIA2",length = 1)
    private String media2;

    @Column(name = "CREATION_DATE",length = 6)
    private Timestamp dateCreation;

    @Column(name = "MEDIA3",length = 1)
    private String media3;

    @Column(name = "CREATION_SIGNATURE",length = 16)
    private String signatureCreation;

    @Column(name = "MEDIA4",length = 1)
    private String media4;

    @Column(name = "CREATION_SITE",length = 10)
    private String siteCreation;

    @Column(name = "MEDIA5",length = 1)
    private String media5;

    @Column(name = "DATE_OPTIN",length = 6)
    private Timestamp dateOptin;

    @Column(name = "DATE_OPTIN_PARTNERS",length = 6)
    private Timestamp dateOptinPartners;

    @Column(name = "DATE_OF_ENTRY",length = 6)
    private Timestamp dateEntry;

    @Column(name = "MODIFICATION_DATE",length = 6)
    private Timestamp dateModification;

    @Column(name = "MODIFICATION_SIGNATURE",length = 16)
    private String signatureModification;

    @Column(name = "MODIFICATION_SITE",length = 10)
    private String siteModification;

    @Column(name = "OPTIN_PARTNERS",length = 1)
    private String optinPartners;

    @Column(name = "CHANNEL",length = 16)
    private String channel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SGIN")
    private Individu individu;
}
