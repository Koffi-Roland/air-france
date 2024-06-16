package com.afklm.cati.common.repository;

import com.afklm.cati.common.entity.RefComPrefCountryMarket;
import com.afklm.cati.common.exception.jraf.JrafDaoException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RefComPrefCountryMarketRepository extends JpaRepository<RefComPrefCountryMarket, String> {
    /**
     * Getting Market by code pays
     *
     * @param codePays
     * @return
     * @throws JrafDaoException
     */
    @Query("select mrt.market from RefComPrefCountryMarket mrt where mrt.codePays=:codePays ")
    List<String> findMarketByCodePays(@Param("codePays") String codePays);

    Long countByCodePays(String codePays);

    List<RefComPrefCountryMarket> findByCodePaysOrderByCodePays(String codePays, Pageable pageable);

    List<RefComPrefCountryMarket> findAllByOrderByCodePays(Pageable pageable);

    @Query("select distinct mrt.market from RefComPrefCountryMarket mrt")
    List<String> findAllDistinctMarket();

    @Query("select count(refComPrefCountryMarket) from RefComPrefCountryMarket refComPrefCountryMarket where refComPrefCountryMarket.codePays = :country")
    Long countRefComPrefCountryMarket(@Param("country") String country);

    @Query("select refComPrefCountryMarket from RefComPrefCountryMarket refComPrefCountryMarket where refComPrefCountryMarket.codePays = :country order by refComPrefCountryMarket.codePays")
    List<RefComPrefCountryMarket> provideRefComPrefCountryMarketWithPagination(@Param("country") String country, Pageable pageable);
}
