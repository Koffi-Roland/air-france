package com.afklm.rigui.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.afklm.rigui.entity.individu.IndividuLight;

public interface IndividuAllRepository extends JpaRepository<IndividuLight, String> {

	public List<IndividuLight> findBySgin(String gin);

}
