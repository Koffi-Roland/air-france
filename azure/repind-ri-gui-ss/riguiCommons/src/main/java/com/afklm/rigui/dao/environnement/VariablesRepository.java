package com.afklm.rigui.dao.environnement;

import com.afklm.rigui.entity.environnement.Variables;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VariablesRepository extends JpaRepository<Variables, String>{

	@Query("select variables from Variables variables where variables.envKey not in :notAlterable")
	List<Variables> getAllVariablesAlterable(@Param("notAlterable") List<String> notAlterable);
}
