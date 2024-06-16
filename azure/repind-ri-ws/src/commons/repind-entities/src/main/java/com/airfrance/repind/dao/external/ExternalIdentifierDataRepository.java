package com.airfrance.repind.dao.external;

import com.airfrance.repind.entity.external.ExternalIdentifier;
import com.airfrance.repind.entity.external.ExternalIdentifierData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExternalIdentifierDataRepository extends JpaRepository<ExternalIdentifierData, Long>, ExternalIdentifierDataRepositoryCustom {

	public ExternalIdentifierData findByIdentifierDataId(Long id);

	public List<ExternalIdentifierData> findByExternalIdentifier(ExternalIdentifier externalIdentifier);
}
