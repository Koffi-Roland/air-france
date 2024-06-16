package com.afklm.repindpp.paymentpreference.dao;

import com.afklm.repindpp.paymentpreference.entity.PaymentDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface PaymentDetailsRepository extends JpaRepository<PaymentDetails, Integer> {
	Long deleteByGin(String gin);
	List<PaymentDetails> findByIsTokenizedOrIsTokenized(String tokenised1, String tokenized2);
	List<PaymentDetails> findByGin(String gin);
	List<PaymentDetails> findByGinAndPaymentId(String gin, Integer paymentid);
	List<PaymentDetails> findByGinBetweenAndPaymentGroupIn(String ginDebut, String ginFin, Collection<String> in);
	List<PaymentDetails> findByGinInAndPaymentGroupIn(Collection<String> gin, Collection<String> in);
	
	@Query("select pd from PaymentDetails pd where pd.isTokenized in ('N', 'E') and pd.paymentGroup not in ('CC', 'DC')")
	List<PaymentDetails> findTokenInAndPaymentgroupNotIn();

	@Query("SELECT p FROM PaymentDetails p WHERE p.changingDate > TO_DATE(sysdate - :days) OR p.dateCreation > TO_DATE(sysdate - :days)")
	//@Query("SELECT p FROM PaymentDetails p WHERE p.dateCreation > TO_DATE(sysdate - :days)")
	List<PaymentDetails> getByLastModificationDate(@Param("days") String days);

	@Query("SELECT p FROM PaymentDetails p WHERE p.dateCreation > TO_DATE(sysdate - :maxDays) AND p.dateCreation < TO_DATE(sysdate - :minDays) ")
	List<PaymentDetails> getByModificationDate(@Param("minDays") String minDays, @Param("maxDays") String maxDays);
}
