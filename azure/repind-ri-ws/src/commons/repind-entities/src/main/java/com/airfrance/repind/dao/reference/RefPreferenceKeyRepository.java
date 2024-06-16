package com.airfrance.repind.dao.reference;

import com.airfrance.repind.entity.reference.RefPreferenceKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefPreferenceKeyRepository extends JpaRepository<RefPreferenceKey, String> {
}
