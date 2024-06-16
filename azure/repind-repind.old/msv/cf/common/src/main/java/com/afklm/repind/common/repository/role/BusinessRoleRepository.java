package com.afklm.repind.common.repository.role;

import com.afklm.repind.common.entity.role.BusinessRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
public interface BusinessRoleRepository extends JpaRepository<BusinessRole, Integer> {

    List<BusinessRole> findBusinessRolesByGinInd(String gin);

    Optional<BusinessRole> findById(Integer id);

    BusinessRole findByNumeroContrat(String cin);
}
