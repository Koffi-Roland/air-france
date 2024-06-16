import {ResourceDetailsCardConfiguration} from './models/resrouce-details-card-configuration';
import {AddressDetailsCardConfig} from './resources/address.details.card.config';
import {EmailDetailsCardConfig} from './resources/email.details.card.config';
import {TelecomDetailsCardConfig} from './resources/telecom.details.card.config';
import {ContractDetailsCardConfig} from './resources/contract.details.card.config';
import {CommPrefDetailsCardConfig} from './resources/comm-pref.details.card.config';
import {PreferenceDetailsCardConfig} from './resources/preference.details.card.config';
import {DelegationDetailsCardConfig} from './resources/delegation.details.card.config';
import {ExternalIDDetailsCardConfig} from './resources/externalID.details.card.config';
import {AlertDetailsCardConfig} from './resources/alert.details.card.config';
import {RoleUCCRDetailsCardConfig} from './resources/uccrrole.details.card.config';
import {InferredDetailsCardConfig} from './resources/inferred.details.card.config';
import {HandicapDetailsCardConfig} from './resources/handicap.details.card.config';
import {ResourceType} from '../../../../../shared/models/resources/resource-type';
import {ConsentDetailsCardConfig} from './resources/consent.details.card.config';
import {AccountDetailsCardConfig} from './resources/account.details.card.config';

export class ResourceDetailsCardConfigurationLoader {
  public static loadCardConfiguration(type: ResourceType, resource: any): ResourceDetailsCardConfiguration {
    switch (type) {
      case ResourceType.Address:
        return AddressDetailsCardConfig.loadAddressCardDetails(resource);
      case ResourceType.Email:
        return EmailDetailsCardConfig.loadEmailCardDetails(resource);
      case ResourceType.Telecom:
        return TelecomDetailsCardConfig.loadTelecomCardDetails(resource);
      case ResourceType.Contract:
        return ContractDetailsCardConfig.loadContractCardDetails(resource);
      case ResourceType.CommunicationPreference:
        return CommPrefDetailsCardConfig.loadCommPrefCardDetails(resource);
      case ResourceType.Preference:
        return PreferenceDetailsCardConfig.loadPreferenceCardDetails(resource);
      case ResourceType.Delegation:
        return DelegationDetailsCardConfig.loadDelegationCardDetails(resource);
      case ResourceType.ExternalIdentifier:
        return ExternalIDDetailsCardConfig.loadExternalIDCardDetails(resource);
      case ResourceType.Alert:
        return AlertDetailsCardConfig.loadAlertCardDetails(resource);
      case ResourceType.UCCRRole:
        return RoleUCCRDetailsCardConfig.loadRoleUccrCardDetails(resource);
      case ResourceType.Inferred:
        return InferredDetailsCardConfig.loadInferredCardDetails(resource);
      case ResourceType.Handicap:
        return HandicapDetailsCardConfig.loadHandicapCardDetails(resource);
      case ResourceType.Consent:
        return ConsentDetailsCardConfig.loadConsentCardDetails(resource);
      case ResourceType.Account:
        return AccountDetailsCardConfig.loadAccountCardDetailsCard(resource);
      default:
        console.error('Oops, no config ready for type !' + type);
        break;
    }
  }
}
