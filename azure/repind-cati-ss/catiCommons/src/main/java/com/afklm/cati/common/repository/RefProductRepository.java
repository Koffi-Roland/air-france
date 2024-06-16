package com.afklm.cati.common.repository;

import com.afklm.cati.common.entity.RefProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RefProductRepository extends JpaRepository<RefProduct, Integer> {

    @Query("select ref.productId from RefProduct ref where ref.contractType = 'C'")
    List<Integer> getAllCustomerRefProduct();

}
