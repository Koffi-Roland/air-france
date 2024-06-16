package com.afklm.repind.common.repository.individual;

import com.afklm.repind.common.entity.individual.DelegationData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
/**
 * A repository used to fetch data about delegation
 */
public interface DelegationDataRepository extends JpaRepository<DelegationData, Integer> {
    /**
     * @param gin The gin related to the data we want
     * @return A list of all the delegation where our gin is the delegate of the delegation
     */
    List<DelegationData> getDelegationDataEntitiesByDelegateGin(String gin);

    /**
     * @param gin The gin related to the data we want
     * @return A list of all the delegation where our gin is the delegator of the delegation
     */
    List<DelegationData> getDelegationDataEntitiesByDelegatorGin(String gin);

    /**
     * @param delegationDataId The delegatioNDataId related to the data we want
     * @return A list of all the delegation where our delegationDataId is the if of the delegation
     */
    DelegationData getDelegationDataEntityByDelegationDataId(String delegationDataId);
}
