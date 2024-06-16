package com.afklm.repind.common.repository.contact;

import com.afklm.repind.common.entity.contact.Telecoms;
import com.afklm.repind.common.entity.individual.Individu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
/**
 * It's a repository designed to fetch data about telecom
 */
public interface TelecomsRepository extends JpaRepository<Telecoms, String> {
    /**
     * @param gin The gin related to the data we want
     * @param statutMedium The statut medium wanted in all the telecom of the response
     * @param codeMedium The code medium wanted in all the telecom of the response
     * @return A list of telecom who validate all the three precedent criteria
     */
    List<Telecoms> getTelecomsByIndividuGinAndStatutMediumAndCodeMediumOrderByDateModificationDesc(String gin, String statutMedium, String codeMedium);

    /**
     * @param gin The gin related to the data we want
     * @return A list of telecom related to the gin we gave + their statutMedium
     */
    List<Telecoms> findByIndividuGinAndStatutMediumIn(String gin, List<String> status);

    /**
     * This method retrieve email and mailing data for a given gin + their code + their statutMedium
     * @param gin The gin related to the data we want
     * @param code The code medium related to the data we want
     * @param status The statut medium related to the data we want
     * @param terminal The terminal type related to the data we want
     * @return Some data about email and mailing
     */
    List<Telecoms> findByIndividuGinAndCodeMediumAndStatutMediumAndTerminal(String gin, String code, String status, String terminal);

    List<Telecoms> findTelecomsByIndividuGinAndStatutMediumInAndCodeMediumIn(String gin, List<String> status, List<String> medium);

    List<Telecoms> findTelecomsByIndividuGinAndStatutMediumIn(String gin, List<String> status);

    List<Telecoms> findByIndividu(Individu individu);

    List<Telecoms> findByIndividuGin(String gin);
}
