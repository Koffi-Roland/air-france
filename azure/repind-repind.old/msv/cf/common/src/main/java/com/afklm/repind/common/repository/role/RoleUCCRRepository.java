package com.afklm.repind.common.repository.role;


import com.afklm.repind.common.entity.role.RoleUCCR;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
/*
 * A repository used to fetch data about the role uccr
 */
public interface RoleUCCRRepository  extends JpaRepository<RoleUCCR, Long> {
    /**
     * @param cleRole The identifier related to the data we want
     * @return The role uccr related to our cleRole
     */
    RoleUCCR findByCleRole(int cleRole);
}
