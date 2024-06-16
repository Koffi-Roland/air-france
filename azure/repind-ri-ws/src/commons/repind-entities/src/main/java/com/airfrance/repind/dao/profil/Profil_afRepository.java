package com.airfrance.repind.dao.profil;

import com.airfrance.repind.entity.profil.Profil_af;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface Profil_afRepository extends JpaRepository<Profil_af, Integer> {
	
	@Query("select prf from Profil_af prf where prf.smatricule = :matricule")
	List<Profil_af> getProfilAfByMatricule(@Param("matricule") String matricule);

	@Query("select paf from Profil_af paf where paf.icle_prf in (select icle_prf from Profil_mere pm where pm.sgin_ind = :sgin_ind)")
	public List<Profil_af> getProfilAFBySginInd(@Param("sgin_ind") String sgin_ind);

	@Query("select paf from Profil_af paf where paf.icle_prf = :icle_prf")
	public Profil_af findByIclePrf(@Param("icle_prf") Integer icle_prf);
}
