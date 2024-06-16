package com.afklm.rigui.dao.reference;

import com.afklm.rigui.entity.reference.RefPreferenceKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefPreferenceKeyRepository extends JpaRepository<RefPreferenceKey, String> {
}
