package com.airfrance.repind.dao.reference;

import com.airfrance.repind.entity.reference.RefComPrefMedia;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RefComPrefMediaRepository extends JpaRepository<RefComPrefMedia, String> {

	List<RefComPrefMedia> findAllByOrderByCodeMedia(Pageable pageable);
	
	List<RefComPrefMedia> findAllByOrderByCodeMedia();
}
