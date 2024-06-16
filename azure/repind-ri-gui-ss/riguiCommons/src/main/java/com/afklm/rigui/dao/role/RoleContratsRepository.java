package com.afklm.rigui.dao.role;

import com.afklm.rigui.exception.jraf.JrafDaoException;
import com.afklm.rigui.entity.role.RoleContrats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface RoleContratsRepository extends JpaRepository<RoleContrats, String> {

    @Query(value = "select SIC2.ISEQ_ROLE_CONTRATS.NEXTVAL from dual", nativeQuery = true)
    Long getSequence();

    /**
     * Find role contrats associated to provided GIN
     *
     * @param gin
     * @return
     */
    @Query("select rol from RoleContrats rol "
            + "where rol.gin = :gin "
            + "and (rol.etat in ('C', 'P' ,'U', 'S') "
            + "or (rol.etat = 'A' and rol.typeContrat = 'MA')) "
            + "order by rol.dateFinValidite desc")
    public List<RoleContrats> findRoleContrats(@Param("gin") String gin);


    @Query("select r from RoleContrats r where r.srin in (select rol.srin from RoleContrats rol "
            + "where rol.gin = :gin "
            + "and rol.typeContrat = :type ) ")
    List<RoleContrats> findRoleContrats(@Param("gin") String gin, @Param("type") String type);

    /**
     * @param pEmail email
     * @param pGin   individual
     * @return number of the FP contracts owned by other indivuals sharing same valid email
     */
    @Query("select count(rc.srin) from Email e, RoleContrats rc "
            + "where rc.gin = e.sgin and e.statutMedium = 'V' "
            + "and e.email = :email and rc.typeContrat = 'FP' "
            + "and rc.gin <> :gin ")
    Long countFrequencePlusContractsOwnedByOtherGinsSharingSameValidEmail(@Param("email") String email, @Param("gin") String gin);

    /**
     * @param pEmail
     * @return number of the FP contracts owned by indivuals sharing same valid email
     */
    @Query("select count(rc.srin) from Email e, RoleContrats rc "
            + "where rc.gin = e.sgin and e.statutMedium = 'V' "
            + "and e.email = :email and rc.typeContrat = 'FP' ")
    Long countFrequencePlusContractsOwnedByGinsSharingSameValidEmail(@Param("email") String email);


    /**
     * Find list of role contracts by the contract number
     *
     * @param numeroContract
     * @return
     * @throws JrafDaoException
     */
    @Query("select rol from RoleContrats rol "
            + "where rol.numeroContrat = :contractNumber")
    List<RoleContrats> findListRoleContratsByNumContract(@Param("contractNumber") String contractNumber);

    public List<RoleContrats> findByGin(String gin);

    @Query("Select rc from RoleContrats rc where rc.gin = :gin and rc.etat IN :etats")
    public List<RoleContrats> findByGinAndEtat(@Param("gin") String gin, @Param("etats") List<String> etats);

    public RoleContrats findByNumeroContrat(String contractNumber);

    /**
     * Reactivate a Role Contract for a customer, that mean change status from 'X' to 'C' and update auxilaries data
     * @param numeroContrat : The contract number to reactivate
     * @param dateModification : Modification date
     * @param signatureModification : Modification signature
     * @param siteModification : Modification site
     */
    @Transactional
    @Modifying
    @Query("Update RoleContrats rc set rc.etat = 'C', rc.signatureModification = :signatureModification, rc.dateModification = :dateModification, rc.siteModification = :siteModification where rc.numeroContrat = :numeroContrat")
    void reactivateContractByNumeroContract(@Param(value = "numeroContrat") String numeroContrat, @Param(value = "dateModification") Date dateModification, @Param(value = "signatureModification") String signatureModification, @Param(value = "siteModification") String siteModification);

    @Query("select CASE WHEN COUNT(rol) > 0 THEN true ELSE false END from RoleContrats rol "
            + "where rol.gin = :gin "
            + "and rol.typeContrat = :typeContrat ")
    boolean isFlyingBlueOrMyAccountByGin(@Param(value ="gin") String gin, @Param(value ="typeContrat") String typeContrat);

}
