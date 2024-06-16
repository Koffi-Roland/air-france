package com.afklm.repind.msv.search.gin.by.email.repository;

import com.afklm.repind.common.entity.contact.EmailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface IEmailRepository extends JpaRepository<EmailEntity, String>  {
    Collection<EmailEntity> findByEmailAndStatutMediumIn(String iEmail , Collection<String> iStatus);
}
