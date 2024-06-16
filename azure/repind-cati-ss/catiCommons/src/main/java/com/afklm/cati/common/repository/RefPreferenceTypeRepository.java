package com.afklm.cati.common.repository;

import com.afklm.cati.common.entity.RefPreferenceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RefPreferenceTypeRepository extends JpaRepository<RefPreferenceType, String> {

    public List<RefPreferenceType> findByCode(String code);

    List<RefPreferenceType> findAllByOrderByCode();

}
