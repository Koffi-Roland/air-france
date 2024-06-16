package com.airfrance.repind.dao.reference;

import com.airfrance.repind.entity.reference.RefPreferenceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RefPreferenceTypeRepository extends JpaRepository<RefPreferenceType, String> {
	
	public List<RefPreferenceType> findByCode(String code);

	List<RefPreferenceType> findAllByOrderByCode();

}
