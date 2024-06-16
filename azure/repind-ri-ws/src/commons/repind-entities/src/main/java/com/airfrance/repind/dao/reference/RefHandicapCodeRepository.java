package com.airfrance.repind.dao.reference;

import com.airfrance.repind.entity.reference.RefHandicapCode;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RefHandicapCodeRepository extends JpaRepository<RefHandicapCode, String> {

	List<RefHandicapCode> findAllByOrderByCode(Pageable pageable);
	
}
