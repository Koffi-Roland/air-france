package com.afklm.repind.common.repository.identifier;

import com.afklm.repind.common.entity.identifier.ExternalIdentifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface ExternalIdentifierRepository extends JpaRepository<ExternalIdentifier, Long> {

    List<ExternalIdentifier> findAllByIndividuGin(String gin);

    List<ExternalIdentifier> findAllByIdentifierAndType(String identifier, String type);

    Collection<ExternalIdentifier> findByIdentifierAndTypeAndIndividuIsNotNull(String iIdentifier , String iType);

    ExternalIdentifier findByIdentifierAndTypeAndIndividuGin(String identifier, String type, String gin);

}
