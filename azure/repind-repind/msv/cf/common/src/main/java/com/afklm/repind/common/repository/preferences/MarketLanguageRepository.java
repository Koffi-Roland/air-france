package com.afklm.repind.common.repository.preferences;

import com.afklm.repind.common.entity.preferences.MarketLanguageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MarketLanguageRepository extends JpaRepository<MarketLanguageEntity, Integer> {
    List<MarketLanguageEntity> getMarketLanguageEntitiesByComPrefId(Long comPrefId);
}
