
import { ResourceTabsCardContent } from './models/resource-tabs-card-content';
import { AddressTabsCardConfiguration } from './resources/address.tabs.card.config';
import { EmailTabsCardConfiguration } from './resources/email.tabs.card.config';
import { TelecomTabsCardConfiguration } from './resources/telecom.tabs.card.config';
import { ContractTabsCardConfiguration } from './resources/contract.tabs.card.config';
import { CommunicationPreferenceTabsCardConfiguration } from './resources/comm-pref.tabs.card.config';
import { PreferenceTabsCardConfiguration } from './resources/preference.tabs.card.config';
import { DelegationTabsCardConfiguration } from './resources/delegation.tabs.card.config';
import { ExternalIDTabsCardConfiguration } from './resources/externalID.tabs.card.config';
import { AlertTabsCardConfiguration } from './resources/alert.tabs.card.config';
import { UCCRRoleTabsCardConfiguration } from './resources/uccrrole.tabs.card.config';
import { InferredTabsCardConfiguration } from './resources/inferred.tabs.card.config';
import { HandicapTabsCardConfiguration } from './resources/handicap.tabs.card.config';
import { ResourceTabsCardConfiguration } from './models/resource-tabs-card-configuration';
import { ResourceTabsCardHeaderConfiguration } from './models/resource-tabs-card-header-configuration';
import { ResourceTabsCardContentConfiguration } from './models/resource-tabs-card-content-configuration';
import { ResourceTabsCardHeader } from './models/resource-tabs-card-header';
import { AttributeIcon } from './models/attribute-icon';
import { Label } from './models/label';
import { LabelConfiguration } from './models/label-configuration';
import { Usage } from '../../../../../../shared/models/common/usage';
import { ResourceType } from '../../../../../../shared/models/resources/resource-type';
import { ConsentTabsCardConfiguration } from './resources/consent.tabs.card.config';

export class ResourceTabsCardConfigurationLoader {

  private static attributeIcons = [
    new AttributeIcon('type', 'D', 'home'),
    new AttributeIcon('type', 'P', 'business'),
    new AttributeIcon('subscribe', 'Y', 'check'),
    new AttributeIcon('subscribe', 'N', 'not_interested'),
    new AttributeIcon('optin', 'Y', 'check'),
    new AttributeIcon('optin', 'N', 'not_interested'),
    new AttributeIcon('status', 'C', 'phonelink_setup'),
    new AttributeIcon('status', 'V', 'check'),
    new AttributeIcon('status', 'I', 'phonelink_erase'),
    new AttributeIcon('status', 'R', 'not_interested'),
    new AttributeIcon('status', 'X', 'delete'),
    new AttributeIcon('consentDataIsConsent', 'Y', 'check'),
    new AttributeIcon('consentDataIsConsent', 'N', 'not_interested'),
    new AttributeIcon('consentDataIsConsent', 'P', 'hourglass_empty'),
  ];

