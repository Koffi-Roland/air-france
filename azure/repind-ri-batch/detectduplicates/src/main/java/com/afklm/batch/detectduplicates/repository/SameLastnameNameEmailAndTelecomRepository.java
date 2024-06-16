package com.afklm.batch.detectduplicates.repository;

import com.afklm.batch.detectduplicates.entity.SameLastnameNameEmailAndTelecomEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SameLastnameNameEmailAndTelecomRepository extends JpaRepository<SameLastnameNameEmailAndTelecomEntity, Long> {
    Page<SameLastnameNameEmailAndTelecomEntity> findAllByDuplicateIs(boolean isDuplicate, Pageable page);
    List<SameLastnameNameEmailAndTelecomEntity> findAllByLastnameNameAndAndTelecomNbGINsIsGreaterThanAndEmailNbGINsIsGreaterThan(String lastnameName, int telNbGins, int emailNbGINs);
}
