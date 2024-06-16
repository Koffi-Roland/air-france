package com.airfrance.repind.dao.profil;

import com.airfrance.repind.entity.profil.Profils;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProfilsRepository extends JpaRepository<Profils, String> {

	@Query(value = "select SIC2.ISEQ_PROFILS.NEXTVAL from dual", nativeQuery = true)
    Long getProfilsNextValue();
    
	Profils findBySgin(String gin);
	
	@Query("select profil from Profils profil where profil.sgin = :gin")
	Profils findProfilsByGin(@Param("gin") String gin);
}
