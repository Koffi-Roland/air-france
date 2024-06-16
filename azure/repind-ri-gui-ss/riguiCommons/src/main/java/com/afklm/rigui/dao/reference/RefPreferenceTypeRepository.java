package com.afklm.rigui.dao.reference;

import com.afklm.rigui.entity.reference.RefPreferenceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RefPreferenceTypeRepository extends JpaRepository<RefPreferenceType, String> {
	
	public List<RefPreferenceType> findByCode(String code);

	List<RefPreferenceType> findAllByOrderByCode();

}
