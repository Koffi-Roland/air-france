package com.airfrance.repind.dao.individu;

import com.airfrance.ref.exception.jraf.JrafDaoException;
import com.airfrance.repind.entity.individu.Individu;
import com.airfrance.repind.entity.individu.IndividuLight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Repository
public interface IndividuRepository extends JpaRepository<Individu, String>, IndividuRepositoryCustom {

	@Query(value="select count(1) from sic2.ACCOUNT_DATA acc, sic2.ROLE_CONTRATS rol "
			+ "where acc.sgin=:gin "
			+ "and acc.FB_IDENTIFIER is not null "
			+ "and acc.SGIN=rol.SGIN "
			+ "and rol.STYPE_CONTRAT='FP' ", nativeQuery=true)
	Long isFlyingBlue(@Param("gin") String gin);

	@Query(value="select count(1) from sic2.ACCOUNT_DATA "
			+ "where sgin=:gin "
			+ "and account_identifier is not null ", nativeQuery=true)
	Long isMyAccount(@Param("gin") String gin);

	@Query(value="select count(1) from sic2.ACCOUNT_DATA acc, sic2.ROLE_CONTRATS rol "
			+ "where acc.SGIN=:gin "
			+ "and acc.ACCOUNT_IDENTIFIER is not null "
			+ "and acc.EMAIL_IDENTIFIER is not null "
			+ "and acc.FB_IDENTIFIER is null "
			+ "and acc.SGIN=rol.SGIN "
			+ "and rol.STYPE_CONTRAT='MA' ", nativeQuery=true)
	Long isPureMyAccount(@Param("gin") String gin);
	
	@Query(value="select semail from "
			+ "( "
			+ "select semail from sic2.EMAILS "
			+ "where SGIN=:gin AND SSTATUT_MEDIUM='V' ORDER BY ddate_modification DESC "
			+ ") "
			+ "where ROWNUM<=1 ", nativeQuery=true)
	String getLastValidEmail(@Param("gin") String gin);

	@Transactional(readOnly = true)
	Individu findBySgin(String pGin);

	// REPIND-1808 : Create a Caller for CCP
	// Catch the next sequence number for a traveler - GIN start with 94xxxxxxxxxx
	@Query(value="SELECT ISEQ_INDIVIDUS_CALLER.NEXTVAL AS SC FROM DUAL ", nativeQuery=true)
	String getCallerNextValue();
	
	// Catch the next sequence number for a traveler - GIN start with 91xxxxxxxxxx
	@Query(value="SELECT ISEQ_INDIVIDUS_TRAVELERS.NEXTVAL AS ST FROM DUAL ", nativeQuery=true)
	String getTravelerNextValue();

	// Catch the next sequence number for a kidSolo - GIN start with 93xxxxxxxxxx
	@Query(value="SELECT ISEQ_INDIVIDUS_KIDSOLO.NEXTVAL AS ST FROM DUAL ", nativeQuery=true)
	String getKidSoloNextValue();

	// Catch the next sequence number for a Prospect - GIN start with 90xxxxxxxxxx
	@Query(value="SELECT ISEQ_INDIVIDUS_PROSPECT.NEXTVAL AS SP FROM DUAL ", nativeQuery=true)
	String getProspectNextValue();

	// Catch the next sequence number for an External Identifier - GIN start with 92xxxxxxxxxx
	@Query(value="SELECT ISEQ_INDIVIDUS_EXTERNAL.NEXTVAL AS ST FROM DUAL ", nativeQuery=true)
	String getExternalIdentifierNextValue();
	
	// Catch the next sequence number for an Individu
	@Query(value="SELECT ISEQ_INDIVIDUS.NEXTVAL AS ST FROM DUAL ", nativeQuery=true)
	String getIdentifierNextValue();
	
	// Catch the next sequence number for an KLM
	@Query(value="SELECT ISEQ_GIN_KLM.NEXTVAL AS ST FROM DUAL ", nativeQuery=true)
	String getKLMIdentifierNextValue();
	
	// Catch the next sequence number for an WP
	@Query(value="SELECT ISEQ_GIN_WP.NEXTVAL AS ST FROM DUAL ", nativeQuery=true)
	String getWPIdentifierNextValue();
	
	// Catch the next sequence number for an AE
	@Query(value="SELECT ISEQ_GIN_AE.NEXTVAL AS ST FROM DUAL ", nativeQuery=true)
	String getAEIdentifierNextValue();
	
	// Catch the next sequence number for an BTA
	@Query(value="SELECT ISEQ_GIN_CARTE_LOGEE.NEXTVAL AS ST FROM DUAL ", nativeQuery=true)
	String getBTAIdentifierNextValue();
	
