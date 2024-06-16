package com.afklm.repind.common.entity.individual;

import com.afklm.repind.common.entity.common.AbstractGenericField;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Getter
@Setter
@Table(name = "DELEGATION_DATA")
/**
 * It's an entity designed to store data about a delegation
 */
public class DelegationData extends AbstractGenericField implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "DELEGATION_DATA_ID")
    private String delegationDataId;

    /**
     * status
     */
    @Column(name = "STATUS", length = 1)
    private String status;

    /**
     * type
     */
    @Column(name = "STYPE")
    private String type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SGIN_DELEGATOR")
    private Individu delegator;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SGIN_DELEGATE")
    private Individu delegate;
}
