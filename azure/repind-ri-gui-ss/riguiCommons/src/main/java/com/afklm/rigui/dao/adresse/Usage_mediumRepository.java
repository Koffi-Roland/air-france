package com.afklm.rigui.dao.adresse;

import com.afklm.rigui.entity.adresse.Usage_medium;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Usage_mediumRepository extends JpaRepository<Usage_medium, String> {

	@Query(value = "select usa from Usage_medium usa "
			+ "where usa.sain_adr = :sain")
	public List<Usage_medium> getAllUsageMediumByAddress(@Param("sain") String sain);
	
	@Query("Select usage from Usage_medium usage where usage.sain_adr = :sainAdr")
	List<Usage_medium> findBySainAdr(@Param("sainAdr") String sainAdr);
}
