package com.afklm.repind.common.repository.profileComplianceScore;

import com.afklm.repind.common.entity.profile.compliance.score.RefPcsScore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefPcsScoreRepository extends JpaRepository<RefPcsScore, String> {
    /**
     * @param code The code Id of to the data we want
     * @return The reference of PCS score category
     */
    RefPcsScore findByCode(String code);
}
