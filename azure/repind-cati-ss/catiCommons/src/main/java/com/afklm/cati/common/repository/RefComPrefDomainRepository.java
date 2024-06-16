package com.afklm.cati.common.repository;

import com.afklm.cati.common.entity.RefComPrefDomain;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RefComPrefDomainRepository extends JpaRepository<RefComPrefDomain, String> {

    List<RefComPrefDomain> findAllByOrderByCodeDomain(Pageable pageable);

    List<RefComPrefDomain> findAllByOrderByCodeDomain();

}
