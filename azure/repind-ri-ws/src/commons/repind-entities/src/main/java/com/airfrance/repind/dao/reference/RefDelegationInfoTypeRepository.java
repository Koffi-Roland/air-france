package com.airfrance.repind.dao.reference;

import com.airfrance.repind.entity.reference.RefDelegationInfoType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefDelegationInfoTypeRepository extends JpaRepository<RefDelegationInfoType, String> {
}
