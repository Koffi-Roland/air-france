package com.airfrance.repind.dao.identifier;

import com.airfrance.repind.entity.identifier.InternalIdentifierData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InternalIdentifierDataRepository
extends JpaRepository<InternalIdentifierData, Long> {
}
