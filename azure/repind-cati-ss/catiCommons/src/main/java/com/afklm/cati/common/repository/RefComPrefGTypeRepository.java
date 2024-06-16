package com.afklm.cati.common.repository;

import com.afklm.cati.common.entity.RefComPrefGType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RefComPrefGTypeRepository extends JpaRepository<RefComPrefGType, String> {

    List<RefComPrefGType> findAllByOrderByCodeGType(Pageable pageable);

    List<RefComPrefGType> findAllByOrderByCodeGType();
}
