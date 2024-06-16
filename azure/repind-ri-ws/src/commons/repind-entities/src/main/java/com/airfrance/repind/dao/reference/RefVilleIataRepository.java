package com.airfrance.repind.dao.reference;

import com.airfrance.repind.entity.reference.RefVilleIata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RefVilleIataRepository extends JpaRepository<RefVilleIata, String> {

	@Query("SELECT vi FROM RefVilleIata vi LEFT JOIN FETCH vi.pays c WHERE (vi.libelle =:name OR vi.libelleEn =:name) AND c.codePays =:countryCode ")
	List<RefVilleIata> findByCityCountry(@Param("name") String name, @Param("countryCode") String countryCode);

	@Query("SELECT vi FROM RefVilleIata vi LEFT JOIN FETCH vi.pays c WHERE (vi.libelle like :name% OR vi.libelleEn like :name%) AND c.codePays =:countryCode ")
	List<RefVilleIata> findSimilarByCityCountry(@Param("name") String name, @Param("countryCode") String countryCode);

	@Query("SELECT vi.scodeVille, vi.libelle, vi.libelleEn FROM RefVilleIata vi")
	List<Object[]> findAllRefVilleIata();

	@Query("SELECT vi FROM RefVilleIata vi LEFT JOIN FETCH vi.pays c "
			+ "WHERE (:cityCode IS NULL OR UPPER(vi.scodeVille) = UPPER(:cityCode)) "
			+ "AND (:cityName IS NULL OR UPPER(vi.libelle) = UPPER(:cityName) OR UPPER(vi.libelleEn) = UPPER(:cityName)) "
			+ "AND (:countryCode IS NULL OR UPPER(c.codePays) = UPPER(:countryCode)) "
			+ "AND (:stateCode IS NULL OR UPPER(vi.codeProvEtat) = UPPER(:stateCode)) ")
	List<RefVilleIata> searchCityMulticriteria(@Param("cityCode") String cityCode, @Param("cityName") String cityName,
			@Param("countryCode") String countryCode, @Param("stateCode") String stateCode);
}
