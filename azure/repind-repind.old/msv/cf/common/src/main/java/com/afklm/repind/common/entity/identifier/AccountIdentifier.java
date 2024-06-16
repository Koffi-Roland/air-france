package com.afklm.repind.common.entity.identifier;

import com.afklm.repind.common.entity.common.AbstractGenericField;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "ACCOUNT_IDENTIFIER")
@Getter
@Setter
/**
 * It's an entity designed to store data about the different account identifier of a user
 */
public class AccountIdentifier extends AbstractGenericField implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ISEQ_ID_ACCOUNTDATA")
    @SequenceGenerator(name = "ISEQ_ID_ACCOUNTDATA", sequenceName = "ISEQ_ID_ACCOUNTDATA",
            allocationSize = 1)
    @Column(name = "ID", length = 12, nullable = false, unique = true, updatable = false)
    private Integer id;


    /**
     * accountIdentifier
     */
    @Column(name = "ACCOUNT_IDENTIFIER", length = 8)
    private String accountId;


	/**
	 * sgin
	 */
	@Column(name="SGIN", length=12)
	private String sgin;

    /**
     * fbIdentifier
     */
    @Column(name = "FB_IDENTIFIER", length = 16, columnDefinition = "nvarchar2")
    private String fbIdentifier;

    /**
     * emailIdentifier
     */
    @Column(name = "EMAIL_IDENTIFIER", length = 60, columnDefinition = "nvarchar2")
    private String emailIdentifier;


    /**
     * socialNetworkId
     */
    @Column(name = "SOCIAL_NETWORK_ID")
    private String socialNetworkId;


    /**
     * lastSocialNetworkId
     */
    @Column(name = "LAST_SOCIAL_NETWORK_ID")
    private String lastSocialNetworkId;

}
