package com.airfrance.repind.dao.role;

import com.airfrance.repind.entity.role.RoleUCCR;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RoleUCCRRepository extends JpaRepository<RoleUCCR, Integer> {

	@Query("select count(ru) from RoleUCCR ru "
			+ "where ru.ceID = :ceID and ru.gin = :gin and ru.etat != 'X'")
	Long countNbOfUCCRIDWByGINAndCEID(@Param("gin") String gin, @Param("ceID") String ceid);

	@Query("select ru.gin from RoleUCCR ru "
			+ "where ru.uccrID = :uccrID and ru.etat != 'X'")
	String getGinByUCCRID(@Param("uccrID") String uccrid);

	@Query("select ru from RoleUCCR ru "
			+ "where ru.gin = :gin and ru.etat != 'X'")
	List<RoleUCCR> getByGin(@Param("gin") String gin);

	public List<RoleUCCR> findByGin(String gin);
}
