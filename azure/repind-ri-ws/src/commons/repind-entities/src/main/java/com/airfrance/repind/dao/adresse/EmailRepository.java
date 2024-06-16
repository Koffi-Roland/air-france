package com.airfrance.repind.dao.adresse;

import com.airfrance.ref.exception.jraf.JrafDaoException;
import com.airfrance.repind.entity.adresse.Email;
import com.airfrance.repind.entity.firme.Fonction;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface EmailRepository extends JpaRepository<Email, String>, EmailRepositoryCustom {

	@Query("select ema from Individu ind "
			+ "right outer join ind.email ema "
			+ "where ind.sgin = :gin "
			+ "and (ema.statutMedium = 'V' "
			+ "or ema.statutMedium = 'I')")
	List<Email> findEmail(@Param("gin") String gin);

	@Transactional(readOnly = true)
	@Query("select ema from Email ema where ema.email = :email")
	List<Email> findByEmail(@Param("email") String email);

	@Query("select ema from Email ema where ema.sgin = :gin and ema.codeMedium = :code")
	List<Email> findByGinAndCodeMedium(@Param("gin") String gin, @Param("code") String code);

	List<Email> findBySgin(String gin);

	/**
	 * Find VALID DIRECT emails associated to provided GIN
	 *
	 * @param gin
	 * @return
	 * @throws JrafDaoException
	 */
	@Query("select ema from Individu ind "
			+ "right outer join ind.email ema "
			+ "where ind.sgin = :gin "
			+ "and ema.statutMedium = 'V' "
			+ "and ema.codeMedium = 'D' "
			+ "order by ema.dateModification desc ")
	List<Email> findDirectEmail(@Param("gin") String gin);

	/**
	 * Find ALL email associated to personne morale
	 *
	 * @param gin
	 * @return
	 * @throws JrafDaoException
	 */
	@Query("select email from PersonneMorale pm "
			+ "right outer join pm.emails email "
			+ "where pm.gin = :gin ")
	List<Email> findPMEmail(@Param("gin") String gin);

	/**
	 * Finds all emails associated to the moral person
	 * having The GIN gin. Returned emails are given from the
	 * firstResultIndex and are in maximum maxResults.
	 *
	 * @param gin
	 * @param firstResultIndex
	 * @param endIndex
	 *
	 * @return null if no results found.
	 *
	 * @throws JrafDaoException
	 *             if a technical exception is fired.
	 */
	@Query("select em from Email em "
			+ "where em.personneMorale.gin = :gin ")
	List<Email> findByPMGin(@Param("gin") String gin, Pageable pageable);


	/**
	 * Gives a list of Email that have the Fonction fonction.
	 * The start index of the results is firstIndex, and the
	 * maximum number of results is maxResults.
	 *
	 * @param fonction
	 * @param firstIndex
	 * @param maxResults
	 *
	 * @return
	 *
	 * @throws JrafDaoException
	 *             if a technical exception occurs (Hibernate exception for
	 *             instance)
	 */
	@Query(value = "select email from Email email "
			+ "where email.fonction = :fonction "
			+ "and email.statutMedium <> 'X' "
			+ "and email.statutMedium <> 'H' "
			+ "order by email.dateModification desc")
	List<Email> findByFonction(@Param("fonction") Fonction fonction, Pageable pageable);

	

	@Query(value = "select m.SAIN from sic2.MBR_EMAIL m "
			+ "where m.IKEY_MBR = :key ", nativeQuery = true)
	List<String> getMbrEmailSainByKey(@Param("key") Integer key);

	@Query(value = "select count(e) from EMAILS e, ROLE_CONTRATS rc "
			+ "where e.semail = :email and e.sgin = :gin "
			+ "and e.sstatut_medium = 'V' "
			+ "and e.sgin = rc.sgin and (rc.stype_contrat = 'FP' or rc.stype_contrat = 'MA') "
			+ "and not exists (select * from EMAILS e1 where e1.sgin = :gin and e1.semail = :email) ", nativeQuery = true)
	Long countEmailFbMember(@Param("gin") String gin, @Param("email") String email);

	@Query("select count(1) from Email e "
			+ "where e.sgin = :gin "
			+ "and e.codeMedium = :codeMedium "
			+ "and e.statutMedium = 'V'")
	Long getNumberProOrPersoEmailByGinAndCodeMedium(@Param("gin") String gin, @Param("codeMedium") String codeMedium);

	public List<Email> findBySginAndStatutMediumIn(String gin, List<String> status);

	public Email findBySain(String sain);

	@Transactional
	@Modifying
	@Query("Delete from Email e where e.sgin = :gin and e.email = :email")
	public void deleteByEmail(@Param("gin") String gin, @Param("email") String email);

	@Transactional
	@Modifying
	@Query("Update Email e set e.email = :email where e.sain = :sain")
	public void updateEmailAddress(@Param("sain") String sain, @Param("email") String email);

	@Transactional
	@Modifying
	@Query("Update Email e set e.statutMedium = :status where e.sain = :sain")
	public void updateEmailStatus(@Param("sain") String sain, @Param("status") String status);

	@Query("SELECT em FROM Email em "
			+ "WHERE em.personneMorale.gin = :gin AND em.statutMedium = 'V' ")
	List<Email> findActiveByPm(@Param("gin") String gin);

	@Query("SELECT em FROM Email em WHERE em.personneMorale.gin = :gin AND em.codeMedium in :codeList")
    public List<Email> findByGinPmCodeMedium(@Param("gin") String gin, @Param("codeList") List<String> codeList);
}
