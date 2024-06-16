package com.airfrance.batch.common.repository.kpi;

import com.airfrance.batch.common.entity.kpi.HistorizationKPIEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistorizationKpiRepository extends JpaRepository<HistorizationKPIEntity,Integer> {
}
