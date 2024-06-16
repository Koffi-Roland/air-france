package com.afklm.repind.common.entity.individual;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "USAGE_CLIENTS")
@Getter
@Setter
/**
 * It's an entity designed to store data about usage client of an individual
 */
public class UsageClientEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "SGIN", length = 12, nullable = false, unique = true, updatable = false)
    private String gin;

    /**
     * application Code
     */
    @Column(name = "SCODE")
    private String code;

    /**
     * authorized modification
     */
    @Column(name = "SCONST")
    private String authorizedModification;

    /**
     * date_modification
     */
    @Column(name = "DDATE_MODIFICATION")
    private Date dateModification;

    /**
     * srin
     */
    @Column(name = "SRIN")
    private String srin;
}
