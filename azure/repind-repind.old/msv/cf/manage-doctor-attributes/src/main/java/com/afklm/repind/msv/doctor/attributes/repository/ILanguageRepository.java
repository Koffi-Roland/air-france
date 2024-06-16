package com.afklm.repind.msv.doctor.attributes.repository;

import com.afklm.repind.msv.doctor.attributes.entity.node.Language;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.Collection;
import java.util.Optional;

public interface ILanguageRepository extends Neo4jRepository<Language, Long> {

    Optional<Language> findByAcronymeAndValue(String iAcronyme , String iValue);
    Collection<Language> findAllByValueIn(Collection<String> iValues);
}
