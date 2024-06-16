package com.airfrance.repind.dao.firme;

import com.airfrance.repind.entity.firme.Synonyme;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface SynonymeRepository extends JpaRepository<Synonyme, Long> {
	
	/**
	 * Get list of Synonymes from a Gin Personne Morale with Pagination
	 * @param ginPm
	 * @return
	 */
	@Query("select s from Synonyme s where s.personneMorale.gin = :ginPm")
	public List<Synonyme> findByPMGin(@Param("ginPm") String ginPm, Pageable pageable);
	
	/**
	 * Get list of Synonymes from a Gin Personne Morale
	 * @param ginPm
	 * @return
	 */
	@Query("select s from Synonyme s where s.personneMorale.gin = :ginPm")
	public List<Synonyme> findByPMGin(@Param("ginPm") String ginPm);

	/**
	 * Get list of Synonymes from a Gin Personne Morale and a Type
	 * @param pType
	 * @param gin
	 * @return
	 */
	@Query("select s from Synonyme s where s.personneMorale.gin = :ginPm and s.type = :pType and s.statut = 'V' ")
	Set<Synonyme> findValidByTypeAndGin(@Param("pType") String pType, @Param("ginPm") String gin);
}
