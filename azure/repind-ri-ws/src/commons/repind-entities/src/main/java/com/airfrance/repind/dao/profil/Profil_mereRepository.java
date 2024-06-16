package com.airfrance.repind.dao.profil;

import com.airfrance.repind.entity.profil.Profil_mere;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

public interface Profil_mereRepository extends JpaRepository<Profil_mere, Integer> {
	
	@Query("Select p from Profil_mere p where p.sgin_ind= :gin")
	Set<Profil_mere> findByGinInd(@Param("gin") String gin);

	@Query("select pm from Profil_mere pm where pm.sgin_ind = :sgin_ind")
	public List<Profil_mere> findBySginInd(@Param("sgin_ind") String sgin_ind);

	@Transactional
	@Modifying
	@Query("Update Profil_mere pm set pm.sgin_ind = :sgin_ind where pm.icle_prf = :icle_prf")
	public void updateSginInd(@Param("icle_prf") Integer icle_prf, @Param("sgin_ind") String sgin_ind);

	@Query("select pm from Profil_mere pm where pm.icle_prf = :icle_prf")
	public Profil_mere findByIclePrf(@Param("icle_prf") Integer icle_prf);
}
