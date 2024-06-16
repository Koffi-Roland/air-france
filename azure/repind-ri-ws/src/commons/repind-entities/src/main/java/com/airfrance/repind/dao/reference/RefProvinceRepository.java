package com.airfrance.repind.dao.reference;

import com.airfrance.repind.entity.reference.RefProvince;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RefProvinceRepository extends JpaRepository<RefProvince, Integer> {

	@Query(value = "SELECT ref FROM RefProvince ref WHERE code = :dcode and codePays = :ccode")
	List<RefProvince> isValidProvinceCode(@Param("dcode") String dcode, @Param("ccode") String ccode);
	
	@Query(value = "SELECT ref.code FROM RefProvince ref WHERE libelleEn = :label and codePays = :ccode")
	List<String> findProvinceCodeByLabel(@Param("label") String label, @Param("ccode") String ccode);

	Optional<RefProvince> findByCodeAndCodePays(String iCode , String iCodePays);
}
