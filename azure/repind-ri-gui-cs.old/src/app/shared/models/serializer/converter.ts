import { Type } from '../common/type';
import { Status } from '../common/status';
import { SignatureType } from '../common/signatures/signature-type';
import { Signature } from '../common/signatures/signature';
import { TerminalType } from '../common/terminal-type';
import { Usage } from '../common/usage';
import { MarketLanguage } from '../common/market-language';
import { ExternalIdentifierData } from '../common/external-identifier-data';
import { PreferenceData } from '../common/preference-data';
import { AlertData } from '../common/alert-data';
import { ResourceType } from '../resources/resource-type';
import { Individual } from '../individual/individual';
import { ProfileData } from '../individual/profile-data';
import { ManagementData } from '../individual/management-data';
import { IndividualResume } from '../individual/individual-resume';
import { LastActivity } from '../common/last-activity';

/**
 * This is an utility class that has only static methods.
 * These methods are mostly used to parse the JSON response from back-end services.
 */
export class Converter {

  public static convertToAlertData(json: any): AlertData[] {
    const alertData: AlertData[] = [];
    if (json.alertdata) {
      json.alertdata.forEach((data: any) => {
        alertData.push(new AlertData(data.key, data.value));
      });
    }
    return alertData;
  }

  public static convertToIndividual(x: any, resume?: any): Individual {
    const gin: string = x.gin;
    const type: string = x.type;
    const civility: string = x.civility;
    const status: string = x.status;
    const lastname: string = x.lastName;
    const firstname: string = x.firstName;
    const lastnameAlias: string = x.lastNameAlias;
    const firstnameAlias: string = x.firstNameAlias;
    const secondFirstname: string = x.secondFirstName;
    const sexe: string = x.sexe;
    const birthdate: Date = x.birthDate;
    const title: string = x.title;
    const profile: ProfileData = this.convertToProfileData(x);
    const managementData: ManagementData = this.convertToManagementData(x);
    const individualResume: IndividualResume = (resume) ? this.convertToIndividualResume(resume) : null;
    return new Individual(gin, type, civility, status, lastname, firstname, lastnameAlias, firstnameAlias,
      secondFirstname, sexe, birthdate, title, individualResume, profile, managementData);
  }

  private static convertToIndividualResume(x: any): IndividualResume {
    const flyingBlueContractNumber: string = x.flyingBlueContractNumber;
    const flyingBlueContractsCount: number = x.flyingBlueContractsCount;
    const otherContractsCount: number = x.otherContractsCount;
    const hasFlyingBlueContract: boolean = x.hasFlyingBlueContract;
    const hasMyAccountContract: boolean = x.hasMyAccountContract;
    const personnalAddressesCount: number = x.personnalAddressesCount;
    const professionalAddressesCount: number = x.professionalAddressesCount;
    const personnalEmailsCount: number = x.personnalEmailsCount;
    const professionalEmailsCount: number = x.professionalEmailsCount;
    const personnalTelecomsCount: number = x.personnalTelecomsCount;
    const professionalTelecomsCount: number = x.professionalTelecomsCount;
    const externalIdentifiersCount: number = x.externalIdentifiersCount;
    const optInCommPrefCount: number = x.optInCommPrefCount;
    const optOutCommPrefCount: number = x.optOutCommPrefCount;
    const optInAlertsCount: number = x.optInAlertsCount;
    const optOutAlertsCount: number = x.optOutAlertsCount;
    const preferencesCount: number = x.preferencesCount;
    const delegatesCount: number = x.delegatesCount;
    const delegatorsCount: number = x.delegatorsCount;
    const UCCRRolesCount: number = x.UCCRRolesCount;
    const isAFEmployee: boolean = x.isAFEmployee;
    const isDoctor: boolean = x.isDoctor;
    const isRestrictedType: boolean = x.isRestrictedTypeIndividual;
    const hasAccountDataDeleted: boolean = x.hasAccountDataDeleted;

    return new IndividualResume(flyingBlueContractNumber, flyingBlueContractsCount, otherContractsCount,
      hasMyAccountContract, hasFlyingBlueContract, personnalAddressesCount, professionalAddressesCount,
      personnalEmailsCount, professionalEmailsCount, personnalTelecomsCount, professionalTelecomsCount,
      externalIdentifiersCount, optInCommPrefCount, optOutCommPrefCount, optInAlertsCount,
      optOutAlertsCount, preferencesCount, delegatesCount, delegatorsCount, UCCRRolesCount,
      isAFEmployee, isDoctor, isRestrictedType, hasAccountDataDeleted);
  }

  private static convertToManagementData(x: any): ManagementData {
    const mergeGIN: string = x.ginMerged;
    const mergeDate: Date = x.dateMerged;
    const signatureCreation: Signature = this.convertToSignature(SignatureType.Creation, x);
    const signatureModification: Signature = this.convertToSignature(SignatureType.Modification, x);
    const lastActivity: LastActivity = x.lastActivity;
    return new ManagementData(mergeGIN, mergeDate, signatureCreation, signatureModification,lastActivity);
  }

