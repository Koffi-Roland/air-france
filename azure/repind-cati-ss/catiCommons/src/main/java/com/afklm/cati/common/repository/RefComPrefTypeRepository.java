package com.afklm.cati.common.repository;

import com.afklm.cati.common.entity.RefComPrefType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RefComPrefTypeRepository extends JpaRepository<RefComPrefType, String> {

    List<RefComPrefType> findAllByOrderByCodeType(Pageable pageable);

    List<RefComPrefType> findAllByOrderByCodeType();

}
