package com.airfrance.repind.dao.firme;

import com.airfrance.repind.entity.firme.PersonneMorale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PersonneMoraleRepository extends JpaRepository<PersonneMorale, String>, PersonneMoraleRepositoryCustom {
    @Query(name = "persMorale.native.findSgin", nativeQuery = true)
    List<String[]> findSgin(
            @Param("persMoraleTypes") List<String> persMoraleTypes,
            @Param("startDate") LocalDate startModificationDate);

    @Query(name = "persMorale.native.findSginWithoutSegmentation", nativeQuery = true)
    List<String[]> findSginWithoutSegmentation(
            @Param("persMoraleTypes") List<String> persMoraleTypes,
            @Param("startDate") LocalDate startModificationDate);

    @Query(name = "persMorale.native.findSginWithExpiredSegmentation", nativeQuery = true)
    List<String[]> findSginWithExpiredSegmentation(
            @Param("persMoraleTypes") List<String> persMoraleTypes);
}
