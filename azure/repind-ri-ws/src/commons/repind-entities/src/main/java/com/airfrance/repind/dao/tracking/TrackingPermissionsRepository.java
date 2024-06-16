package com.airfrance.repind.dao.tracking;

import com.airfrance.repind.entity.tracking.TrackingPermissions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrackingPermissionsRepository extends JpaRepository<TrackingPermissions, Integer> {
	
	List<TrackingPermissions> findBySgin_sgin(String gin);
	
}
