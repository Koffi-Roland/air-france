package com.afklm.repind.common.repository.individual;

import com.afklm.repind.common.entity.individual.ComplementaryInformationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
/**
 * A repository used to fetch data about complementary information related to a specific delegation
 */
public interface ComplementaryInformationRepository extends JpaRepository<ComplementaryInformationEntity, Long> {
    /**
     * @param delegationDataId The delegationDataId shared between all the complementary information of a specific delegation
     * @return The list of all the complementary information of a delegation
     */
    List<ComplementaryInformationEntity> findAllByDelegationDataId(String delegationDataId);
}
