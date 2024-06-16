package com.afklm.rigui.dao.individu;

import com.afklm.rigui.exception.jraf.JrafDaoException;
import com.afklm.rigui.entity.individu.Individu;
import com.afklm.rigui.entity.individu.IndividuLight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Repository
public interface IndividuRepository extends JpaRepository<Individu, String> {

	@Query(value="select semail from "
			+ "( "
			+ "select semail from sic2.EMAILS "
			+ "where SGIN=:gin AND SSTATUT_MEDIUM='V' ORDER BY ddate_modification DESC "
			+ ") "
			+ "where ROWNUM<=1 ", nativeQuery=true)
	String getLastValidEmail(@Param("gin") String gin);

	@Transactional(readOnly = true)
	Individu findBySgin(String pGin);

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

	List<Individu> findIndividuBySginIn(List<String> gins);

}
