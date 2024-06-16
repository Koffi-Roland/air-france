package com.airfrance.repind.dao.reference;

import com.airfrance.repind.entity.reference.RefHandicapType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RefHandicapTypeRepository extends JpaRepository<RefHandicapType, String> {

	List<RefHandicapType> findAllByOrderByCode(Pageable pageable);
	
}
