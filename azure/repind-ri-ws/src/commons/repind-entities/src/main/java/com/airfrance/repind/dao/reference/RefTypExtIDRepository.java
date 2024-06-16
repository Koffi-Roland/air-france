package com.airfrance.repind.dao.reference;

import com.airfrance.repind.entity.reference.RefTypExtID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RefTypExtIDRepository extends JpaRepository<RefTypExtID, String> {
	
	List<RefTypExtID> findByOption(String option);
	
}
