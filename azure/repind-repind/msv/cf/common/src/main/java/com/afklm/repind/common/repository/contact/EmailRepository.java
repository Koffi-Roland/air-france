package com.afklm.repind.common.repository.contact;

import com.afklm.repind.common.entity.contact.EmailEntity;
import com.afklm.repind.common.entity.individual.Individu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
/**
 * It's a repository used to fetch data about email and mailing
 */
public interface EmailRepository extends JpaRepository<EmailEntity, String> {
    /**
     * This method retrieve email and mailing data for a given gin + their statutMedium
     * @param gin The gin related to the data we want
     * @return Some data about email and mailing + their statutMedium
     */
    List<EmailEntity> findByIndividuGinAndStatutMediumIn(String gin, List<String> status);

    /**
     * This method retrieve email and mailing data for a given gin + their code + their statutMedium
     * @param gin The gin related to the data we want
     * @param code The code medium related to the data we want
     * @param status The statut medium related to the data we want
     * @return Some data about email and mailing
     */
    List<EmailEntity> findByIndividuGinAndCodeMediumAndStatutMedium(String gin, String code, String status);

    List<EmailEntity> findEmailEntitiesByIndividuGinAndStatutMediumIn(String gin, List<String> status);

    List<EmailEntity> findByIndividu(Individu individu);

    List<EmailEntity> findByIndividuGin(String gin);

    /*
  RULES CustomCAD:
   -- no invalid email
 */
    @Query("SELECT CASE WHEN COUNT(e) > 0 THEN false ELSE true END FROM EmailEntity e "
            + "where e.individu.gin = :gin and e.codeMedium='D' and e.statutMedium ='V' ")
    boolean checkEmailIsDirectAndValid(@Param("gin") String gin);
}
