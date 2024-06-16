package com.afklm.repind.msv.doctor.attributes.repository;

import com.afklm.repind.msv.doctor.attributes.entity.node.Role;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;

public interface IRoleRepository extends Neo4jRepository<Role , String> {

    Role findByDoctorId(@Param("doctorId") String doctorId);

    Role findByDoctorIdAndRoleIdNot(@Param("doctorId") String doctorId, @Param("roleId") String roleId);

    @Query("MATCH(r:Role {roleId : $0})-[l:EXPERT]-(s:Speciality{ value:$1}) DELETE l")
    void detachRelationExpert(String roleId, String value);

    @Query("MATCH(r:Role {roleId : $0})-[l:APPROVAL_BY]-(a:AirLineCode{ value:$1}) DELETE l")
    void detachRelationApproval(String roleId, String value);

    @Query("MATCH(r:Role {roleId : $0})-[l:SPEAK]-(a:Language{ value:$1}) DELETE l")
    void detachRelationSpeak(String roleId, String value);


}
