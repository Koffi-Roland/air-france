package com.airfrance.repind.dao.reference;

import com.airfrance.repind.entity.reference.RefLanguage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefLanguageRepository extends JpaRepository<RefLanguage, String>, RefLanguageRepositoryCustom {
	
	RefLanguage findByLanguageCode(String code);
	
}
