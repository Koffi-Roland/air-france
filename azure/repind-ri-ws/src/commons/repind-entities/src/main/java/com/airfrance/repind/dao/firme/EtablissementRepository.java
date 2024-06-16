package com.airfrance.repind.dao.firme;

import com.airfrance.repind.entity.firme.Etablissement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface EtablissementRepository extends JpaRepository<Etablissement, String>, EtablissementRepositoryCustom {

    @Query("SELECT DISTINCT et FROM Etablissement et INNER JOIN FETCH et.pmZones pmz INNER JOIN FETCH pmz.zoneDecoup zd WHERE TYPE(zd) = ZoneComm AND pmz.lienPrivilegie = :isPrimary AND (pmz.dateFermeture IS NULL OR pmz.dateFermeture >= :today) AND zd.zc1 = :zc1 AND zd.zc2 = :zc2 AND (:zc3 IS NULL OR zd.zc3 = :zc3) AND (:zc4 IS NULL OR zd.zc4 = :zc4) AND (:zc5 IS NULL OR zd.zc5 = :zc5)")
    List<Etablissement> searchCompanyByZc(@Param("isPrimary") String isPrimary, @Param("zc1") String zc1, @Param("zc2") String zc2, @Param("zc3") String zc3, @Param("zc4") String zc4, @Param("zc5") String zc5, @Param("today") Date today);

}
