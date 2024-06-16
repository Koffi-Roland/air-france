package com.airfrance.repind.dao.reference;


import com.airfrance.repind.entity.reference.RefPreferenceDataKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RefPreferenceDataKeyRepository extends JpaRepository<RefPreferenceDataKey, String> {

	public List<RefPreferenceDataKey> findByCode(String code);

	public List<RefPreferenceDataKey> findByNormalizedKey(String key);

	public List<RefPreferenceDataKey> findAllByOrderByCode();
}
