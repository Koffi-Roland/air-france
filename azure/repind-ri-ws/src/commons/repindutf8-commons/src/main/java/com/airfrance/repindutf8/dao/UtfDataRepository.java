package com.airfrance.repindutf8.dao;

import com.airfrance.repindutf8.entity.UtfData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UtfDataRepository extends JpaRepository<UtfData, Long>, UtfDataRepositoryCustom {
	Long countByUtfUtfId(Long utfId);
	
	@Query("select utfdata.utfDataId from UtfData utfdata where utfdata.refUtfDataKey.scode = :key and utfdata.utf.utfId = :utfId")
	Long findUtfDataIdByRefUtfDataKeyScodeAndUtfUtfId(@Param("key") String key, @Param("utfId") Long utfId);
	
	Long deleteByUtfUtfIdAndRefUtfDataKeyScode(Long utfId, String scode);
}
