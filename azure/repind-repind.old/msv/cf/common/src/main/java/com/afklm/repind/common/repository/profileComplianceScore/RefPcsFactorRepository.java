package com.afklm.repind.common.repository.profileComplianceScore;

import com.afklm.repind.common.entity.profile.compliance.score.RefPcsFactor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefPcsFactorRepository extends JpaRepository<RefPcsFactor, String> {
    /**
     * @param code The code Id of to the data we want
     * @return The reference of PCS score category
     */
    RefPcsFactor findByCode(String code);
}
