package com.afklm.rigui.dao.lastactivity;


import com.afklm.rigui.entity.lastactivity.LastActivity;
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
