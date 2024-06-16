package com.afklm.rigui.dao.reference;

import com.afklm.rigui.entity.reference.RefDelegationInfoType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefDelegationInfoTypeRepository extends JpaRepository<RefDelegationInfoType, String> {
}
