package com.afklm.repindpp.paymentpreference.dao;

import com.afklm.repindpp.paymentpreference.entity.Fields;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FieldsRepository extends JpaRepository<Fields, Integer> {
	long deleteByPaymentdetailsPaymentId(Integer paymentId);
	List<Fields> findByPaymentdetailsPaymentId(Integer paymentId);
}
