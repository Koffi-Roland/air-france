package com.afklm.rigui.dao.external;

import com.afklm.rigui.entity.external.ExternalIdentifier;
import com.afklm.rigui.entity.external.ExternalIdentifierData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExternalIdentifierDataRepository extends JpaRepository<ExternalIdentifierData, Long> {
	public List<ExternalIdentifierData> findByExternalIdentifier(ExternalIdentifier externalIdentifier);
}
