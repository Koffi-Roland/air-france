package com.afklm.repind.common.repository.identifier;

import com.afklm.repind.common.entity.identifier.ExternalIdentifierData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface ExternalIdentifierDataRepository extends JpaRepository<ExternalIdentifierData, Long> {
    List<ExternalIdentifierData> findAllByIdentifierId(Long identifierId);

    List<ExternalIdentifierData> deleteAllByIdentifierId(Long identifierId);
}
