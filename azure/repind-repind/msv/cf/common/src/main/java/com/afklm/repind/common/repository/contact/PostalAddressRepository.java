package com.afklm.repind.common.repository.contact;

import com.afklm.repind.common.entity.contact.PostalAddress;
import com.afklm.repind.common.entity.individual.Individu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostalAddressRepository extends JpaRepository<PostalAddress, String> {
    List<PostalAddress> findByIndividuGinAndStatutMediumIn(String gin, List<String> status);

    PostalAddress findPostalAddressByAin(String ain);

    List<PostalAddress> findByIndividu(Individu individu);

    List<PostalAddress> findByIndividuGin(String gin);
}
