package com.afklm.repind.msv.search.gin.by.lastname.firstname.repository;

import com.afklm.repind.msv.search.gin.by.lastname.firstname.entity.Individu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IndividuRepository extends JpaRepository<Individu, Long> {

    List<Individu> findByNomAndPrenomAndStatutIndividuIn(String nom,  String prenom, List<String> status);

}