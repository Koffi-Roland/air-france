package com.afklm.repind.msv.preferences.repository;

import com.afklm.repind.msv.preferences.entity.Preference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository	
public interface PreferenceRepository extends JpaRepository<Preference, Long> {

	List<Preference> findByGin(String gin);
	
	// @Query("select pref from Preference pref where pref.gin = :gin and pref.type LIKE 'U%' ")
	@Query("select pref from Preference pref where pref.gin = :gin and pref.type in ('UCO','UFB','UFD','ULO','ULS','UMU','UOB','UPM','UST','UTF','UTS') ")
	List<Preference> findUltimatePreferencesByGin(@Param("gin") String gin);

}
