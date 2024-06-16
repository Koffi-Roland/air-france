package com.airfrance.repind.dao.consent;

import com.airfrance.repind.entity.consent.Consent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConsentRepository extends JpaRepository<Consent, Long> {

	List<Consent> findByGin(String gin);
		
}
