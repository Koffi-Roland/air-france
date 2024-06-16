package com.afklm.repind.msv.preferences.repository;

import com.afklm.repind.msv.preferences.entity.RefPreferenceKeyType;
import com.afklm.repind.msv.preferences.entity.RefPreferenceKeyTypeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RefPreferenceKeyTypeRepository extends JpaRepository<RefPreferenceKeyType, RefPreferenceKeyTypeId> {

	@Query("Select ref.refPreferenceKeyTypeId.key from RefPreferenceKeyType ref where ref.refPreferenceKeyTypeId.type = :type")
	List<String> findByType(@Param("type") String type);
	
}
