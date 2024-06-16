package com.airfrance.repind.dao.external;

import com.airfrance.repind.entity.external.ExternalIdentifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExternalIdentifierRepository extends JpaRepository<ExternalIdentifier, Long>, ExternalIdentifierRepositoryCustom {
	
	List<ExternalIdentifier> findByTypeAndIdentifier(String type, String identifier);
	
	@Query("select ext from ExternalIdentifier ext left outer join ext.externalIdentifierDataList dat "
			+ "where ext.gin= :gin and ext.type = :type and (dat.key = :key and dat.value = :value)")
	public List<ExternalIdentifier> findValidExternalByGinAndTypeAndKeyAndValue(@Param("gin") String gin,
			@Param("type") String type, @Param("key") String key, @Param("value") String value);

	@Query("select count(1) from ExternalIdentifier ext left outer join ext.externalIdentifierDataList dat "
			+ "where ext.gin= :gin and ext.type = :type and (dat.key = :key and dat.value = :value)")
	public int getValidExternalIdentifiersCount(@Param("gin") String gin, @Param("type") String type,
			@Param("key") String key, @Param("value") String value);

	public List<ExternalIdentifier> findByGinAndTypeOrderByModificationDateAsc(String gin, String type);

	public List<ExternalIdentifier> findByGin(String gin);

	Long countByGinAndType(String gin, String type);
}
