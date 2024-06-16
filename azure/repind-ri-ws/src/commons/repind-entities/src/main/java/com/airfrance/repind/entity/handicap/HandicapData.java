package com.airfrance.repind.entity.handicap;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Getter
@Setter
@Entity
@Table(name="HANDICAP_DATA")
public class HandicapData implements Serializable {

    private static final long serialVersionUID = 1L;

    
    /**
     * preferenceDataId
     */
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ISEQ_HANDICAP_DATA")
    @SequenceGenerator(name="ISEQ_HANDICAP_DATA", sequenceName="ISEQ_HANDICAP_DATA", allocationSize=1)
    @Column(name="HANDICAP_DATA_ID")
    private Long handicapDataId;
        
            
    /**
     * key
     */
    @Column(name="SKEY", length=60, nullable=false)
    private String key;
        
            
    /**
     * value
     */
    @Column(name="SVALUE", length=500)
    private String value;

    /**
     * dateCreation
     */
    @Column(name="DDATE_CREATION")
    private Date dateCreation;
        
    /**
     * signatureCreation
     */
    @Column(name="SSIGNATURE_CREATION", length=16)
    private String signatureCreation;
        
    /**
     * siteCreation
     */
    @Column(name="SSITE_CREATION", length=10)
    private String siteCreation;
        
    /**
     * dateModification
     */
    @Column(name="DDATE_MODIFICATION")
    private Date dateModification;
        
    /**
     * signatureModification
     */
    @Column(name="SSIGNATURE_MODIFICATION", length=16)
    private String signatureModification;
        
    /**
     * siteModification
     */
    @Column(name="SSITE_MODIFICATION", length=10)
    private String siteModification;
    /**
     * handicap
     */
    // * <-> 1
    @JsonBackReference
    @ManyToOne()
    @JoinColumn(name="HANDICAP_ID", referencedColumnName = "HANDICAP_ID", nullable=false, foreignKey = @ForeignKey(name = "FK_HANDICAP_ID"))
    private Handicap handicap;

}
