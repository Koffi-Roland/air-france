package com.afklm.repind.common.repository.role;

import com.afklm.repind.common.entity.role.RoleGP;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleGPRepository extends JpaRepository<RoleGP, Integer> {

    @Query("select rg from RoleGP rg inner join BusinessRole br "
            + "on br.cleRole = rg.cleRole "
            + "where br.ginInd = :gin ")
    List<RoleGP> findRoleGPSByGin(@Param("gin") String gin);

    @Query("select count(1) from RoleGP rg inner join BusinessRole br "
            + "on br.cleRole = rg.cleRole "
            + "where br.ginInd = :gin ")
    Integer countRoleGPSByGin(@Param("gin") String gin);
}
