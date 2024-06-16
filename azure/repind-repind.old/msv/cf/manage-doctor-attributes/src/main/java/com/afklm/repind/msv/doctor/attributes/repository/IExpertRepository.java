package com.afklm.repind.msv.doctor.attributes.repository;

import com.afklm.repind.msv.doctor.attributes.entity.relationship.Expert;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface IExpertRepository extends Neo4jRepository<Expert , Long> {
}
