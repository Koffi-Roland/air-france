package com.airfrance.repind.dao.preference;

import com.airfrance.repind.entity.preference.PreferenceData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PreferenceDataRepository extends JpaRepository<PreferenceData, Long> {
}