	//ISEQ_GIN_CARTE_LOGEE
	

	
	
	
	/**
	 * Finds the individual having the GIN gin if it exists in the database.
	 * 
	 * @param gin
	 * 
	 * @return
	 * 
	 * @throws JrafDaoException
	 */
	@Query("Select ind from IndividuLight ind "
			+ "where ind.sgin= :gin ")
	IndividuLight findIndividualLightByGin(@Param("gin") String gin);
	
	/**
	 * Finds the individual having the GIN gin if it exists in the database.
	 * 
	 * @param matricule
	 * @return List<GIN>
	 */
	@Query(value="SELECT I.SGIN FROM INDIVIDUS_ALL I, PROFIL_MERE M, PROFIL_AF P "
			+ "WHERE LTRIM(P.SMATRICULE,'0') = :matricule "
			+ "AND M.ICLE_PRF = P.ICLE_PRF "
			+ "AND I.SGIN = M.SGIN_IND "
			+ "AND I.SGIN_FUSION IS NULL", nativeQuery=true)
	List<String> getIndividuListByMatricule(@Param("matricule") String matricule);
	
	/**
	 * Finds the individual having the GIN gin if it exists in the database.
	 * 
	 * @param matricule
	 * @return List<GIN>
	 */
	@Query(value="SELECT I.SGIN FROM INDIVIDUS I, BUSINESS_ROLE B, ROLE_GP R "
			+ "WHERE LTRIM(R.SMATRICULE,'0') = :matricule "
			+ "AND B.ICLE_ROLE = R.ICLE_ROLE "
			+ "AND I.SGIN = B.SGIN_IND "
			+ "AND I.SGIN_FUSION IS NULL", nativeQuery=true)
	List<String> getIndividuListByMatriculeWithoutIdentifierOrder(@Param("matricule") String matricule);


	@Query(value="SELECT I.SGIN FROM INDIVIDUS I, BUSINESS_ROLE B, ROLE_GP R "
			+ "WHERE LTRIM(R.SMATRICULE,'0') = :matricule "
			+ "AND B.ICLE_ROLE = R.ICLE_ROLE "
			+ "AND I.SGIN = B.SGIN_IND "
			+ "AND I.SGIN_FUSION IS NULL "
			+ "AND R.SORDRE_IDENTIFIANT= :identifierOrder", nativeQuery=true)
	List<String> getIndividuListByMatriculeAndIdentifierOrder(@Param("matricule") String matricule, @Param("identifierOrder") String identifierOrder);

	/**
	 * Finds the individual having the GIN gin if it exists in the database.
	 *
	 * @param lastName
	 * @param firstName
	 * @param birthDate
	 * @return List<GIN>
	 */
	@Query(value="SELECT I.SGIN FROM INDIVIDUS_ALL I WHERE I.SGIN_FUSION IS NULL AND I.SNOM=:lastName AND I.SPRENOM=:firstName AND TO_CHAR(I.DDATE_NAISSANCE,'DD/MM/YYYY')=:birthDate", nativeQuery=true)
	List<String> getIndividuListByNameAndAmex(@Param("lastName") String lastName,
			@Param("firstName") String firstName, @Param("birthDate") String birthDate);

	@Query(value="SELECT I.SGIN FROM INDIVIDUS_ALL I"
			+ "WHERE SGIN_FUSION IS NULL "
			+ "AND I.SNOM = :lastName "
			+ "AND I.SPRENOM = :firstName", nativeQuery=true)
	List<String> getIndividuListByNameWithoutAmex(@Param("lastName") String lastName,
			@Param("firstName") String firstName);

	/**
	 * Finds the list of same individual matricule  
	 * 
	 * @param matricule
	 * @return List<GIN>
	 */
	@Query(value="SELECT I.SGIN  FROM INDIVIDUS I, BUSINESS_ROLE BR, ROLE_GP RG "
			+ "WHERE RG.SMATRICULE = :matricule "
			+ "AND RG.ICLE_ROLE = BR.ICLE_ROLE "
			+ "AND I.SGIN = BR.SGIN_IND "
			+ "AND I.SGIN_FUSION IS NULL", nativeQuery=true)
	List<String> getFamilyByMatricule(@Param("matricule") String matricule);

	@Query("select count(1) from IndividuLight i "
			+ "where i.sgin = :gin and "
			+ "( exists ( select numeroContrat from RoleContrats r where r.numeroContrat = i.sgin ) "
			+ "or exists ( select numeroContrat from BusinessRole b where b.numeroContrat = i.sgin ) )")
	Long isContractAndIndividualHaveSameNumber(@Param("gin") String gin);

