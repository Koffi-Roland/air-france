package com.afklm.rigui.repository;


import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.afklm.rigui.model.individual.requests.ModelMergeStatistiques;
import com.afklm.rigui.repository.custom.MergeStatistiquesRepositoryCustom;
import com.afklm.rigui.entity.individu.Individu;

public interface MergeStatistiquesRepository
		extends JpaRepository<Individu, String>, MergeStatistiquesRepositoryCustom {

	@Query("select new com.afklm.rigui.model.individual.requests.ModelMergeStatistiques(individu.sgin, individu.signatureModification, individu.dateModification, individu.siteModification, individu.ginFusion, individu2.nom, individu2.prenom, individu2.statutIndividu, individu2.civilite) from Individu individu, Individu individu2 where individu.ginFusion = individu2.sgin and individu.dateModification >= current_date - :dayInPast")
	public List<ModelMergeStatistiques> findByGinMergeNotNull(Pageable pageable, @Param("dayInPast") int dayInPast);
}
