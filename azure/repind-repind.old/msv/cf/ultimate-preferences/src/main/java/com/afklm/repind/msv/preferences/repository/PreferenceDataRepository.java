package com.afklm.repind.msv.preferences.repository;

import com.afklm.repind.msv.preferences.entity.PreferenceData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PreferenceDataRepository extends JpaRepository<PreferenceData, Long> {

	List<PreferenceData> findByPreferencePreferenceId(Long id);
	
}
