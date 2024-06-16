package com.afklm.repind.msv.search.gin.by.contract.repository;

import com.afklm.repind.common.entity.role.BusinessRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface IBusinessRoleRepository extends JpaRepository<BusinessRole, Integer>  {
	@Query("select br from BusinessRole br where br.numeroContrat = :num and br.ginInd is not null and br.type = 'C'")
	Collection<BusinessRole> findByContractNumber(@Param("num") String num);
}
