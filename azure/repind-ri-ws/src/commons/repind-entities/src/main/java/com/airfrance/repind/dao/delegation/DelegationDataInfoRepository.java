package com.airfrance.repind.dao.delegation;

import com.airfrance.repind.entity.delegation.DelegationDataInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DelegationDataInfoRepository extends JpaRepository<DelegationDataInfo, Integer> {
}
