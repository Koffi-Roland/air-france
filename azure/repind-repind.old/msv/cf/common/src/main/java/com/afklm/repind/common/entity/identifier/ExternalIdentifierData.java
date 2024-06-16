package com.afklm.repind.common.entity.identifier;

import com.afklm.repind.common.entity.common.AbstractGenericField;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "EXTERNAL_IDENTIFIER_DATA")
@Getter
@Setter
public class ExternalIdentifierData extends AbstractGenericField implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * identifierDataId
     */
    @Id
    @Column(name = "IDENTIFIER_DATA_ID")
    private Long identifierDataId;
    /**
     * identifierId
     */
    @Column(name = "IDENTIFIER_ID")
    private Long identifierId;
    /**
     * Key
     */
    @Column(name = "KEY")
    private String key;

    /**
     * Value
     */
    @Column(name = "VALUE")
    private String value;
}
