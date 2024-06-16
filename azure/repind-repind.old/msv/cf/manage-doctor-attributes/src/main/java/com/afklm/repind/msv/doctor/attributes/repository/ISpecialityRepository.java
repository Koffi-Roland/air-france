package com.afklm.repind.msv.doctor.attributes.repository;

import com.afklm.repind.msv.doctor.attributes.entity.node.Speciality;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.Collection;
import java.util.Optional;

public interface ISpecialityRepository extends Neo4jRepository<Speciality, Long> {

    Optional<Speciality> findByValue(String iValue);
    Collection<Speciality> findAllByValueIn(Collection<String> iValues);
}
