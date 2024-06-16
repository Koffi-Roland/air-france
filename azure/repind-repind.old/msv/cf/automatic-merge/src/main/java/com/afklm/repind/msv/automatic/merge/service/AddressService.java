package com.afklm.repind.msv.automatic.merge.service;

import com.afklm.repind.common.entity.contact.PostalAddress;
import com.afklm.repind.common.entity.contact.UsageMedium;
import com.afklm.repind.common.repository.contact.PostalAddressRepository;
import com.afklm.repind.common.repository.contact.UsageMediumRepository;
import com.afklm.repind.msv.automatic.merge.model.RoleUsageMediumEnum;
import com.afklm.repind.msv.automatic.merge.model.UsageMediumEnum;
import com.afklm.repind.msv.automatic.merge.model.individual.LinkGinUsageMediumDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;


/**
 * <p>
 * Title : IndividuAllImpl.java
 * </p>
 * Service Implementation to manage IndividuAll
 * <p>
 * Copyright : Copyright (c) 2018
 * </p>
 * <p>
 * Company : AIRFRANCE-KLM
 * </p>
 */

@Service
public class AddressService {

    /**
     * logger
     */
    private static final List<String> ADDRESSES_NOT_MERGEABLE = List.of("BDC");

    @Autowired
    private PostalAddressRepository postalAddressRepository;

    @Autowired
    private UsageMediumRepository usageMediumRepository;

    @Autowired
    public BeanMapper beanMapper;

    /**
     * Find a PostalAddress by GIN and status
     *
     * @param gin    Individual ID
     * @param status Filter on the status field
     * @return A list of PostalAddress instance
     */
    public List<PostalAddress> findByGinAndStatus(String gin, List<String> status) {
        List<PostalAddress> result;

        result = postalAddressRepository.findByIndividuGinAndStatutMediumIn(gin, status);

        if (result != null && !result.isEmpty()) {
            for (PostalAddress postalAddress : result) {
                List<UsageMedium> listUsageMedium = usageMediumRepository.findUsageMediumByAinAdr(postalAddress.getAin());
                postalAddress.setUsageMedium(new HashSet<>(listUsageMedium));
            }
        }

        return result;
    }

    /**
     * Save in database a PostalAddress
     *
     * @param address an instance of PostalAddress
     * @param commit commit in database
     * @return An instance of PostalAddress after the save
     */
    public PostalAddress save(PostalAddress address, boolean commit) {
        if(commit){
            return postalAddressRepository.save(address);
        }else{
            return address;
        }
    }

    /**
     * Find a PostalAddress by this ID (sain)
     *
     * @param sain PostalAddress ID
     * @return An instance of PostalAddress
     */
    public PostalAddress findBySain(String sain) {
        PostalAddress postalAddress = postalAddressRepository.findPostalAddressByAin(sain);

        if (postalAddress != null) {
            List<UsageMedium> listUsageMedium = usageMediumRepository.findUsageMediumByAinAdr(postalAddress.getAin());
            postalAddress.setUsageMedium(new HashSet<>(listUsageMedium));
        }

        return postalAddress;
    }

    /**
     * Check if a postal address is mergeable
     *
     * @param address Instance of PostalAddress
     * @return True if there is at least one mergeable usage or if there is no usage linked to this postal address
     */
    public boolean isMergeable(PostalAddress address) {
        int nbAddressesMergeable = 0;
        for (UsageMedium usage : address.getUsageMedium()) {
            if (!ADDRESSES_NOT_MERGEABLE.contains(usage.getCodeApplication())) {
                nbAddressesMergeable++;
            }
        }

        return nbAddressesMergeable > 0 || address.getUsageMedium().isEmpty();
    }

