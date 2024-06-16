package com.airfrance.repind.dao.agence;

import com.airfrance.ref.exception.jraf.JrafDaoException;
import com.airfrance.repind.bean.AgenceBean;
import com.airfrance.repind.entity.agence.Agence;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface AgenceRepository extends JpaRepository<Agence, String>, AgenceRepositoryCustom {

	List<Agence> findAllByAgenceRA2(String ra2, Pageable pageable);

	@Query("SELECT DISTINCT ag FROM Agence ag LEFT JOIN FETCH ag.postalAddresses adr WHERE ag.agenceRA2 = 'Y' AND adr.scode_medium = 'L' AND adr.scode_pays =:countryCode "
			+ "AND adr.sville like :cityCode% AND ag.nom like :name% ")
	List<Agence> findRa2AgencyByNameAddress(@Param("name") String name, @Param("cityCode") String cityCode,
			@Param("countryCode") String countryCode);

	@Query("SELECT DISTINCT ag FROM Agence ag LEFT JOIN FETCH ag.postalAddresses LEFT JOIN FETCH ag.pmZones WHERE ag.numeroIATAMere = :momIataNumber")
	List<Agence> findAgencyDaughtersByMomIataNumber(@Param("momIataNumber") String momIataNumber);

	@Query("select new com.airfrance.repind.bean.AgenceBean(a.gin, a.agenceRA2, a.nom, a.bsp, a.localisation, a.codeVilleIso) "
			+ "from Agence a " + "where a.nom like :name%")
	List<AgenceBean> findLightAgenceByName(@Param("name") String name) throws JrafDaoException;

	@Query("SELECT DISTINCT ag FROM Agence ag LEFT JOIN FETCH ag.offices LEFT JOIN FETCH ag.numerosIdent numId WHERE numId.numero = :numeroId AND numId.type in :types "
			+ "AND (numId.dateFermeture IS NULL OR numId.dateFermeture >= SYSDATE )")
	List<Agence> findActiveByIdType(@Param("numeroId") String numeroId, @Param("types") List<String> types);

	@Query("SELECT DISTINCT ag FROM Agence ag LEFT JOIN FETCH ag.offices LEFT JOIN FETCH ag.numerosIdent numId WHERE numId.numero = :numeroId AND numId.type in :types "
			+ "AND ag.statut = 'X' ")
	List<Agence> findInactiveByIdType(@Param("numeroId") String numeroId, @Param("types") List<String> types);

	@Query("SELECT DISTINCT ag FROM Agence ag INNER JOIN FETCH ag.offices off WHERE off.officeID = :oid AND off.codeGDS = :gds")
	List<Agence> findByOidAndGds(@Param("oid") String oid, @Param("gds") String gds);

	/**
	 * Find the waiting list (agencies that have 'Q' as status).
	 *
	 * @return the waiting list
	 */
	@Query("SELECT DISTINCT ag FROM Agence ag WHERE ag.statut = 'Q' AND ag.type <> '0'")
	List<Agence> findWaitingList();

	/**
	 * Retrieves list of agencies with close ZV3 by date
	 *
	 * @param closeDate Zv3 closure date
	 *
	 * @return the agency list
	 */
	@Query("SELECT ag FROM Agence ag INNER JOIN FETCH ag.pmZones pmz INNER JOIN FETCH pmz.zoneDecoup zd WHERE TYPE(zd) = ZoneVente AND pmz.dateOuverture <= :closedate AND (pmz.dateFermeture IS NULL OR pmz.dateFermeture >= :closedate) AND zd.dateFermeture <= :closedate")
	List<Agence> findWithClosedZv3ByDate(@Param("closedate") Date closeDate);

	@Query("SELECT DISTINCT ag FROM Agence ag WHERE ag.codeVilleIso = :city AND ag.agenceRA2 = 'Y'")
	List<Agence> findAllRa2ByCodeVilleIso(@Param("city") String city);
	/**
	 * Retrieves a list of agencies that have links to the provided ZC
	 * @param zc1 Mandatory
	 * @param zc2 Mandatory
	 * @param zc3 Optional
	 * @param zc4 Optional
	 * @param zc5 Optional
	 * @return
	 */
	@Query("SELECT DISTINCT ag FROM Agence ag INNER JOIN FETCH ag.pmZones pmz INNER JOIN FETCH pmz.zoneDecoup zd WHERE TYPE(zd) = ZoneComm AND pmz.lienPrivilegie = 'O' AND (pmz.dateFermeture IS NULL OR pmz.dateFermeture >= :today) AND zd.zc1 = :zc1 AND zd.zc2 = :zc2 AND (:zc3 IS NULL OR zd.zc3 = :zc3) AND (:zc4 IS NULL OR zd.zc4 = :zc4) AND (:zc5 IS NULL OR zd.zc5 = :zc5)")
	List<Agence> findAgencyByZc(@Param("zc1") String zc1, @Param("zc2") String zc2, @Param("zc3") String zc3, @Param("zc4") String zc4, @Param("zc5") String zc5,@Param("today") Date today);
	/**
	 * Retrieves a list of agencies that have links to the provided ZV
	 * @param zv0 Mandatory
	 * @param zv1 Mandatory
	 * @param zv2 Optional
	 * @param zv3 Optional
	 * @return
	 */
	@Query("SELECT DISTINCT ag FROM Agence ag INNER JOIN FETCH ag.pmZones pmz INNER JOIN FETCH pmz.zoneDecoup zd WHERE TYPE(zd) = ZoneVente AND (pmz.dateFermeture IS NULL OR pmz.dateFermeture >= :today) AND zd.zv0 = :zv0 AND zd.zv1 = :zv1 AND (:zv2 IS NULL OR zd.zv2 = :zv2) AND (:zv3 IS NULL OR zd.zv3 = :zv3)")
	List<Agence> findAgencyByZv(@Param("zv0") Integer zv0, @Param("zv1") Integer zv1, @Param("zv2") Integer zv2, @Param("zv3") Integer zv3, @Param("today") Date today);

	@Query("SELECT ag FROM Agence ag INNER JOIN FETCH ag.reseaux mr INNER JOIN FETCH mr.reseau r WHERE r.code = :networkCode AND (mr.dateFin IS NULL OR mr.dateFin>= :today)")
	List<Agence> findAgencyByNetwork(@Param("networkCode") String networkCode, @Param("today") Date today);
	
	@Query(value = "SELECT DISTINCT ag FROM Agence ag LEFT JOIN FETCH ag.numerosIdent numId WHERE ag.statut = 'D'",
			countQuery = "SELECT count(DISTINCT ag) FROM Agence ag WHERE ag.statut = 'D'")
	Page<Agence> findDeletedAgencies(Pageable pageable);
}
