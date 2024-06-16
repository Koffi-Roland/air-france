package com.afklm.repind.common.repository.role;

import com.afklm.repind.common.entity.role.RoleContract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
/**
 * A repository used to fetch data about the role contract
 */
public interface RoleContractRepository extends JpaRepository<RoleContract, String> {

    /**
     * @param email email
     * @return number of the FP contracts owned by indivuals sharing same valid email
     */
    @Query(value = "select count(rc.srin) from EMAILS e, ROLE_CONTRATS rc "
            + "where rc.SGIN = e.SGIN and e.SSTATUT_MEDIUM = 'V' "
            + "and e.SEMAIL = :email and rc.STYPE_CONTRAT = 'FP' ",
            nativeQuery = true)
    Long countFrequencePlusContractsOwnedByGinsSharingSameValidEmail(@Param("email") String email);

    /**
     * @param email email
     * @param gin   individual
     * @return number of the FP contracts owned by other indivuals sharing same valid email
     */
    @Query(value = "select count(rc.srin) from ROLE_CONTRATS rc left join EMAILS e "
            + "on rc.SGIN = e.SGIN "
            + "where e.SSTATUT_MEDIUM = 'V' "
            + "and e.SEMAIL = :email and rc.STYPE_CONTRAT = 'FP' "
            + "and rc.SGIN <> :gin ",
            nativeQuery = true)
    Long countFrequencePlusContractsOwnedByOtherGinsSharingSameValidEmail(@Param("email") String email, @Param("gin") String gin);


    /**
     * @param gin The gin related to the data we want
     * @return List of role contracts related to our gin
     */
    List<RoleContract> findByIndividuGin(String gin);

    /**
     * @param gin
     * @param etats
     * @return matching role contracts
     */
    List<RoleContract> findRoleContractsByIndividuGinAndEtatIn(String gin, List<String> etats);

	/**
	 * @param cleRole The identifier related to the data we want
     * @return The role contract related to our cleRole
     */
    RoleContract findByCleRole(int cleRole);

    /*
  RULES CustomCAD:
    -- No FB Number present AND FB enrollment date is not null
    -- FB/MA contract is present AND no FB/MA enrollment date
   */
    @Query("SELECT CASE WHEN COUNT(rc) > 0 THEN true ELSE false END FROM RoleContract rc "
            + "where rc.individu.gin = :gin and ( (rc.numeroContrat is null and rc.typeContrat = 'FP' and rc.dateCreation is not null) " +
            " or (rc.numeroContrat is not null and rc.typeContrat in ('FP', 'MA') and rc.dateCreation is null) )")
    boolean checkRoleContratIsNotEligibleToSendToSFMCByGin(@Param("gin") String gin);

	/**
     * @param gin         The gin of the individual related to the business role
     * @param typeContrat The type of the contract we want to fetch
     * @return The role contract with the gin and the type contract we want
     */
    RoleContract findByIndividuGinAndTypeContrat(String gin, String typeContrat);

    /**
     * @param cin
     * @return list of Contracts found
     */
    List<RoleContract> findRoleContractsByNumeroContrat(String cin);
}
