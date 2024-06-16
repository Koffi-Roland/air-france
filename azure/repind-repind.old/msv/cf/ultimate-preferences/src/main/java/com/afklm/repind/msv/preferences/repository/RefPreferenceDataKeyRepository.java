package com.afklm.repind.msv.preferences.repository;

import com.afklm.repind.msv.preferences.entity.RefPreferenceDataKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RefPreferenceDataKeyRepository extends JpaRepository<RefPreferenceDataKey, String> {

	@Query("Select ref.code from RefPreferenceDataKey ref where ref.code not in ('C', 'I', 'X')")
	List<String> findStatusForUpdate();
	
}	
