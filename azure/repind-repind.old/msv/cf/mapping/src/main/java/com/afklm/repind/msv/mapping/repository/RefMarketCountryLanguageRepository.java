package com.afklm.repind.msv.mapping.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.afklm.repind.msv.mapping.entity.RefMarketCountryLanguage;
import com.afklm.repind.msv.mapping.entity.RefMarketCountryLanguageId;


@Repository
public interface RefMarketCountryLanguageRepository
		extends JpaRepository<RefMarketCountryLanguage, RefMarketCountryLanguageId> {

	
	@Query("Select ref from RefMarketCountryLanguage ref where ref.refMarketCountryLanguageId.scodeContext = :context")
	List<RefMarketCountryLanguage> findByContext(@Param("context") String context);

	@Query("Select ref from RefMarketCountryLanguage ref order by ref.refMarketCountryLanguageId.scodeContext")
	List<RefMarketCountryLanguage> findAllGroupByContext();

	@Query("Select ref from RefMarketCountryLanguage ref where ref.refMarketCountryLanguageId.scodeContext = :context and ref.refMarketCountryLanguageId.scodeLanguageNoISO = :noneIsoLanguage and ref.refMarketCountryLanguageId.scodeMarket = :market")
	RefMarketCountryLanguage findByContextLanguageNoneIsoAndMarket(@Param("context") String context,
			@Param("noneIsoLanguage") String noneIsoLanguage, @Param("market") String market);

	@Query("Select ref from RefMarketCountryLanguage ref where ref.refMarketCountryLanguageId.scodeContext = :context and ref.refMarketCountryLanguageId.scodeLanguageISO = :isoLanguage and ref.refMarketCountryLanguageId.scodeMarket = :market")
	RefMarketCountryLanguage findByContextLanguageIsoAndMarket(@Param("context") String context,
			@Param("isoLanguage") String isoLanguage, @Param("market") String market);

}