  public static loadContent(type: ResourceType, resource: any): ResourceTabsCardConfiguration {
    switch (type) {
      case ResourceType.Address:
        return this.configuration(resource, AddressTabsCardConfiguration.hc, AddressTabsCardConfiguration.cc);
      case ResourceType.Email:
        return this.configuration(resource, EmailTabsCardConfiguration.hc, EmailTabsCardConfiguration.cc);
      case ResourceType.Telecom:
        return this.configuration(resource, TelecomTabsCardConfiguration.hc, TelecomTabsCardConfiguration.cc);
      case ResourceType.Contract:
        return this.configuration(resource, ContractTabsCardConfiguration.hc, ContractTabsCardConfiguration.cc);
      case ResourceType.CommunicationPreference:
        const header = CommunicationPreferenceTabsCardConfiguration.hc;
        const content = CommunicationPreferenceTabsCardConfiguration.cc;
        return this.configuration(resource, header, content);
      case ResourceType.Preference:
        return this.configuration(resource, PreferenceTabsCardConfiguration.hc, PreferenceTabsCardConfiguration.getCardContent(resource));
      case ResourceType.Delegation:
        return this.configuration(resource, DelegationTabsCardConfiguration.hc, DelegationTabsCardConfiguration.cc);
      case ResourceType.ExternalIdentifier:
        return this.configuration(resource, ExternalIDTabsCardConfiguration.hc, ExternalIDTabsCardConfiguration.cc);
      case ResourceType.Alert:
        return this.configuration(resource, AlertTabsCardConfiguration.hc, AlertTabsCardConfiguration.cc);
      case ResourceType.UCCRRole:
        return this.configuration(resource, UCCRRoleTabsCardConfiguration.hc, UCCRRoleTabsCardConfiguration.cc);
      case ResourceType.Inferred:
        return this.configuration(resource, InferredTabsCardConfiguration.hc, InferredTabsCardConfiguration.cc);
      case ResourceType.Handicap:
        return this.configuration(resource, HandicapTabsCardConfiguration.hc, HandicapTabsCardConfiguration.cc);
      case ResourceType.Consent:
        return this.configuration(resource, ConsentTabsCardConfiguration.hc, ConsentTabsCardConfiguration.cc);
      default:
        console.error('Oops, no config ready!');
        break;
    }
  }

  private static configuration(resource: any, headerConfig: ResourceTabsCardHeaderConfiguration,
    contentConfig: ResourceTabsCardContentConfiguration): ResourceTabsCardConfiguration {
    const header = this.header(resource, headerConfig);
    const content = this.content(resource, contentConfig);
    return new ResourceTabsCardConfiguration(header, content);
  }

  private static header(resource: any, config: ResourceTabsCardHeaderConfiguration): ResourceTabsCardHeader {
    const icon = this.headerIcon(resource, config.iconAttribute);
    const label = this.headerTitle(resource, config.titleLabelConfiguration);
    return new ResourceTabsCardHeader(icon, label, resource.backgroundColor);
  }

  private static headerTitle(resource: any, labelConfig: LabelConfiguration): Label {
    return new Label(resource, labelConfig);
  }

  private static headerIcon(resource: any, attr: string): string {
    const filteredAttributeIcons = this.attributeIcons.filter((attrIcon: AttributeIcon) => attrIcon.attr === attr);
    if (filteredAttributeIcons.length === 0) { return attr; }
    let icon = '';
    filteredAttributeIcons.forEach((attrIcon: AttributeIcon) => {
      if (attrIcon.key === resource[attr]) {
        icon = attrIcon.value;
        return icon;
      }
    });
    return icon;
  }

  private static content(resource: any, config: ResourceTabsCardContentConfiguration): ResourceTabsCardContent {
    const labels = this.contentLabels(resource, config.labelConfigurations);
    const chips = this.contentChipLabels(resource, config.chipLabelConfigurations);
    return new ResourceTabsCardContent(labels, chips);
  }

  private static contentLabels(resource: any, labelConfigurations: LabelConfiguration[]): Label[] {
    const labels = [];
    labelConfigurations.forEach((config: LabelConfiguration) => {
      labels.push(new Label(resource, config));
    });
    return labels;
  }

  private static contentChipLabels(resource: any, chipLabelConfigurations: LabelConfiguration[]): Label[] {
    const chips = [];
    chipLabelConfigurations.forEach((config: LabelConfiguration) => {
      if (config.hasMultipleValues) {
        const labels = this.manageMultipleValues(resource, config.resourceAttribute);
        labels.forEach((label: Label) => {
          chips.push(label);
        });
      } else {
        chips.push(new Label(resource, config));
      }
    });
    return chips;
  }

  private static manageMultipleValues(resource: any, attr: string): Label[] {
    const values = resource[attr];
    const labels = [];
    values.forEach((val: Usage) => {
      const labelConfig = new LabelConfiguration(val.toString(), '', '', false, false);
      labels.push(new Label(resource, labelConfig));
    });
    return labels;
  }

}
