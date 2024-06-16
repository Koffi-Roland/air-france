package com.afklm.repind.common.repository.individual;

import com.afklm.repind.common.entity.individual.UsageClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
/**
 * A repository used to fetch data about the usage client of an individual
 */
public interface UsageClientRepository extends JpaRepository<UsageClientEntity, Integer> {

    /**
     * @param gin The gin related to the data we want
     * @return The list of usage client of the individual related to the given gin
     */
    List<UsageClientEntity> getUsageClientEntitiesByGin(String gin);
}
