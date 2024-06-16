package com.afklm.repind.common.repository.identifier;

import com.afklm.repind.common.entity.identifier.ExternalIdentifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExternalIdentifierRepository extends JpaRepository<ExternalIdentifier, Long> {
    List<ExternalIdentifier> findAllByGin(String gin);

    /**
     * @param gin The gin related to the data we want
     * @return List of external identifiers related to the gin
     */
    List<ExternalIdentifier> findByGin(String gin);

    List<ExternalIdentifier> findAllByIdentifierAndType(String identifier, String type);

    ExternalIdentifier findByIdentifierAndTypeAndGin(String identifier, String type, String gin);
}
