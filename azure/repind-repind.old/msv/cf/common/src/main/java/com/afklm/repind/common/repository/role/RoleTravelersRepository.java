package com.afklm.repind.common.repository.role;


import com.afklm.repind.common.entity.role.RoleTravelers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
/*
 * A repository used to fetch data about the role travelers
 */
public interface RoleTravelersRepository extends JpaRepository<RoleTravelers, Long> {
    /**
     * @param cleRole The identifier related to the data we want
     * @return The role travelers related to our cleRole
     */
    RoleTravelers findByCleRole(int cleRole);
}
