package com.airfrance.batch.detectduplicates.repository;

import com.airfrance.batch.detectduplicates.entity.SameLastnameNameEmailAndTelecomNoDuplicateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SameLastnameNameEmailAndTelecomNoDuplicateRepository extends JpaRepository<SameLastnameNameEmailAndTelecomNoDuplicateEntity, String> {
}
