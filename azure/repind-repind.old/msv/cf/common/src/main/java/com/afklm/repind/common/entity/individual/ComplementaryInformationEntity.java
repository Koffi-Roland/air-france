package com.afklm.repind.common.entity.individual;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Getter
@Setter
@Table(name = "DELEGATION_DATA_INFO")
@AllArgsConstructor
@NoArgsConstructor
/**
 * It's an entity designed to store data about complementary information related to a delegation
 */
public class ComplementaryInformationEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "DELEGATION_DATA_INFO_ID")
    private String delegationDataInfoId;

    /**
     * Delegation_data_id
     */
    @Column(name = "DELEGATION_DATA_ID")
    private String delegationDataId;

    /**
     * type
     */
    @Column(name = "STYPE")
    private String type;

    /**
     * key
     */
    @Column(name = "SKEY")
    private String key;

    /**
     * value
     */
    @Column(name = "SVALUE")
    private String value;
}
