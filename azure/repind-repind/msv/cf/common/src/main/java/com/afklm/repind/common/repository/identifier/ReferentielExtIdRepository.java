package com.afklm.repind.common.repository.identifier;

import com.afklm.repind.common.entity.identifier.ReferentielExternalIdentifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReferentielExtIdRepository extends   JpaRepository<ReferentielExternalIdentifier, String> {

    ReferentielExternalIdentifier findByOption(String option);
    boolean existsByOption(String option);

}
