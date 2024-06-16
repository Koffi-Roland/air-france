package com.airfrance.repind.dao.role;

import com.airfrance.ref.exception.jraf.JrafDaoException;
import com.airfrance.repind.entity.role.RoleContrats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface RoleContratsRepository extends JpaRepository<RoleContrats, String>, RoleContratsRepositoryCustom {

    @Query(value = "select SIC2.ISEQ_ROLE_CONTRATS.NEXTVAL from dual", nativeQuery = true)
    Long getSequence();

    void deleteByGin(String gin);

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
     * Find role contrats associated to provided GIN
     *
     * @param gin
     * @return
     * @throws JrafDaoException
     */
    @Query("select rol from RoleContrats rol "
            + "where rol.numeroContrat = :contractNumber "
            + "and rol.typeContrat = 'FP' "
            + "order by rol.dateFinValidite desc")
    public RoleContrats findRoleContratsFP(@Param("contractNumber") String contractNumber);

    /**
     * Recherche de gin
     *
     * @param pType
     * @param pNumero
     * @return gin
     * @throws JrafDaoException
     */
    @Query("select rc.gin from RoleContrats rc "
            + "where rc.typeContrat = :type and rc.numeroContrat = :contractNumber ")
    List<String> findGinsByTypeAndNumero(@Param("type") String type, @Param("contractNumber") String contractNumber) throws JrafDaoException;

    /**
     * Liste de Map contenant les noms de propriété et leurs valeurs respectives
     *
     * @param pNumero
     * @return List of Map if found, null otherwise
     */
    @Query("select new Map( "
            + "roleContrats.gin as gin, "
            + "roleContrats.typeContrat as typeContrat, "
            + "roleContrats.etat as etat, "
            + "roleContrats.dateFinValidite as dateFinValidite "
            + ") from RoleContrats roleContrats "
            + "where roleContrats.numeroContrat = :contractNumber ")
    List<Map<String, ?>> findSimplePropertiesByNumero(@Param("contractNumber") String contractNumber);

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
     * Liste de Map contenant les noms de propriété et leurs valeurs respectives
     *
     * @param pGin
     * @return List of Map if found, null otherwise
     */
    @Query("select new Map( "
            + "roleContrats.numeroContrat as numeroContrat, "
            + "roleContrats.typeContrat as typeContrat, "
            + "roleContrats.etat as etat, "
            + "roleContrats.dateFinValidite as dateFinValidite "
            + ") from RoleContrats roleContrats "
            + "where roleContrats.gin = :gin ")
    List<Map<String, ?>> findSimplePropertiesByGin(@Param("gin") String pGin);

    /**
     * Find all role contracts by the contract number
     *
     * @param numeroContract
     * @return
     * @throws JrafDaoException
     */
    @Query("select rol from RoleContrats rol "
            + "where rol.numeroContrat = :contractNumber "
            + "order by rol.dateFinValidite desc")
    RoleContrats findRoleContratsByNumContract(@Param("contractNumber") String contractNumber);


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

    /**
     * Find all valid and active Contract of an individual expect MyA
     *
     * @param gin
     * @return list of valid contract
     * @throws JrafDaoException
     */
    @Query("select rol from RoleContrats rol "
            + "where rol.gin = :gin "
            + "and rol.etat in ('C', 'P', 'U', 'V') "
            + "and rol.typeContrat not in ('MA') "
            + "and (rol.dateFinValidite is null or rol.dateFinValidite > to_date(sysdate)) ")
    List<RoleContrats> findValidRoleContractNotMyA(@Param("gin") String gin);

    /**
     * Find all valid and active Contract of an individual expect MyA
     *
     * @param gin
     * @return list of valid contract
     * @throws JrafDaoException
     */
    @Query("select count(1) from RoleContrats rol "
            + "where rol.gin = :gin "
            + "and rol.etat not in ('I', 'A') "
            + "and rol.typeContrat = 'FP' ")
    Long countFPContractsByGin(@Param("gin") String gin);

    @Query("select count(1) from RoleContrats rol "
            + "where rol.gin = :gin "
            + "and rol.etat not in ('I', 'A') "
            + "and rol.typeContrat <> 'FP' ")
    Long countOtherContractsByGin(@Param("gin") String gin);

    @Query("select rol.numeroContrat from RoleContrats rol "
            + "where rol.gin = :gin "
            + "and rol.etat != 'X' "
            + "and rol.typeContrat = 'FP' "
            + "and (rol.dateFinValidite is null or rol.dateFinValidite > to_date(sysdate)) "
            + "order by rol.dateDebutValidite desc")
    List<String> getFPNumberByGin(@Param("gin") String gin);

    @Transactional
    Long deleteByGinAndTypeContrat(String gin, String type);

    public List<RoleContrats> findByGin(String gin);

    @Query("Select rc from RoleContrats rc where rc.gin = :gin and rc.etat IN :etats")
    public List<RoleContrats> findByGinAndEtat(@Param("gin") String gin, @Param("etats") List<String> etats);

    public RoleContrats findByNumeroContrat(String contractNumber);

    @Transactional
    public RoleContrats findByNumeroContratAndTypeContrat(String contractNumber, String contractType);

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

    RoleContrats findByGinAndNumeroContrat( String gin, String numeroContract);


    /**
     * Find all valid and active Contract of an individual expect MyA
     *
     * @param gin, numContract
     * @return number of valid contract
     * @throws JrafDaoException
     */
    @Query("select count(1) from RoleContrats rol "
            + "where rol.gin = :gin "
            + "and rol.numeroContrat = :numContrat "
            + "and rol.typeContrat = 'FP' ")
    Long countFPContractsByGinAndNumContract(@Param("gin") String gin, @Param("numContrat") String numContrat);

    /**
     * Find valid MyA Contract of a given GIN
     *
     * @param gin
     * @return list of valid MYA contract
     */
    @Query("select rol from RoleContrats rol "
            + "where rol.gin = :gin "
            + "and rol.etat <> 'A' "
            + "and rol.typeContrat = 'MA' "
            + "order by rol.dateDebutValidite desc")
    List<RoleContrats> findValidRoleContractMya(@Param(value ="gin") String gin);

}
