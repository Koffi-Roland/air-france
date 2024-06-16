package com.airfrance.repind.dao.firme;

import com.airfrance.repind.entity.firme.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceRepository extends JpaRepository<Service, String>, ServiceRepositoryCustom {
		
}
