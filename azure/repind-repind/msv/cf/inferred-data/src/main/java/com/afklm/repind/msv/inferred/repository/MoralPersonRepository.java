package com.afklm.repind.msv.inferred.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.afklm.repind.msv.inferred.entity.MoralPerson;

@Repository
public interface MoralPersonRepository extends JpaRepository<MoralPerson, String> {

	@Query("select mP from MoralPerson mP where mP.gin = :gin and mP.status NOT IN ('X', 'F', 'H')")
	Optional<MoralPerson> findMoralPersonNotDeleted(@Param("gin") String gin);
	
}