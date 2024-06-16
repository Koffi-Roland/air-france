package com.afklm.repind.msv.doctor.role.repository;


import com.afklm.repind.msv.doctor.role.entity.BusinessRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BusinessRoleRepository extends JpaRepository<BusinessRole, String> {

	Optional<BusinessRole> findBusinessRuleByGinIndAndType(String ginInd , String type);
	Optional<BusinessRole> findBusinessRuleByNumeroContratAndType(String roleId , String type);

}