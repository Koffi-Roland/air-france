package com.afklm.cati.common.repository;

import com.afklm.cati.common.entity.RefComPrefDgt;
import com.afklm.cati.common.entity.RefComPrefMl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefComPrefMlRepository extends JpaRepository<RefComPrefMl, Integer> {

    Long countByRefComPrefDgt(RefComPrefDgt refComPrefDgt);

}
