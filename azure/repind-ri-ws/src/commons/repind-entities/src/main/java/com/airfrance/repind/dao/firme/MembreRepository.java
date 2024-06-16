package com.airfrance.repind.dao.firme;

import com.airfrance.repind.entity.firme.Fonction;
import com.airfrance.repind.entity.firme.Membre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MembreRepository extends JpaRepository<Membre, Integer>, MembreRepositoryCustom {
	
	/**
	 * Find Membre from Gin Personne Morale and Gin Individual
	 * @param pGinPm
	 * @param pGinInd
	 * @return
	 */
	@Query("select m from Membre m where m.personneMorale.gin = :ginPm and m.individu.sgin = :ginInd")
	public List<Membre> findByGinPmAndGinInd(@Param("ginPm") String pGinPm, @Param("ginInd") String pGinInd);
	
	/**
	 * Find Membre from Gin Personne Morale and Gin Individual with Valid Link
	 * @param pLegalPersonGin
	 * @param pIndividualGin
	 * @return
	 */
	@Query("select m from Membre m where m.personneMorale.gin = :ginPm and m.individu.sgin = :ginInd and (m.dateFinValidite is null or m.dateFinValidite > sysdate)")
	public Membre findByGinsValidLink(@Param("ginPm") String pLegalPersonGin, @Param("ginInd") String pIndividualGin);
	
	/**
	 * Find Membre from Personne Morale
	 * @param pGinPm
	 * @return
	 */
	@Query("select m from Membre m where m.personneMorale.gin = :ginPm")
	public List<Membre> findByGinPm(@Param("ginPm") String pGinPm);
	
	/**
	 * Find list of Foctions from Membre and Job Title
	 * @param key
	 * @param jobTitle
	 * @return
	 */
	@Query("select f from Membre m inner join m.fonctions f where m.key = :key and f.fonction = :jobTitle and (f.dateFinValidite is null or f.dateFinValidite > sysdate)")
	public List<Fonction> findByValidJobTitle(@Param("key") Integer key, @Param("jobTitle") String jobTitle);
	
	/**
	 * Find specific Member light
	 * @param key
	 * @return
	 */
	@Query("select distinct m from Membre m left join fetch m.individu ind left join fetch m.personneMorale pm where m.key = :key")
	public Membre findSpecificMemberLight(@Param("key") Integer key);
	
	/**
	 * Find list of valid Membres from Individual Gin
	 * @param ginInd
	 * @return
	 */
	@Query("select mbr from Membre mbr where mbr.individu.sgin = :ginInd and (mbr.dateFinValidite is null or mbr.dateFinValidite >= to_date(sysdate))")
	public List<Membre> findValidByIndividualGin(@Param("ginInd") String ginInd);
	
	/**
	 * Find list of Id Membre from Individual Gin
	 * @param ginInd
	 * @return
	 */
	@Query("select mbr.key from Membre mbr where mbr.individu.sgin = :ginInd")
	public List<Integer> getKeyByGin(@Param("ginInd") String ginInd);
}
