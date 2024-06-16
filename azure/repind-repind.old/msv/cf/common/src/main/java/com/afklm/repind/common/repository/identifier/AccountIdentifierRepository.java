package com.afklm.repind.common.repository.identifier;

import com.afklm.repind.common.entity.identifier.AccountIdentifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
/**
 * A repository used to fetch data about Account Identifier
 */
public interface AccountIdentifierRepository extends JpaRepository<AccountIdentifier, Integer> {
    /**
     * @param email The mail related to the data we want
     * @return The account identifier related to our emailIdentifier
     */
    AccountIdentifier findByEmailIdentifier(String email);

    /**
     * @param fbIdentifier The FbIdentifier related to the data we want
     * @return The account identifier related to our FbIdentifier
     */
    AccountIdentifier findByFbIdentifier(String fbIdentifier);

    /**
     * @param gin The gin related to the data we want
     * @return The account identifier related to our gin
     */
    AccountIdentifier findBySgin(String gin);

    /**
     * @param gin The gin related to the data we want
     * @return The account identifier related to our gin
     */
    Optional<AccountIdentifier> getBySgin(String gin);
}
