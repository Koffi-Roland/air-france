package com.afklm.rigui.dao.role;

import com.afklm.rigui.entity.role.RoleTravelers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RoleTravelersRepository extends JpaRepository<RoleTravelers, Integer> {

	@Query(value = "select SIC2.ISEQ_ROLE_Travelers.NEXTVAL from dual", nativeQuery = true)
    Long getSequence();
	
	/**
	 * Get role traveler associated to provided GIN
	 * @param gin
	 * @return
	 * @throws JrafDaoException 
	 */
	@Query("select rol from RoleTravelers rol "
			+ "where rol.gin = :gin "
			+ "order by rol.lastRecognitionDate desc")
	public List<RoleTravelers> findRoleTravelers(@Param("gin") String gin);
	
	/**
	 * Get role traveler associated to provided GIN
	 * @param gin
	 * @return
	 * @throws JrafDaoException 
	 */
	@Query("select rol from RoleTravelers rol "
			+ "where rol.gin = :gin "
			+ "order by rol.lastRecognitionDate desc")
	public RoleTravelers getRoleTravelers(@Param("gin") String gin);
	
}
