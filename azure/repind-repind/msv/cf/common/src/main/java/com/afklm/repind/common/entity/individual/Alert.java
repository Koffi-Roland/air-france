package com.afklm.repind.common.entity.individual;

import com.afklm.repind.common.entity.preferences.PreferenceEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ALERT")
public class Alert implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Log log = LogFactory.getLog(Alert.class);
    /**
     * alertId
     */
    @Id
    @Column(name = "ALERT_ID", length = 12, nullable = false, unique = true)
    @SequenceGenerator(name = "SEQ_ALERT", sequenceName = "SEQ_ALERT",
            allocationSize = 1)
    @GeneratedValue(generator = "SEQ_ALERT")
    private Integer alertId;

    /**
     * type
     */
    @Column(name="TYPE", length=2)
    private String type;

    /**
     * creationDate
     */
    @Column(name="CREATION_DATE")
    private Date creationDate;

    /**
     * creationSignature
     */
    @Column(name="CREATION_SIGNATURE")
    private String creationSignature;

    /**
     * creationSite
     */
    @Column(name="CREATION_SITE")
    private String creationSite;

    /**
     * modificationDate
     */
    @Column(name="MODIFICATION_DATE")
    private Date modificationDate;

    /**
     * modificationSignature
     */
    @Column(name="MODIFICATION_SIGNATURE")
    private String modificationSignature;

    /**
     * modificationSite
     */
    @Column(name="MODIFICATION_SITE")
    private String modificationSite;


    /**
     * optIn
     */
    @Column(name = "OPTIN")
    private String optin;

    /**
     * alertdata
     */
    @OneToMany(targetEntity = AlertData.class, mappedBy = "alert", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<AlertData> alertdata;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SGIN")
    private Individu individu;
}
