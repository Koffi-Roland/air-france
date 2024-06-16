package com.afklm.repind.msv.provide.last.activity.repository;

import com.afklm.repind.msv.provide.last.activity.entity.LastActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Last activity repository
 */
@Repository
public interface LastActivityRepository extends JpaRepository<LastActivity, Long> {

    /**
     * Find the last activity according to the given gin
     *
     * @param gin Individual number
     * @return Optional last activity (LastActivity)
     */
    Optional<LastActivity> findByGin(String gin);

}
