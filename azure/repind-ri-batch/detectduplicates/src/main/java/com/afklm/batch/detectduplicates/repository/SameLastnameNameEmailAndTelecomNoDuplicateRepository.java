package com.afklm.batch.detectduplicates.repository;

import com.afklm.batch.detectduplicates.entity.SameLastnameNameEmailAndTelecomNoDuplicateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SameLastnameNameEmailAndTelecomNoDuplicateRepository extends JpaRepository<SameLastnameNameEmailAndTelecomNoDuplicateEntity, String> {
}
