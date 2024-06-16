package com.airfrance.repind.dao.identifier;

import com.airfrance.repind.entity.identifier.InternalIdentifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InternalIdentifierRepository extends JpaRepository<InternalIdentifier, Long> {
}
