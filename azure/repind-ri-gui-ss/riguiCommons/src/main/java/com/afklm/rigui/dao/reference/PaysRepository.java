package com.afklm.rigui.dao.reference;

import com.afklm.rigui.entity.reference.Pays;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaysRepository extends JpaRepository<Pays, String> {
	
	public List<Pays> findByLibellePaysEn(String countryLabel);
	
	@Query("select distinct pays from Pays pays order by codePays")
	public List<Pays> providePaysWithPagination(Pageable pageable);
	
	@Query("Select p from Pays p where p.codePays = :code")
	public List<Pays> findCountry(@Param("code") String code);
	
	@Query("select count(distinct codePays) from Pays")
	public long count();
	
	@Modifying(clearAutomatically = true)
	@Query("update Pays set normalisable = :isNormalisable where codePays = :pays ")
	public void updateNormalisableByCodePays(@Param("isNormalisable") String isNormalisable, @Param("pays") String pays);
}
