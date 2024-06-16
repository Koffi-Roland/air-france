package com.afklm.rigui.dao.delegation;

import com.afklm.rigui.entity.delegation.DelegationDataInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DelegationDataInfoRepository extends JpaRepository<DelegationDataInfo, Integer> {
}
