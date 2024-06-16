package com.afklm.repind.msv.doctor.attributes.repository;

import com.afklm.repind.msv.doctor.attributes.entity.Role;
import com.azure.spring.data.cosmos.repository.CosmosRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IRoleRepository extends CosmosRepository<Role,String> {

    Role findByDoctorId(@Param("doctorId") String doctorId);
    Optional<Role> findByRoleId(@Param("roleId") String roleId);

    Role findByDoctorIdAndRoleIdNot(@Param("doctorId") String doctorId, @Param("roleId") String roleId);

}
