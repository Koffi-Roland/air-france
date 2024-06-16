package com.afklm.repind.commonpp.entity.paymentDetails;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ForeignKey;

import java.io.Serializable;

@Entity
@Table(name = "FIELDS")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FieldsEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * fieldId
     */
    @Id
    @Column(name = "FIELDID")
    @SequenceGenerator(name = "ISEQ_FIELDS_ID", sequenceName = "ISEQ_FIELDS_ID",
            allocationSize = 1)
    @GeneratedValue(generator = "ISEQ_FIELDS_ID")
    private Integer fieldId;

    /**
     * paymentFieldCode
     */
    private String paymentFieldCode;

    /**
     * paymentFieldPreference
     */
    private String paymentFieldPreference;

    /**
     * paymentdetails
     */
    // * <-> 1
    @ManyToOne()
    @JoinColumn(nullable = false)
    @ForeignKey(name = "FK_FIELDS_PAYMENTDETAILS")
    private PaymentDetailsEntity paymentdetails;

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
        final FieldsEntity other = (FieldsEntity) obj;
        return super.equals(other);
    }

    /**
     * {@inheritDoc}
     *
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return super.hashCode();
    }

}
