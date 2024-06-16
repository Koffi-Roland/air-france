package com.afklm.repind.msv.doctor.role.repository;

import com.afklm.repind.msv.doctor.role.entity.RoleContrats;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleContratsRepository extends JpaRepository<RoleContrats, String> {

    Optional<RoleContrats> findByIndividuGinAndTypeContrat(String iGin , String iTypeContrat);
}
