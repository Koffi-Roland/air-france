package com.airfrance.repind.dao.consent;

import com.airfrance.repind.entity.consent.ConsentData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsentDataRepository extends JpaRepository<ConsentData, Long> {
	
		
}
