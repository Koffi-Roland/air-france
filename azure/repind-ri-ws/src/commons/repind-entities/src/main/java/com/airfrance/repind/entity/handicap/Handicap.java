package com.airfrance.repind.entity.handicap;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name="HANDICAP")
public class Handicap implements Serializable {
	
    /**
     * id
     */
    private static final long serialVersionUID = 1L;
            
    /**
     * handicapId

     */
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ISEQ_HANDICAP")
    @SequenceGenerator(name="ISEQ_HANDICAP", sequenceName="ISEQ_HANDICAP", allocationSize=1)
    @Column(name="HANDICAP_ID")
    private Long handicapId;

    /**
     * gin
     */
    @Column(name="SGIN", length=12, nullable=false)
    private String gin;
        
    /**
     * type
     */
    @Column(name="STYPE", length=10, nullable=false)
    private String type;

    /**
     * code
     */
    @Column(name="SCODE", length=10, nullable=false)
    private String code;
    
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
     * inferredData
     */
    // 1 <-> * 
    @JsonManagedReference
    @OneToMany(mappedBy="handicap", cascade=CascadeType.ALL, fetch=FetchType.LAZY, orphanRemoval = true)
    private List<HandicapData> handicapData;

}
