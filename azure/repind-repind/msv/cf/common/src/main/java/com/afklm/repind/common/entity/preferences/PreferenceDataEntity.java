package com.afklm.repind.common.entity.preferences;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "PREFERENCE_DATA")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PreferenceDataEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Log log = LogFactory.getLog(PreferenceDataEntity.class);

    @Id
    @Column(name = "PREFERENCE_DATA_ID", length = 12, nullable = false, unique = true, updatable = false)
    private Integer preferenceDataId;

    @Column(name = "DDATE_MODIFICATION")
    private Date dateModification;

    @Column(name = "SSITE_MODIFICATION",length = 10)
    private String siteModification;

    @Column(name = "SSIGNATURE_MODIFICATION",length = 16)
    private String signatureModification;

    @Column(name = "SKEY",length = 60,nullable = false)
    private String key;

    @Column(name = "SVALUE",length = 1500)
    private String value;

    @Column(name = "DDATE_CREATION")
    private Date dateCreation;

    @Column(name = "SSITE_CREATION",length = 10)
    private String siteCreation;

    @Column(name = "SSIGNATURE_CREATION",length = 16)
    private String signatureCreation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PREFERENCE_ID")
    private PreferenceEntity preference;
}
