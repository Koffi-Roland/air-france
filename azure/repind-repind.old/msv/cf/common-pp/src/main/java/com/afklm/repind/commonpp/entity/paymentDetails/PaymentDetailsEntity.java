package com.afklm.repind.commonpp.entity.paymentDetails;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "PAYMENTDETAILS")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PaymentDetailsEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * paymentId
     */
    @Id
    @Column(name = "PAYMENTID")
    @SequenceGenerator(name = "ISEQ_PAYMENTDETAILS", sequenceName = "ISEQ_PAYMENTDETAILS",
            allocationSize = 1)
    @GeneratedValue(generator = "ISEQ_PAYMENTDETAILS")
    private Integer paymentId;

    /**
     * gin
     */
    @Column(nullable = false)
    private String gin;

    /**
     * version
     */
    @Version
    @Column(nullable = false, updatable = false)
    private Integer version;

    /**
     * paymentType
     */
    private String paymentType;

    /**
     * pointOfSell
     */
    private String pointOfSell;

    /**
     * carrier
     */
    private String carrier;

    /**
     * dateCreation
     */
    private Date dateCreation;

    /**
     * signatureCreation
     */
    private String signatureCreation;

    /**
     * siteCreation
     */
    private String siteCreation;


    /**
     * changingDate
     */
    private Date changingDate;

    /**
     * changingSignature
     */
    private String changingSignature;

    /**
     * changingSite
     */
    private String changingSite;

    /**
     * paymentGroup
     */
    private String paymentGroup;

    /**
     * paymentMethod
     */
    private String paymentMethod;

    /**
     * preferred
     */
    private String preferred;

    /**
     * corporate
     */
    private String corporate;

    /**
     * cardName
     */
    private String cardName;

    /**
     * ipAdresse
     */
    private String ipAdresse;

    /**
     * isTokenized
     */
    private String isTokenized;

    /**
     * {@inheritDoc}
     *
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PaymentDetailsEntity other = (PaymentDetailsEntity) obj;

        // TODO: writes or generates equals method

        return super.equals(other);
    }

    /**
     * {@inheritDoc}
     *
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        int result = super.hashCode();

        // TODO: writes or generates hashcode method

        return result;
    }
}
