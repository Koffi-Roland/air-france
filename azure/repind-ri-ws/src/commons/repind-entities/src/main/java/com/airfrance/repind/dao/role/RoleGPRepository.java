package com.airfrance.repind.dao.role;

import com.airfrance.repind.entity.role.RoleGP;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface RoleGPRepository extends JpaRepository<RoleGP, Integer> {
	
	@Query("SELECT rg from RoleGP rg WHERE rg.businessRole.ginInd = :gin")
	List<RoleGP> findByGin(@Param("gin") String gin);
	
	/**
	 * @param matricule
	 * @return
	 */
	List<RoleGP> findByMatricule(String matricule);

	@Query(value = "SELECT ro.scode, ro.slibelle FROM SIC2.REF_ORIGINE ro WHERE ro.scode = :code", 
			nativeQuery=true)
	Object[] getRefOriginByCode(@Param("code") String code);

	@Query("select r from RoleGP r where r.cleRole in (select cleRole from BusinessRole br where br.ginInd = :gin)")
	public List<RoleGP> getRoleGPByGin(@Param("gin") String gin);

	@Query("select count(1) from RoleGP r where r.cleRole in (select cleRole from BusinessRole br where br.ginInd = :gin)")
	public int getGPRolesCount(@Param("gin") String gin);

	@Transactional
	@Modifying
	@Query("Update RoleGP r set r.ordreIdentifiant = :orderId where r.cleRole = :cleRole")
	public void updateIdentifiantOrder(@Param("cleRole") Integer cleRole, @Param("orderId") String orderId);

	@Transactional
	@Modifying
	@Query("Update RoleGP r set r.codePays = :countryCode where r.cleRole = :cleRole")
	public void updateCountryCode(@Param("cleRole") Integer cleRole, @Param("countryCode") String countryCode);

	public RoleGP findByCleRole(Integer cleRole);
}
