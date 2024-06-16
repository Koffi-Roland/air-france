package com.airfrance.repind.dao.individu;

import com.airfrance.repind.entity.individu.ForgottenIndividual;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/*PROTECTED REGION END*/

/**
 * <p>Title : IForgottenIndividualDAO.java</p>
 * BO: ForgottenIndividual
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
@Repository
public interface ForgottenIndividualRepository extends JpaRepository<ForgottenIndividual, Long> {

	@Query(value = "SELECT SEQ_FORGOTTEN_INDIVIDUAL.NEXTVAL AS ST FROM DUAL", nativeQuery = true)
	Long getNextValue();

	@Query(value="select fi from ForgottenIndividual fi where fi.identifier=:identifier and fi.identifierType=:identifierType")
	public List<ForgottenIndividual> findByIdentifier(@Param("identifier") String identifier, @Param("identifierType") String identifierType);
	
	@Query(value="select fi from ForgottenIndividual fi where fi.context = :context")
	public List<ForgottenIndividual> findByContext(@Param("context") String context);
	
	@Query(value="select fi from ForgottenIndividual fi where fi.context = :context and fi.modificationDate < sysdate - :periodInDays")
	public List<ForgottenIndividual> findByContextWithPeriod(@Param("context") String context, @Param("periodInDays") int periodInDays);
}
