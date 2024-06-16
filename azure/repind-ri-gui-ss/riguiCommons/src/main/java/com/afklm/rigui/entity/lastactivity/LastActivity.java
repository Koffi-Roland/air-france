package com.afklm.rigui.entity.lastactivity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Last activity entity
 */

@Entity
@Table(name = "LAST_ACTIVITY")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LastActivity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * last activity identifier
     */
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="LAST_ACTIVITY_ID")
    private Long id;

    /**
     * Individual GIN number
     */
    @Column(name="SGIN", length=12, nullable=false)
    private String gin;

    /**
     * Signature for individual modification
     */
    @Column(name="SSIGNATURE_MODIFICATION", length=16)
    private String signatureModification;

    /**
     * Site where modification originated
     */
    @Column(name="SSITE_MODIFICATION", length=10)
    private String siteModification;

    /**
     * last modification date
     */
    @Column(name="DDATE_MODIFICATION")
    private Date dateModification;

    /**
     * source modification
     */
    @Column(name="SSOURCE_MODIFICATION")
    private String sourceModification;

}
