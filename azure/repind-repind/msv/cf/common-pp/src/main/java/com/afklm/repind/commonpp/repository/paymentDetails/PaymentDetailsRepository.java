package com.afklm.repind.commonpp.repository.paymentDetails;

import com.afklm.repind.commonpp.entity.paymentDetails.PaymentDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentDetailsRepository extends JpaRepository<PaymentDetailsEntity, Integer> {

    List<PaymentDetailsEntity> findByGin(String gin);

    @Modifying
    @Query(value = "DELETE FROM PAYMENTDETAILS WHERE GIN = :gin", nativeQuery = true)
    void deleteByGin(@Param("gin") String gin);
}
