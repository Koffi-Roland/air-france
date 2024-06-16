package com.afklm.repind.common.entity.preferences;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.afklm.repind.common.entity.individual.Individu;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "PREFERENCE")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PreferenceEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Log log = LogFactory.getLog(PreferenceEntity.class);

    @Id
    @Column(name = "PREFERENCE_ID", length = 12, nullable = false, unique = true, updatable = false)
    private Long preferenceId;

    @Column(name = "DDATE_MODIFICATION")
    private Date dateModification;

    @Column(name = "SSITE_MODIFICATION",length = 10)
    private String siteModification;

    @Column(name = "SSIGNATURE_MODIFICATION",length = 16)
    private String signatureModification;

    @Column(name = "STYPE",length = 3,nullable = false)
    private String type;

    @Column(name = "ILINK",length = 12)
    private Long link;

    @Column(name = "DDATE_CREATION")
    private Date dateCreation;

    @Column(name = "SSITE_CREATION",length = 10)
    private String siteCreation;

    @Column(name = "SSIGNATURE_CREATION",length = 16)
    private String signatureCreation;

    @OneToMany(targetEntity = PreferenceDataEntity.class, mappedBy = "preference", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<PreferenceDataEntity> preferenceData;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SGIN")
    private Individu individu;

}
