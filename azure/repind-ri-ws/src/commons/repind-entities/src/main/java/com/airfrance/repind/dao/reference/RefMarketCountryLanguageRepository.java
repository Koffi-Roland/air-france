package com.airfrance.repind.dao.reference;

import com.airfrance.repind.entity.reference.RefMarketCountryLanguage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RefMarketCountryLanguageRepository extends JpaRepository<RefMarketCountryLanguage, String>, RefMarketCountryLanguageRepositoryCustom {
	
}
