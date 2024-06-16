package com.afklm.batch.detectduplicates.repository;

import com.afklm.batch.detectduplicates.entity.SameLastnameNameEmailOrTelecomEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SameLastnameNameEmailOrTelecomRepository extends JpaRepository<SameLastnameNameEmailOrTelecomEntity, Long> {
    List<SameLastnameNameEmailOrTelecomEntity> findAllByLastnameNameAndNbGINsGreaterThan(String lastnameName, int nbGins);
    Page<SameLastnameNameEmailOrTelecomEntity> findAllByDuplicateIs(boolean isDuplicate, Pageable page);
}
