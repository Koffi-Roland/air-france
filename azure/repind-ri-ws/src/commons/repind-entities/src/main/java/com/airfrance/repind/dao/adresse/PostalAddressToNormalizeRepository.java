package com.airfrance.repind.dao.adresse;

import com.airfrance.repind.entity.adresse.PostalAddressToNormalize;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostalAddressToNormalizeRepository extends JpaRepository<PostalAddressToNormalize, String> {
	
}
