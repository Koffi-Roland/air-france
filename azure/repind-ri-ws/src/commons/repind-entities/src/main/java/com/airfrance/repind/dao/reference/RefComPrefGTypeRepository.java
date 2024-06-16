package com.airfrance.repind.dao.reference;

import com.airfrance.repind.entity.reference.RefComPrefGType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RefComPrefGTypeRepository extends JpaRepository<RefComPrefGType, String> {

	List<RefComPrefGType> findAllByOrderByCodeGType(Pageable pageable);
	
	List<RefComPrefGType> findAllByOrderByCodeGType();
}
