package com.airfrance.repind.dao.reference;

import com.airfrance.repind.entity.reference.RefHandicapDataKey;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RefHandicapDataKeyRepository extends JpaRepository<RefHandicapDataKey, String> {

	List<RefHandicapDataKey> findAllByOrderByCode(Pageable pageable);
	
}
