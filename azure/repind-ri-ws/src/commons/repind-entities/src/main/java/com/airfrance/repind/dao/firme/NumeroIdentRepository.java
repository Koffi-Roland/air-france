package com.airfrance.repind.dao.firme;

import com.airfrance.repind.entity.firme.NumeroIdent;
import com.airfrance.repind.entity.firme.NumeroIdentLight;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NumeroIdentRepository extends JpaRepository<NumeroIdent, Long>,NumeroIdentRepositoryCustom {

	/**
	 * Find list of Numero Ident from IATA and Gin Agency
	 * @param numeroIATAMere
	 * @param ginAgency
	 * @return
	 */
	@Query("select distinct numId from NumeroIdent numId, Agence ag where ag.gin = :gin and ag.numeroIATAMere = :numero and ag.numeroIATAMere = numId.numero")
	public List<NumeroIdent> findByNumeroIATAMere(@Param("numero") String numeroIATAMere, @Param("gin") String ginAgency);
	
	/**
	 * Find list of Numero Ident from a Gin with Pagination
	 * @param gin
	 * @return
	 */
	@Query("select numId FROM NumeroIdent numId where numId.personneMorale.gin = :gin")
	public List<NumeroIdent> findByPMGin(@Param("gin") String gin, Pageable pageable);
	
	@Query("select numId FROM NumeroIdent numId where numId.personneMorale.gin = :gin")
	public List<NumeroIdent> findByPMGin(@Param("gin") String gin);
	
	/**
	 * Find list of Numero Ident from a Numero and a list of Type
	 * @param numero
	 * @param typeList
	 * @return
	 */
	@Query("select numId from NumeroIdent numId where numId.numero = :numero and numId.type in :types")
	public List<NumeroIdent> findByKeyNumberType(@Param("numero") String numero, @Param("types") List<String> typeList);
	
	/**
	 * Find list of Numero Ident from a Key, Numero and Type
	 * @param key
	 * @param numero
	 * @param typeList
	 * @return
	 */
	@Query("select numId from NumeroIdent numId where numId.key = :key and numId.numero = :numero and numId.type = :types")
	public List<NumeroIdent> findAllByKeyNumberType(@Param("key") long key,@Param("numero") String numero, @Param("types") String type);

	
	/**
	 * Find list of Numero Ident Light (Lazy Personne Morale) from a Key Number Type and Gin
	 * @param keyNumberType
	 * @param pmGin
	 * @return
	 */
	@Query("select numId from NumeroIdentLight numId where numId.personneMoraleLight.gin = :gin and (:type is null or numId.type = :type) order by numId.dateFermeture desc")
	public List<NumeroIdentLight> findByTypeAndPmGin(@Param("type") String keyNumberType,
			@Param("gin") String pmGin);
	
	/**
	 * Retrieves the active legal entity identifier by the key number and type.
	 * 
	 * @param numero   the unique identification number
	 * @param typeList the type of identification number
	 * @return the active legal entity identifier by the key number and type
	 */
	@Query("select numId from NumeroIdent numId where numId.numero = :numero and numId.type in :types and (numId.dateFermeture is null OR numId.dateFermeture >= current_date )")
	public List<NumeroIdent> findActiveByNumeroType(@Param("numero") String numero,
			@Param("types") List<String> typeList);
}
