package com.airfrance.repind.dao.handicap;


import com.airfrance.repind.entity.handicap.Handicap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface HandicapRepository extends JpaRepository<Handicap, Long> {

	List<Handicap> findByGin(String gin);
	
	List<Handicap> findByGinAndType(String gin, String type);
	
	List<Handicap> findByGinAndTypeAndCode(String gin, String type, String code);
	
	@Transactional
	@Modifying
	Long deleteByHandicapId(Long id);
}
