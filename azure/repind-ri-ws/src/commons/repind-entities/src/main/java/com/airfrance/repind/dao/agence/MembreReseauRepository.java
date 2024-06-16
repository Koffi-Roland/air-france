package com.airfrance.repind.dao.agence;

import com.airfrance.repind.entity.agence.MembreReseau;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MembreReseauRepository extends JpaRepository<MembreReseau, Integer>, MembreReseauRepositoryCustom {
	
	@Query("SELECT mr FROM MembreReseau mr LEFT JOIN FETCH mr.agence ag LEFT JOIN FETCH mr.reseau re WHERE ag.gin = :gin AND re.code = :code "
			+ "AND (mr.dateFin IS NULL OR (mr.dateDebut <= CURRENT_DATE AND mr.dateFin >= CURRENT_DATE)) ")
	List<MembreReseau> findActiveByPmAndCode(@Param("gin") String gin, @Param("code") String code);
}
