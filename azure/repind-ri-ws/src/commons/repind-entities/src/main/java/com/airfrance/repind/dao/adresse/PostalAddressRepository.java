package com.airfrance.repind.dao.adresse;

import org.springframework.data.domain.Pageable;
import com.airfrance.ref.exception.jraf.JrafDaoException;
import com.airfrance.repind.entity.adresse.PostalAddress;
import org.springframework.data.jpa.repository.Modifying;
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

	/**
	 * Find ALL postall address associated to personne morale
	 *
	 * @param gin
	 * @return
	 * @throws JrafDaoException
	 */
	@Query(value = "select adr from PersonneMorale pm "
			+ "right outer join pm.postalAddresses adr "
			+ "where pm.gin = :gin ")
	List<PostalAddress> findPMPostalAddress(@Param("gin") String gin);
	
	/**
	 * Finds all postal addresses associated to the moral person
	 * having The GIN gin. Returned addresses are given from the
	 * firstResultIndex and are in maximum maxResults.
	 *
	 * @param gin
	 * @param firstResultIndex
	 * @param endIndex
	 *
	 * @return null if no results found.
	 *
	 * @throws JrafDaoException
	 *             if a technical exception is fired.
	 */
	@Query(value = "select pa from PostalAddress pa "
			+ "where pa.personneMorale.gin = :gin ")
	List<PostalAddress> findByPMGin(@Param("gin") String gin, Pageable pageable);

	@Query(value = "select m.SAIN from sic2.MBR_ADR m "
			+ "where m.IKEY_MBR = :key ", nativeQuery = true)
	List<String> getMbrAdrSainByKey(@Param("key") Integer key);

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

	@Query("Update PostalAddress p set p.sno_et_rue = :noAndStreet where p.sain = :sain")
	public void updateNumberAndStreet(@Param("sain") String sain, @Param("noAndStreet") String noAndStreet);

	/**
	 * Find the address for a PersonneMorale for the specified GIN and CodeMedium
	 *
	 * @param gin      the GIN of PersonneMorale
	 * @param codeList list of CodeMedium
	 * @return the list of address found
	 */
	@Query("Select pa from PostalAddress pa where pa.personneMorale.gin = :gin and pa.scode_medium IN :codeList")
	public List<PostalAddress> findByGinPmCodeMedium(@Param("gin") String gin, @Param("codeList") List<String> codeList);

	/**
	 * Associates the address to the specified PersonneMorale
	 *
	 * @param gin  GIN of the PersonneMorale
	 * @param sain Unique identifier of the address to be updated
	 */
	@Modifying
	@Query("Update PostalAddress pa set pa.personneMorale.gin = :ginPm where pa.sain = :sain")
	public void updateGinPm(@Param("ginPm") String gin, @Param("sain") String sain);

}
