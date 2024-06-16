package com.afklm.rigui.dao.adresse;

import com.afklm.rigui.entity.adresse.PostalAddressToNormalize;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostalAddressToNormalizeRepository extends JpaRepository<PostalAddressToNormalize, String> {
	
}
