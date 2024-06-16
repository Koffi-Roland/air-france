package com.afklm.repind.msv.doctor.attributes.repository;

import com.afklm.repind.msv.doctor.attributes.entity.node.AirLineCode;
import com.afklm.repind.msv.doctor.attributes.entity.node.Speciality;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.Collection;
import java.util.Optional;

public interface IAirLineCodeRepository extends Neo4jRepository<AirLineCode, Long> {
    Optional<AirLineCode> findByValue(String iValue);

}