  private static convertToProfileData(x: any): ProfileData {
    if (!x.profilsdto) {
      return null;
    }
    const mailing: string = x.profilsdto.smailing_autorise;
    const branch: string = x.profilsdto.scode_professionnel;
    const communicationLanguage: string = x.profilsdto.scode_langue;
    return new ProfileData(mailing, branch, communicationLanguage);
  }

  public static convertToPreferenceData(json: any): PreferenceData[] {
    const preferenceData: PreferenceData[] = [];
    if (json.preferenceData) {
      json.preferenceData.forEach((y: any) => {
        const id: number = y.preferenceDataId;
        const key: string = y.key;
        const value: string = y.value;
        preferenceData.push(new PreferenceData(id, key, value));
      });
    }
    return preferenceData;
  }

  public static convertToFunctionalData(json: any): ExternalIdentifierData[] {
    const functionalData: ExternalIdentifierData[] = [];
    if (json.externalIdentifierDataList) {
      json.externalIdentifierDataList.forEach((data: any) => {
        const key: string = data.key;
        const value: string = data.value;
        const signatureCreation: Signature = this.convertToSignature(SignatureType.Creation, data);
        const signatureModification: Signature = this.convertToSignature(SignatureType.Modification, data);
        functionalData.push(new ExternalIdentifierData(key, value, signatureCreation, signatureModification));
      });
    }
    return functionalData;
  }

  public static convertToMarketLanguages(json: any, channel: string): MarketLanguage[] {
    const marketLanguages: MarketLanguage[] = [];
    if (json.marketLanguage) {
      json.marketLanguage.forEach((data: any) => {
        const market: string = data.market;
        const language: string = data.language;
        const optin: string = data.optIn;
        const consentDate: Date = data.dateOfConsent;
        const signatureCreation: Signature = this.convertToSignature(SignatureType.Creation, data);
        const signatureModification: Signature = this.convertToSignature(SignatureType.Modification, data);
        const marketLanguage = new MarketLanguage(market, language, optin, channel, consentDate, signatureCreation, signatureModification);
        marketLanguages.push(marketLanguage);
      });
    }
    return marketLanguages;
  }

  /** Common convertion operations (type, status, signature) */

  public static convertToType(x: string): Type {
    switch (x) {
      case 'P':
        return Type.Business;
      case 'D':
        return Type.Home;
      default:
        return null;
    }
  }

  public static convertToStatus(x: string): Status {
    switch (x) {
      case 'V':
        return Status.Valid;
      case 'I':
        return Status.Invalid;
      case 'H':
        return Status.Archived;
      case 'T':
        return Status.Temporary;
      case 'X':
        return Status.Deleted;
      case 'S':
        return Status.Suspended;
    }
  }

  public static convertToSignature(type: SignatureType, x: any): Signature {
    switch (type) {
      case SignatureType.Creation:
        return this.convertToCreationSignature(x);
      case SignatureType.Modification:
        return this.convertToModificationSignature(x);
      default:
        return null;
    }
  }

  private static convertToCreationSignature(x: any): Signature {
    if (!x.signature) {
      return null;
    }
    const signature: string = x.signature.creationSignature;
    const site: string = x.signature.creationSite;
    const date: Date = x.signature.creationDate;
    return new Signature(SignatureType.Creation, signature, site, date);
  }

  private static convertToModificationSignature(x: any): Signature {
    if (!x.signature) {
      return null;
    }
    const signature: string = x.signature.modificationSignature;
    const site: string = x.signature.modificationSite;
    const date: Date = x.signature.modificationDate;
    return new Signature(SignatureType.Modification, signature, site, date);
  }

  /** Specific convertion operations */

  public static convertToTerminalType(x: string): TerminalType {
    switch (x) {
      case 'F':
        return TerminalType.Fax;
      case 'M':
        return TerminalType.Mobile;
      default:
        return TerminalType.Phone;
    }
  }

  public static convertToUsages(x: any): Usage[] {
    const usages: Usage[] = [];
    if (x.usages) {
      x.usages.forEach((usage: any) => {
        usages.push(this.convertToUsage(usage));
      });
    }
    return usages;
  }

  private static convertToUsage(usage: any): Usage {
    const applicationCode: string = usage.applicationCode;
    const number: number = usage.number;
    const role1: string = usage.role1 ? usage.role1 : '';
    const role2: string = usage.role2 ? usage.role2 : '';
    const role3: string = usage.role3 ? usage.role3 : '';
    const role4: string = usage.role4 ? usage.role4 : '';
    const role5: string = usage.role5 ? usage.role5 : '';
    return new Usage(applicationCode, number, role1, role2, role3, role4, role5);
  }

  public static convertToResourceType(str: string): ResourceType {
    switch (str) {
      case 'contracts': return ResourceType.Contract;
      case 'addresses': return ResourceType.Address;
      case 'emails': return ResourceType.Email;
      case 'telecoms': return ResourceType.Telecom;
      case 'externalIdentifiers': return ResourceType.ExternalIdentifier;
      case 'communicationPreferences': return ResourceType.CommunicationPreference;
      case 'alerts': return ResourceType.Alert;
      case 'preferences': return ResourceType.Preference;
      case 'delegations': return ResourceType.Delegation;
      case 'uccrRoles': return ResourceType.UCCRRole;
    }
  }

}
