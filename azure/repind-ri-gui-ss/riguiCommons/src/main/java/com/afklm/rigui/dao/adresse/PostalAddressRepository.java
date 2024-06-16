package com.afklm.rigui.dao.adresse;

import com.afklm.rigui.exception.jraf.JrafDaoException;
import com.afklm.rigui.entity.adresse.PostalAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostalAddressRepository extends JpaRepository<PostalAddress, String> {

	@Query(value = "select pa from PostalAddress pa, Usage_medium usa "
			+ "where pa.sgin=:gin "
			+ "and pa.scode_medium=:mediumCode "
			+ "and (pa.sstatut_medium= 'V' "
			+ "or pa.sstatut_medium= 'T' "
			+ "or pa.sstatut_medium= 'S') "
			+ "and pa.sain=usa.sain_adr "
			+ "and usa.scode_application=:applicationCode "
			+ "and usa.inum=:version")
	PostalAddress findPostalAddressByGinAndMediumCodeAndApplicationCodeAndVersion(@Param("gin") String gin, @Param("mediumCode") String mediumCode, 
			@Param("applicationCode") String applicationCode, @Param("version") Integer version);
	
	/**
	 * Find postal address associated to provided GIN and USAGE_MEDIUMS
	 * 
	 * @param gin
	 * @param addressRoleCode
	 * @param applicationCode
	 * @return
	 * @throws JrafDaoException
	 */
	@Query(value = "select pa.scode_pays from PostalAddress pa, Usage_medium usa "
			+ "where pa.sgin=:gin "
			+ "and pa.sain=usa.sain_adr "
			+ "and usa.scode_application=:applicationCode "
			+ "and usa.srole1=:addressRoleCode "
			+ "order by pa.ddate_modification DESC")
	List<String> findISIPostalAddressCodePays(@Param("gin") String gin, @Param("addressRoleCode") String addressRoleCode, @Param("applicationCode") String applicationCode);
	
	/**
	 * Find VALID postal address associated to provided GIN and USAGE_MEDIUMS
	 * 
	 * @param gin
	 * @param addressRoleCode
	 * @param applicationCode
	 * @return
	 * @throws JrafDaoException
	 */
	@Query(value = "select pa.scode_pays from PostalAddress pa, Usage_medium usa "
			+ "where pa.sgin=:gin "
			+ "and pa.sain=usa.sain_adr "
			+ "and pa.sstatut_medium='V' "
			+ "and usa.scode_application=:applicationCode "
			+ "and usa.srole1=:addressRoleCode "
			+ "order by pa.ddate_modification DESC")
	List<String> findISIValidPostalAddressCodePays(@Param("gin") String gin, @Param("addressRoleCode") String addressRoleCode, @Param("applicationCode") String applicationCode);
	
	/**
	 * Find VALID postal address associated to provided GIN
	 * 
	 * @param gin
	 * @return
	 * @throws JrafDaoException
	 */
	@Query(value = "select pa.scode_pays from PostalAddress pa "
			+ "where pa.sgin=:gin "
			+ "and pa.sstatut_medium='V' "
			+ "order by pa.ddate_modification DESC")
	List<String> findValidPostalAddressCodePays(@Param("gin") String gin);
	
	/**
	 * Find VALID postal address associated to provided GIN
	 * 
	 * @param gin
	 * @return
	 * @throws JrafDaoException
	 */
	@Query(value = "select adr from Individu ind "
			+ "right outer join ind.postaladdress adr "
			+ "where ind.sgin = :gin "
			+ "and (adr.sstatut_medium = 'V' "
			+ "or adr.sstatut_medium = 'I') ")
	List<PostalAddress> findPostalAddress(@Param("gin") String gin);

	@Query(value = "select count(1) from PostalAddress pa "
			+ "where pa.sgin = :gin "
			+ "and pa.scode_medium = :codeMedium "
			+ "and pa.sstatut_medium = 'V'")
    int getNumberProOrPersoAddressesByGinAndCodeMedium(@Param("gin") String gin, @Param("codeMedium") String codeMedium);

	List<PostalAddress> findBySgin(String gin);
	
	@Query(value = "select adr from Individu ind "
			+ "right outer join ind.postaladdress adr "
			+ "where ind.sgin = :gin "
			+ "and adr.sstatut_medium != 'X'")
	List<PostalAddress> findNotDeletedPostalAddress(@Param("gin") String gin);
	
	@Query(value = "select adr from Individu ind "
			+ "right outer join ind.postaladdress adr "
			+ "where ind.sgin = :gin")
	List<PostalAddress> findAllPostalAddress(@Param("gin") String gin);
	
	@Query(value = "select adr from PostalAddress adr "
			+ "join adr.usage_medium um "
			+ "where adr.sgin = :gin "
			+ "and adr.sstatut_medium in ('V', 'I', 'S', 'T') "
			+ "and um.scode_application = 'ISI' "
			+ "and um.srole1 = 'C'")
	List<PostalAddress> getIsiComplementaryAddress(@Param("gin") String gin);

	@Query("Select adr from PostalAddress adr where adr.sgin = :gin and adr.sstatut_medium IN :status")
	public List<PostalAddress> findByGinAndStatus(@Param("gin") String gin, @Param("status") List<String> status);

	public PostalAddress findBySain(String sain);

}
