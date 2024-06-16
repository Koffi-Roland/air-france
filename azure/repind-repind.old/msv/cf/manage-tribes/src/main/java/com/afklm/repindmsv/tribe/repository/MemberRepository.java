package com.afklm.repindmsv.tribe.repository;

import com.afklm.repindmsv.tribe.entity.node.Member;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends Neo4jRepository<Member, Long> {

    @Query("MATCH (n:Member) where n.gin=$0 RETURN n")
    Member findByGin(String gin);


    @Query("MATCH (t:Tribe)-[r:MEMBERSHIP]-(n:Member) where n.gin=$0 and  n.status=$1 RETURN n, collect(r), t")
    Member retrieveMemberWithValidatedStatus(String gin, String status);



		
}