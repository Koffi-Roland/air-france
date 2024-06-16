package com.afklm.repind.msv.search.gin.by.social.media.repository;

import com.afklm.repind.msv.search.gin.by.social.media.entity.ReferentielExternalIdentifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IReferentielExtIdRepository extends   JpaRepository<ReferentielExternalIdentifier, String> {

    ReferentielExternalIdentifier findByOption(String option);
    boolean existsByOption(String option);

}
