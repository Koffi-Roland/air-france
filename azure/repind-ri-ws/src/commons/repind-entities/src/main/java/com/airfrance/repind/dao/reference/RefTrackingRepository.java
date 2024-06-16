package com.airfrance.repind.dao.reference;

import com.airfrance.repind.entity.tracking.TrackingRefPermissions;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * <p>Title : RefPermissions.java</p>
 * <p>Copyright : Copyright (c) 2018</p>
 * <p>Company : AIRFRANCE-KLM</p>
 */
public interface RefTrackingRepository extends JpaRepository<TrackingRefPermissions, String> {
}
