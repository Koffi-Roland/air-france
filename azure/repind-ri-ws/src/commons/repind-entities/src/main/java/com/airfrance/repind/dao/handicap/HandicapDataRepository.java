package com.airfrance.repind.dao.handicap;


import com.airfrance.repind.entity.handicap.HandicapData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface HandicapDataRepository extends JpaRepository<HandicapData, Long> {

	List<HandicapData> findByHandicapHandicapId(Long id);
		
	@Transactional
	@Modifying
	Long deleteByHandicapHandicapIdAndKey(Long handicapId, String key);
}
