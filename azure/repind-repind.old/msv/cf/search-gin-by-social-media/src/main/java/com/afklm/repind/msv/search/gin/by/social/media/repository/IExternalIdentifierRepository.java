package com.afklm.repind.msv.search.gin.by.social.media.repository;


import com.afklm.repind.msv.search.gin.by.social.media.entity.ExternalIdentifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface IExternalIdentifierRepository extends   JpaRepository<ExternalIdentifier, String> {

    Collection<ExternalIdentifier> findByIdentifierAndType(String iIdentifier , String iType);

}
