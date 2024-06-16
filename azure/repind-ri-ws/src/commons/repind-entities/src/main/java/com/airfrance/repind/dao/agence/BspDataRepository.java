package com.airfrance.repind.dao.agence;

import com.airfrance.repind.entity.agence.BspData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BspDataRepository extends JpaRepository<BspData, Integer> {
	
	List<BspData> findByGin(String gin);
}