	@Query("select i from IndividuLight i "
			+ "where i.sgin = :gin "
			+ "and i.type not in ('F', 'H') ")
	IndividuLight getIndividualOrProspectByGinExceptForgotten(@Param("gin") String gin);

	@Modifying
	@Query(value="UPDATE INDIVIDUS_ALL I SET I.STYPE=:type, I.SSITE_MODIFICATION=:site, I.SSIGNATURE_MODIFICATION=:signature WHERE I.SGIN=:gin", nativeQuery=true)
	void updateTypeByGin(@Param("gin") String gin, @Param("type") String type, @Param("site") String site, @Param("signature") String signature);

	@Modifying
	@Query(value = "UPDATE INDIVIDUS_ALL I SET I.STYPE=:type WHERE I.SGIN=:gin", nativeQuery = true)
	void updateTypeByGin(@Param("gin") String gin, @Param("type") String type);

	@Query(value = "SELECT I.* FROM INDIVIDUS_ALL I"
			+ " WHERE I.SSTATUT_INDIVIDU in ('P', 'V')"
			+ " AND I.SNOM = :nom"
			+ " AND I.SPRENOM = :prenom"
			+ " AND I.DDATE_NAISSANCE = TO_DATE(:dateNaissance,'DD/MM/YYYY')", nativeQuery = true)
	List<Individu> findByNomAndPrenomAndDateNaissance(@Param("nom") String nom, @Param("prenom") String prenom, @Param("dateNaissance") String dateNaissance);

	@Query(value = "SELECT I.* FROM INDIVIDUS_ALL I"
			+ " WHERE I.SSTATUT_INDIVIDU in ('P', 'V')"
			+ " AND I.SNOM = :nom"
			+ " AND I.SPRENOM = :prenom", nativeQuery = true)
	List<Individu> findByNomAndPrenom(@Param("nom") String nom, @Param("prenom") String prenom);

	@Transactional(readOnly = true)
	@Query(value = "SELECT I.* FROM INDIVIDUS_ALL I, EMAILS E"
			+ " WHERE I.SSTATUT_INDIVIDU in ('P', 'V')"
			+ " AND I.SNOM = :nom"
			+ " AND I.SPRENOM = :prenom"
			+ " AND E.SGIN = I.SGIN"
			+ " AND E.SEMAIL = :email", nativeQuery = true)
	List<Individu> findByNomAndPrenomAndEmail(@Param("nom") String nom, @Param("prenom") String prenom, @Param("email") String email);

	@Query(value = "SELECT I.* FROM INDIVIDUS_ALL I, BUSINESS_ROLE BR INNER JOIN ROLE_GP GP"
			+ " ON GP.ICLE_ROLE = BR.ICLE_ROLE"
			+ " WHERE I.SSTATUT_INDIVIDU in ('P', 'V')"
			+ " AND I.SNOM = :nom"
			+ " AND I.SPRENOM = :prenom"
			+ " AND I.DDATE_NAISSANCE = TO_DATE(:birthdate,'DD/MM/YYYY')"
			+ " AND GP.SMATRICULE = :matricule"
			+ " AND BR.SGIN_IND = I.SGIN", nativeQuery = true)
	List<Individu> findByNomAndPrenomAndDateNaissanceAndMatricule(@Param("nom") String nom, @Param("prenom") String prenom, @Param("birthdate") String dateNaissance,
			@Param("matricule") String matricule);

	/**
	 * Unmerge individual by changing status to 'V', deleted the merge date and
	 * the SGIN merge Also change signature, date and site modification
	 * 
	 * @param sain
	 * @param noAndStreet
	 */
	@Transactional
	@Modifying
	@Query("Update Individu i set i.ginFusion = null, i.dateFusion = null, statutIndividu = 'V', i.dateModification = :dateModification, i.signatureModification = :signatureModification, i.siteModification = :siteModification where i.sgin = :sgin")
	public void unmergeIndividualByGin(@Param("sgin") String sgin, @Param("dateModification") Date dateModification,
			@Param("signatureModification") String signatureModification,
			@Param("siteModification") String siteModification);

	@Query("select distinct i from Individu i " +
			"inner join i.email e " +
			"where e.email = :email " +
			"and e.statutMedium = 'V' " +
			"and i.statutIndividu in ('V', 'T', 'P')")
	List<Individu> findActiveIndividualByEmail(@Param("email") String email);

	/**
	 * Retrieve Individual Type in DB
	 * @param gin
	 * @return type of Individual (I, W, T, K, etc...)
	 */
	@Query("select i.type from Individu i where i.sgin = :gin")
    String findTypeByGin(@Param("gin") String gin);
}
