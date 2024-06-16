package com.afklm.repind.common.entity.identifier;

import com.afklm.repind.common.entity.common.AbstractGenericField;
import com.afklm.repind.common.entity.individual.Individu;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "EXTERNAL_IDENTIFIER")
@Getter
@Setter
/**
 * It's an entity designed to store data about the different external identifier of a user
 */
public class ExternalIdentifier extends AbstractGenericField implements Serializable {
    private static final long serialVersionUID = 1L;
    /*PROTECTED REGION END*/

    /**
     * IDENTIFIER_ID
     */
    @Id
    @Column(name = "IDENTIFIER_ID", length = 12, nullable = false, unique = true, updatable = false)
    private Long identifierId;

    /**
     * identifier
     */
    @Column(name = "IDENTIFIER", length=500, nullable=false)
    private String identifier;

    /**
     * type
     */
    @Column(name = "TYPE", length=100, updatable=false, nullable=false)
    private String type;

    /**
     * last seen date
     */
    @Column(name = "LAST_SEEN_DATE")
    private Date lastSeenDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SGIN")
    private Individu individu;
}
