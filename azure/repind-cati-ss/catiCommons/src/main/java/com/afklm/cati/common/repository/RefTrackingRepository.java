package com.afklm.cati.common.repository;


import com.afklm.cati.common.entity.TrackingRefPermissions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * <p>Title : RefPermissions.java</p>
 * <p>Copyright : Copyright (c) 2018</p>
 * <p>Company : AIRFRANCE-KLM</p>
 */
@Repository
public interface RefTrackingRepository extends JpaRepository<TrackingRefPermissions, String> {
}
