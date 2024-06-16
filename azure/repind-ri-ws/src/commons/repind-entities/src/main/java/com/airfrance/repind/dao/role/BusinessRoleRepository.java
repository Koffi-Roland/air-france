package com.airfrance.repind.dao.role;

import com.airfrance.ref.exception.jraf.JrafDaoException;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.entity.role.BusinessRole;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BusinessRoleRepository extends JpaRepository<BusinessRole, Integer> {

	
	@Query("select br from BusinessRole br left join fetch br.roleUCCR ru "
			+ "where ru.ceID = :ceID and ru.uccrID = :uccrID  and ru.etat != 'X'")
	BusinessRole getBusinessRoleByUCCRIDAndCEID(@Param("uccrID") String uccrID, @Param("ceID") String corporateEnvironmentID);
	
	/**
	 * @param cleRole
	 * @return
	 */
	BusinessRole findByCleRole(Integer cleRole);

	/**
	 * Finds all business roles associated to the moral person
	 * having The GIN gin. Returned business roles are given from the
	 * firstResultIndex and are in maximum maxResults.
	 * 
	 * @param gin
	 * @param firstResultIndex
	 * @param endIndex
	 * 
	 * @return null if no results found.
	 * 
	 * @throws JrafDaoException if a technical exception is fired.
	 */
	@Query("select br from BusinessRole br where br.ginPm = :gin")
	List<BusinessRole> findByGinPm(@Param("gin") String gin, Pageable pageable);

	/**
	 * Finds all business roles associated to the moral person
	 * having The GIN gin.
	 * 
	 * @param gin
	 * 
	 * @return null if no results found.
	 * 
	 * @throws JrafDaoException if a technical exception is fired.
	 */
	@Query("select br from BusinessRole br where br.ginPm = :gin")
	List<BusinessRole> findByGinPm(@Param("gin") String gin); 
	
	/**
	 * @param ginPM
	 * @param contractNumber
	 * @return
	 * @throws JrafDomainException
	 */
	List<BusinessRole> findByGinPmAndNumeroContrat(String ginPM, String contractNumber);
	
	@Query("select br from BusinessRole br " + "where br.ginPm = :ginPM " + "and br.roleFirme.etat = 'X' "
			+ "and br.numeroContrat = :contractNumber ")
	List<BusinessRole> findByGinPmAndNumeroContratWithStatusX(@Param("ginPM") String ginPM,
			@Param("contractNumber") String contractNumber);

	/**
	 * Finds all business roles associated to the individual
	 * having The GIN gin.
	 * 
	 * @param gin
	 * 
	 * @return null if no results found.
	 * 
	 * @throws JrafDaoException if a technical exception is fired.
	 * @throws JrafDomainException 
	 */
	@Query("select br from BusinessRole br "
			+ "where br.ginInd = :gin "
			+ "and br.roleUCCR.etat in ('C', 'P', 'U', 'V') "
			+ "and (br.roleUCCR.finValidite is null or br.roleUCCR.finValidite > to_date(sysdate)) ")
	List<BusinessRole> findValidByIndividualGin(@Param("gin") String gin);

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
	
	/**
	 * 
	 * @param ginInd
	 * @param type
	 * @return
	 */
	List<BusinessRole> findByGinIndAndType(String ginInd, String type);


	/**
	 * Finds for specified PersonneMorale and the type of business role.
	 * 
	 * @param gin   GIN of PersonneMorale
	 * @param types the types of business roles
	 * @return the list of business roles matching the criteria
	 */
	@Query("select br from BusinessRole br where br.ginPm = :gin and br.type IN :types ")
	List<BusinessRole> findByGinPmAndType(@Param("gin") String gin, @Param("types") List<String> types);

	@Transactional
	Long deleteByGinIndAndType(String ginInd, String type);
	
}
