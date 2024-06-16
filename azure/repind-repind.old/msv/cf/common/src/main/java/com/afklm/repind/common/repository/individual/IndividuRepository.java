package com.afklm.repind.common.repository.individual;

import com.afklm.repind.common.entity.individual.Individu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
/**
 * A repository used to fetch data about individual
 */
public interface IndividuRepository extends JpaRepository<Individu, String> {
    /**
     * @param gin The gin related to the data we want
     * @return The individual related to our gin
     */
    Individu getIndividuByGin(String gin);

    @Query("select i from Individu i "
            + "where i.gin = :gin "
            + "and i.type not in ('F', 'H') ")
    Individu getIndividualOrProspectByGinExceptForgotten(@Param("gin") String gin);
}
