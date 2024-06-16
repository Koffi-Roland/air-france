package com.airfrance.repind.dao.reference;

import com.airfrance.repind.entity.reference.RefComPrefDgt;
import com.airfrance.repind.entity.reference.RefComPrefMl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefComPrefMlRepository extends JpaRepository<RefComPrefMl, Integer> {
	
	Long countByRefComPrefDgt(RefComPrefDgt refComPrefDgt);
	
}
