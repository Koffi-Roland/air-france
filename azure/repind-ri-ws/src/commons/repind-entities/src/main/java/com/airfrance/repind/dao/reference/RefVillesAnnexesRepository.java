package com.airfrance.repind.dao.reference;

import com.airfrance.repind.entity.reference.RefVillesAnnexes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RefVillesAnnexesRepository extends JpaRepository<RefVillesAnnexes, String> {
	
	@Query("SELECT va FROM RefVillesAnnexes va LEFT JOIN FETCH va.pays c WHERE (va.libelle =:name OR va.libelleEn =:name) AND c.codePays =:countryCode ")
	List<RefVillesAnnexes> findByCityCountry(@Param("name") String name, @Param("countryCode") String countryCode);

	@Query("SELECT va FROM RefVillesAnnexes va LEFT JOIN FETCH va.pays c WHERE (va.libelle like :name% OR va.libelleEn like :name%) AND c.codePays =:countryCode ")
	List<RefVillesAnnexes> findSimilarByCityCountry(@Param("name") String name, @Param("countryCode") String countryCode);
}
