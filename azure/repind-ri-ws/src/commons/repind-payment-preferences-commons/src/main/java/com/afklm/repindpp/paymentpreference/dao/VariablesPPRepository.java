package com.afklm.repindpp.paymentpreference.dao;

import com.afklm.repindpp.paymentpreference.entity.EnvVarPp;
import com.afklm.repindpp.paymentpreference.entity.EnvVarPpId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VariablesPPRepository extends JpaRepository<EnvVarPp, EnvVarPpId> {
	Optional<EnvVarPp> findByIdEnvKeyPp(String key);
}
