import { SectionCardContent } from '../models/section-card-content';
import { AddressesConfig } from '../addresses.config';
import { AlertsConfig } from '../alerts.config';
import { CommunicationPreferencesConfig } from '../communication-pref.config';
import { ContractsConfig } from '../contracts.config';
import { DelegationsConfig } from '../delegations.config';
import { EmailsConfig } from '../emails.config';
import { ExternalIdentifiersConfig } from '../external-id.config';
import { PreferencesConfig } from '../preferences.config';
import { TelecomsConfig } from '../telecoms.config';
import { UCCRRolesConfig } from '../uccrroles.config';
import { IndividualResume } from '../../../../../../shared/models/individual/individual-resume';
import { ResourceType } from '../../../../../../shared/models/resources/resource-type';

export class SectionCardConfigurationLoader {

  public static load(
    type: ResourceType,
    resume: IndividualResume
  ): SectionCardContent {
    switch (type) {
      case ResourceType.Address:
        return AddressesConfig.config(resume);
      case ResourceType.Alert:
        return AlertsConfig.config(resume);
      case ResourceType.CommunicationPreference:
        return CommunicationPreferencesConfig.config(resume);
      case ResourceType.Contract:
        return ContractsConfig.config(resume);
      case ResourceType.Delegation:
        return DelegationsConfig.config(resume);
      case ResourceType.Email:
        return EmailsConfig.config(resume);
      case ResourceType.ExternalIdentifier:
        return ExternalIdentifiersConfig.config(resume);
      case ResourceType.Preference:
        return PreferencesConfig.config(resume);
      case ResourceType.Telecom:
        return TelecomsConfig.config(resume);
      case ResourceType.UCCRRole:
        return UCCRRolesConfig.config(resume);
    }
  }
}
