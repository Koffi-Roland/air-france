package com.airfrance.repind.dao.tracking;

import com.airfrance.repind.entity.tracking.TriggerChangeAutMailing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface TriggerChangeAutMailingRepository extends JpaRepository<TriggerChangeAutMailing, Long> {
	
	@Query("select count(tri) from TriggerChangeAutMailing tri "
			+ "where (tri.gin= :gin "
			+ "or tri.ginPM= :gin) "
			+ "and tri.dateChange > :date ")
    Long countGinMoreRecent(@Param("gin") String gin, @Param("date") Date date);
}
