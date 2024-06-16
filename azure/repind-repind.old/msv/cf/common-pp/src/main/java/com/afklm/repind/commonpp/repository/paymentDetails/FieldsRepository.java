package com.afklm.repind.commonpp.repository.paymentDetails;

import com.afklm.repind.commonpp.entity.paymentDetails.FieldsEntity;
import com.afklm.repind.commonpp.entity.paymentDetails.PaymentDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FieldsRepository extends JpaRepository<FieldsEntity, Long> {

    @Modifying
    @Query(value = "DELETE FROM FIELDS WHERE PAYMENTDETAILS_PAYMENTID IN :ids", nativeQuery = true)
    void deleteByPaymentIdIn(@Param("ids") List<Integer> paymentIds);
}
