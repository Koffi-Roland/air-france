package com.afklm.rigui.dao.role;

import com.afklm.rigui.exception.jraf.JrafDaoException;
import com.afklm.rigui.exception.jraf.JrafDomainException;
import com.afklm.rigui.entity.role.BusinessRole;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BusinessRoleRepository extends JpaRepository<BusinessRole, Integer> {

	@Query("select br.ginInd from BusinessRole br "
			+ "where br.numeroContrat = :contractNumber ")
	List<String> getSginIndByContractNumber(@Param("contractNumber") String contractNumber);

	@Query("select br.type from BusinessRole br "
			+ "where br.numeroContrat = :contractNumber ")
	List<String> getContractTypeByContractNumber(@Param("contractNumber") String contractNumber);
	
	/**
	 * @param ginInd
	 * @return
	 */
	List<BusinessRole> findByGinInd(String ginInd);

}
