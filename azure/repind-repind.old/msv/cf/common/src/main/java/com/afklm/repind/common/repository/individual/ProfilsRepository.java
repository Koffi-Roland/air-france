package com.afklm.repind.common.repository.individual;

import com.afklm.repind.common.entity.individual.ProfilsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
/**
 * A repository used to fetch data about profile
 */
public interface ProfilsRepository extends JpaRepository<ProfilsEntity, Integer> {
    /**
     * @param gin The gin related to the data we want
     * @return The profile related to the gin we gave
     */
    ProfilsEntity getProfilsEntityByGin(String gin);
}
