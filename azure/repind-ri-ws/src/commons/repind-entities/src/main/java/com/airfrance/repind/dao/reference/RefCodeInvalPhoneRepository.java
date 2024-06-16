package com.airfrance.repind.dao.reference;

import com.airfrance.repind.entity.reference.RefCodeInvalPhone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefCodeInvalPhoneRepository extends JpaRepository<RefCodeInvalPhone, Integer>{

}
