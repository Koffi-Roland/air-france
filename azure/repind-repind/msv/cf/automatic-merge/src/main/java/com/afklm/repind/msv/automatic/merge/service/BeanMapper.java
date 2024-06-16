package com.afklm.repind.msv.automatic.merge.service;

import com.afklm.repind.common.entity.contact.EmailEntity;
import com.afklm.repind.common.entity.contact.PostalAddress;
import com.afklm.repind.common.entity.contact.Telecoms;
import com.afklm.repind.common.entity.contact.UsageMedium;
import com.afklm.repind.common.entity.individual.AccountData;
import com.afklm.repind.common.entity.individual.Individu;
import com.afklm.repind.common.entity.role.RoleContract;
import com.afklm.repind.common.entity.role.RoleGP;
import com.afklm.repind.msv.automatic.merge.model.individual.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface BeanMapper {

    PostalAddress toPostalAddress(PostalAddress postalAddress);
    UsageMedium toUsageMedium(UsageMedium usageMedium);
    ModelRoleGP toModelRoleGP(RoleGP roleGP);

    @Mapping(source = "ain", target = "identifiant")
    @Mapping(source = "codeMedium", target = "type")
    @Mapping(source = "statutMedium", target = "status")
    @Mapping(source = "raisonSociale", target = "corporateName")
    @Mapping(source = "complementAdresse", target = "addressComplement")
    @Mapping(source = "noEtRue", target = "numberAndStreet")
    @Mapping(source = "localite", target = "locality")
    @Mapping(source = "ville", target = "city")
    @Mapping(source = "codePostal", target = "zipCode")
    @Mapping(source = "codePays", target = "country")
    @Mapping(source = "codeProvince", target = "state")
    @Mapping(source = "forcage", target = "forced")
    @Mapping(source = "usageMedium", target = "usages")
    @Mapping(source = "siteCreation", target = "signature.creationSite")
    @Mapping(source = "signatureCreation", target = "signature.creationSignature")
    @Mapping(source = "dateCreation", target = "signature.creationDate")
    @Mapping(source = "siteModification", target = "signature.modificationSite")
    @Mapping(source = "signatureModification", target = "signature.modificationSignature")
    @Mapping(source = "dateModification", target = "signature.modificationDate")
    ModelAddress postalAddressToModelAddress(PostalAddress postalAddress);

    @Mapping(source = "srin", target = "identifiant")
    @Mapping(source = "numeroContrat", target = "contractNumber")
    @Mapping(source = "etat", target = "status")
    @Mapping(source = "typeContrat", target = "productType")
    @Mapping(source = "sousType", target = "productSubType")
    @Mapping(source = "codeCompagnie", target = "companyCode")
    @Mapping(source = "dateFinValidite", target = "endingDate")
    @Mapping(source = "dateDebutValidite", target = "startingDate")
    @Mapping(source = "familleTraitement", target = "contractType")
    @Mapping(source = "siteCreation", target = "signature.creationSite")
    @Mapping(source = "signatureCreation", target = "signature.creationSignature")
    @Mapping(source = "dateCreation", target = "signature.creationDate")
    @Mapping(source = "siteModification", target = "signature.modificationSite")
    @Mapping(source = "signatureModification", target = "signature.modificationSignature")
    @Mapping(source = "dateModification", target = "signature.modificationDate")
    ModelContract roleContratsToModelContract(RoleContract roleContrats);

    @Mapping(source = "ain", target = "identifiant")
    @Mapping(source = "codeMedium", target = "type")
    @Mapping(source = "statutMedium", target = "status")
    @Mapping(source = "autorisationMailing", target = "authorizationMailing")
    @Mapping(source = "descriptifComplementaire", target = "description")
    @Mapping(source = "siteCreation", target = "signature.creationSite")
    @Mapping(source = "signatureCreation", target = "signature.creationSignature")
    @Mapping(source = "dateCreation", target = "signature.creationDate")
    @Mapping(source = "siteModification", target = "signature.modificationSite")
    @Mapping(source = "signatureModification", target = "signature.modificationSignature")
    @Mapping(source = "dateModification", target = "signature.modificationDate")
    ModelEmail emailToModelEmail(EmailEntity email);

    @Mapping(source = "ain", target = "identifiant")
    @Mapping(source = "codeMedium", target = "type")
    @Mapping(source = "statutMedium", target = "status")
    @Mapping(source = "normInterPhoneNumber", target = "phoneNumber")
    @Mapping(source = "numero", target = "phoneNumberNotNormalized")
    @Mapping(source = "terminal", target = "terminal")
    @Mapping(source = "normInterCountryCode", target = "countryCode")
    @Mapping(source = "siteCreation", target = "signature.creationSite")
    @Mapping(source = "signatureCreation", target = "signature.creationSignature")
    @Mapping(source = "dateCreation", target = "signature.creationDate")
    @Mapping(source = "siteModification", target = "signature.modificationSite")
    @Mapping(source = "signatureModification", target = "signature.modificationSignature")
    @Mapping(source = "dateModification", target = "signature.modificationDate")
    ModelTelecom telecomsToModelTelecom(Telecoms telecoms);

    @Mapping(source = "civilite", target = "civility")
    @Mapping(source = "statutIndividu", target = "status")
    @Mapping(source = "nom", target = "lastName")
    @Mapping(source = "prenom", target = "firstName")
    @Mapping(source = "alias", target = "lastNameAlias")
    @Mapping(source = "aliasPrenom", target = "firstNameAlias")
    @Mapping(source = "secondPrenom", target = "secondFirstName")
    @Mapping(source = "dateNaissance", target = "birthDate")
    @Mapping(source = "codeTitre", target = "title")
    @Mapping(source = "nationalite", target = "nationality")
    @Mapping(source = "autreNationalite", target = "secondNationality")
    @Mapping(source = "ginFusion", target = "ginMerged")
    @Mapping(source = "dateFusion", target = "dateMerged")
    @Mapping(source = "siteCreation", target = "signature.creationSite")
    @Mapping(source = "signatureCreation", target = "signature.creationSignature")
    @Mapping(source = "dateCreation", target = "signature.creationDate")
    @Mapping(source = "siteModification", target = "signature.modificationSite")
    @Mapping(source = "signatureModification", target = "signature.modificationSignature")
    @Mapping(source = "dateModification", target = "signature.modificationDate")
    ModelIndividual individuToModelIndividual(Individu individu);

    @Mapping(source = "codeApplication", target = "applicationCode")
    @Mapping(source = "num", target = "number")
    ModelUsageMedium usageMediumToModelUsageMedium(UsageMedium usageMedium);

    @Mapping(source = "id", target = "identifiant")
    @Mapping(source = "siteCreation", target = "signature.creationSite")
    @Mapping(source = "signatureCreation", target = "signature.creationSignature")
    @Mapping(source = "dateCreation", target = "signature.creationDate")
    @Mapping(source = "siteModification", target = "signature.modificationSite")
    @Mapping(source = "signatureModification", target = "signature.modificationSignature")
    @Mapping(source = "dateModification", target = "signature.modificationDate")
    ModelAccountData accountDataToModelAccountData(AccountData accountData);

}