    /**
     * Check if an postal address have minimum one usage BDC
     * @param address an instance of PostalAddress
     * @return true : if one usage BDC exist
     */
    public boolean isBDCPostalAddress(PostalAddress address) {
        for (UsageMedium usage : address.getUsageMedium()) {
            if (ADDRESSES_NOT_MERGEABLE.contains(usage.getCodeApplication())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check if two postal addresses are equals
     * @param firstAdr an instance of PostalAddress
     * @param secondAdr an instance of PostalAddress
     * @return true: first address is equal to second address
     */
    public boolean areEquals(PostalAddress firstAdr, PostalAddress secondAdr) {
        return strEquals(firstAdr.getNoEtRue(),secondAdr.getNoEtRue()) &&
                strEquals(firstAdr.getCodePays(), secondAdr.getCodePays()) &&
                strEquals(firstAdr.getCodeProvince(), secondAdr.getCodeProvince()) &&
                strEquals(firstAdr.getCodePostal(), secondAdr.getCodePostal()) &&
                strEquals(firstAdr.getStatutMedium(),secondAdr.getStatutMedium()) &&
                strEquals(firstAdr.getCodeMedium(),secondAdr.getCodeMedium());
    }

    /**
     * Check if two string are equals (support null value)
     * @param first a string
     * @param second a string
     * @return true: the both are null or the both are equals
     */
    private boolean strEquals(String first, String second){
        return ((first == null && second == null) || (first != null && first.equals(second)));
    }

    /**
     * Retrieve unique key of postal address
     * @param adr an instance of PostalAddress
     * @return a list of unique key (one key for each usage)
     */
    public List<String> retrieveUnicityKey(PostalAddress adr) {
        List<String> uniqueKeys = new ArrayList<>();
        for (UsageMedium usage : adr.getUsageMedium()) {
            String usageKey = usage.getCodeApplication();
            if (UsageMediumEnum.ISI.getName().equals(usage.getCodeApplication())) {
                usageKey = usage.getCodeApplication() + usage.getRole1();
            }
            uniqueKeys.add(usageKey + adr.getCodeMedium() + adr.getStatutMedium());
        }
        return uniqueKeys;
    }

    /**
     * Retrieve the list of usage that can be removed
     * @param adr an instance of PostalAddress
     * @return a list of usage
     */
    public List<String> getUsageToDelete(PostalAddress adr){
        List<String> usageToDelete = new ArrayList<>();
        for (UsageMedium usage : adr.getUsageMedium()) {
            if (!ADDRESSES_NOT_MERGEABLE.contains(usage.getCodeApplication())) {
                usageToDelete.add(usage.getCodeApplication());
            }
        }
        return usageToDelete;
    }

    /**
     * Remove ISI C usage if an ISI M exist in list and remove duplicated usages
     * @param ginTarget target individual ID
     * @param usages list of usages
     * @return a consistent list of usages
     */
    public Set<LinkGinUsageMediumDTO> consistencyOfUsages(String ginTarget, Set<LinkGinUsageMediumDTO> usages){
        // Remove usages with scode_application == null
        usages.removeIf(element -> element.getUsage().getCodeApplication() == null);

        // Remove ISI C if an ISI M exist
        List<LinkGinUsageMediumDTO> postalAddressesISIM = usages.stream()
                .filter(el -> UsageMediumEnum.ISI.getName().equals(el.getUsage().getCodeApplication()) && RoleUsageMediumEnum.M.getName().equals(el.getUsage().getRole1()))
                .toList();

        if(!postalAddressesISIM.isEmpty()){
            usages.removeIf(element -> UsageMediumEnum.ISI.getName().equals(element.getUsage().getCodeApplication()) && RoleUsageMediumEnum.C.getName().equals(element.getUsage().getRole1()));
        }

        // Remove usages duplicated
        for (Iterator<LinkGinUsageMediumDTO> iterator = usages.iterator(); iterator.hasNext(); ) {
            LinkGinUsageMediumDTO element = iterator.next();

            List<?> founds = usages.stream()
                    .filter(el ->  el.getUsage().getCodeApplication().equals(element.getUsage().getCodeApplication())
                                    && !el.getUsage().getRin().equals(element.getUsage().getRin()))
                    .toList();

            if(!founds.isEmpty() && !element.getGin().equals(ginTarget)) {
                iterator.remove();
            }
        }

        return usages;
    }
}
