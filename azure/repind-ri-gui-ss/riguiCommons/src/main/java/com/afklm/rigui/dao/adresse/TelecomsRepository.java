package com.afklm.rigui.dao.adresse;

import com.afklm.rigui.entity.adresse.Telecoms;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TelecomsRepository extends JpaRepository<Telecoms, String> {
	@Query(value = "select t from Telecoms t where t.sain in (select tel from Telecoms tel "
			+ "where tel.sgin = :gin "
			+ "and tel.sstatut_medium = 'V' "
			+ "and tel.sterminal=:terminalType "
			+ "and tel.snorm_inter_country_code=:countryCode "
			+ "and tel.snorm_nat_phone_number=:phoneNumber) and ROWNUM <=1 ORDER BY t.ddate_modification DESC")
	public Telecoms findLatest(@Param("gin") String gin, @Param("terminalType") String terminalType, 
			@Param("countryCode") String countryCode, @Param("phoneNumber") String phoneNumber);

	@Query(value = "select t from Telecoms t where t.sain in (select tel from Telecoms tel "
			+ "where tel.sgin = :gin "
			+ "and tel.scode_medium=:mediumCode "
			+ "and tel.sterminal=:terminalType "
			+ "and tel.snumero=:phoneNumber) and ROWNUM <=1 ORDER BY t.ddate_modification DESC")
	public Telecoms findLatestByUsageCodeValidOrNot(@Param("gin") String gin, @Param("mediumCode") String mediumCode, 
			@Param("terminalType") String terminalType, @Param("phoneNumber") String phoneNumber);

	@Query(value = "select t from Telecoms t where t.sain in (select tel from Telecoms tel "
			+ "where tel.sgin = :gin "
			+ "and tel.sstatut_medium = 'V' "
			+ "and tel.scode_medium=:mediumCode "
			+ "and tel.sterminal=:terminalType "
			+ "and tel.snumero=:phoneNumber) and ROWNUM <=1 ORDER BY t.ddate_modification DESC ")
	public Telecoms findLatestByUsageCode(@Param("gin") String gin, @Param("terminalType") String terminalType,
			@Param("mediumCode") String mediumCode, @Param("phoneNumber") String phoneNumber);

	@Query(value = "select t from Telecoms t where t.sain in (select tel from Telecoms tel "
			+ "left outer join tel.individu ind "
			+ "where ind.sgin = :gin "
			+ "and (tel.sstatut_medium = 'V' "
			+ "or tel.sstatut_medium = 'I') "
			+ "and tel.sterminal = :terminalType) "
			+ "and ROWNUM <=1 order by t.sstatut_medium desc, t.ddate_modification desc, t.sain desc")
	public Telecoms findLatest(@Param("gin") String gin, @Param("terminalType") String terminalType);

	@Query(value = "select t from Telecoms t where t.sain in (select tel from Telecoms tel "
			+ "left outer join tel.individu ind "
			+ "where ind.sgin = :gin "
			+ "and tel.scode_medium = :mediumCode "
			+ "and (tel.sstatut_medium = 'V' "
			+ "or tel.sstatut_medium = 'I') "
			+ "and tel.sterminal = :terminalType) "
			+ "and ROWNUM <=1 order by t.sstatut_medium desc, t.ddate_modification desc, t.sain desc")
	public Telecoms findLatestByUsageCode(@Param("gin") String gin, @Param("mediumCode") String mediumCode, @Param("terminalType") String terminalType);

	@Modifying
	@Query(value = "update Telecoms tel set "
			+ "tel.sstatut_medium = 'X', "
			+ "tel.ssignature_modification = :modificationSignature, "
			+ "tel.ssite_modification = :modificationSite, "
			+ "tel.ddate_modification = :modificationDate "
			+ "where tel.sgin = :gin "
			+ "and (tel.sstatut_medium = 'V' "
			+ "or tel.sstatut_medium = 'I') "
			+ "and tel.sterminal = :terminalType "
			+ "and sain <> :sain")
	public void removeAllButThis(@Param("gin") String gin, @Param("terminalType") String terminalType, @Param("sain") String sain, 
			@Param("modificationSignature") String modificationSignature, @Param("modificationSite") String modificationSite, @Param("modificationDate") Date modificationDate);

	@Modifying
	@Query(value = "update Telecoms tel set "
			+ "tel.sstatut_medium = 'X', "
			+ "tel.ssignature_modification = :modificationSignature, "
			+ "tel.ssite_modification = :modificationSite, "
			+ "tel.ddate_modification = :modificationDate "
			+ "where tel.sgin = :gin "
			+ "and (tel.sstatut_medium = 'V' "
			+ "or tel.sstatut_medium = 'I') "
			+ "and tel.scode_medium = :mediumCode "
			+ "and tel.sterminal = :terminalType "
			+ "and sain <> :sain")
	public void removeAllButThisByUsageCode(@Param("gin") String gin, @Param("terminalType") String terminalType, @Param("sain") String sain, @Param("mediumCode") String mediumCode, 
			@Param("modificationSignature") String modificationSignature, @Param("modificationSite") String modificationSite, @Param("modificationDate") Date modificationDate);
	


	/**
	 * Finds telecoms of moral person having the GIN gin.
	 *
	 * @param gin
	 *
	 * @return null if no telecoms found for this gin.
	 */
	List<Telecoms> findBySgin(String gin);

	@Query(value = "select count(1) from Telecoms tel "
			+ "where tel.sgin = :gin "
			+ "and tel.scode_medium = :codeMedium "
			+ "and tel.sstatut_medium = 'V'")
    int getNumberTelecomsProOrPersoByGinAndCodeMedium(@Param("gin") String gin, @Param("codeMedium") String codeMedium);
	
	@Query(value = "select tel from Telecoms tel where tel.sgin = :gin and tel.sstatut_medium in ('I', 'S', 'V', 'H', 'T')")
	List<Telecoms> findTelecomsToRemoveByGIN(@Param("gin") String gin);

	public Telecoms findBySain(String sain);

}
