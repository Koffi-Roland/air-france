import { ResourceTabsConfiguration } from './models/resource-tabs-configuration';
import { ResourceType } from '../../../../../shared/models/resources/resource-type';
import { CommonService } from '../../../../../core/services/common.service';
import { ReferenceDataType } from '../../../../../shared/models/references/ReferenceDataType.enum';

export class ResourceTabsConfigurationLoader {

  public static loadTabsConfig(type: ResourceType, resources: Array<any>): Array<ResourceTabsConfiguration> {
    switch (type) {
      case ResourceType.Address:
        return this.loadTabsConfiguration(resources, 'status', '', '-STATUS-ADDRESSES-TAB-LABEL');
      case ResourceType.Email:
        return this.loadTabsConfiguration(resources, 'status', '', '-STATUS-EMAILS-TAB-LABEL');
      case ResourceType.Telecom:
        return this.loadTabsConfiguration(resources, 'status', '', '-STATUS-TELECOMS-TAB-LABEL');
      case ResourceType.Contract:
        return this.loadTabsConfiguration(resources, 'productType', 'PRODUCT-TYPE-', '');
      case ResourceType.CommunicationPreference:
        return this.loadTabsConfiguration(resources, 'domain', CommonService.getTransformEnumTypeStat(ReferenceDataType.COMM_PREF_DOMAIN), '');
      case ResourceType.Preference:
        return this.loadTabsConfiguration(resources, 'type', CommonService.getTransformEnumTypeStat(ReferenceDataType.PREFERENCE_TYPES),
        '');
      case ResourceType.Delegation:
        return this.loadTabsConfiguration(resources, 'type', '', '-DELEGATION-TYPE');
      case ResourceType.ExternalIdentifier:
        return this.loadTabsConfiguration(resources, 'type', '', '-EXTERNAL-IDENTIFIER-TYPE');
      case ResourceType.Alert:
        return this.loadTabsConfiguration(resources, 'type', '', '-ALERT-TYPE');
      case ResourceType.UCCRRole:
        return this.loadTabsConfiguration(resources, 'type', '', '-ROLE-UCCR-TYPE');
      case ResourceType.Inferred:
        return this.loadTabsConfiguration(resources, 'type', '', '-INFERRED-TYPE');
      case ResourceType.Handicap:
        return this.loadTabsConfiguration(resources, 'type', CommonService.getTransformEnumTypeStat(ReferenceDataType.HANDICAP_TYPE), '');
      case ResourceType.Consent:
        return this.loadTabsConfiguration(resources, 'type',  CommonService.getTransformEnumTypeStat(ReferenceDataType.CONSENT_TYPE), '');
      default:
        console.error('Oops, no config ready!');
        break;
    }
  }

  /**
   * Load the configuration for tabs by giving these parameters
   * @param resources the array of resources
   * @param attribute the attribute on which we wanna make groups
   * @param labelPrefix (for traduction) the prefix
   * @param labelSuffix (for traduction) the suffix
   * @param displayAll if there are no groups set this parameter to `true`
   * @param label if there are no groups set this parameter to `the label name` (in i18n file)
   */
  public static loadTabsConfiguration(resources: any[], attribute: string,
    labelPrefix: string, labelSuffix: string, displayAll?: boolean, label?: string) {

      const prefixes = [];
      const config = [];

      // No group specified
      if (displayAll) {
        config.push(new ResourceTabsConfiguration(`${label}`, resources));
        return config;
      }

      resources.forEach((data) => {
        const prefix = data[attribute];
        if (prefixes.indexOf(prefix) === -1) { prefixes.push(prefix); }
      });

      prefixes.forEach((prefix: string) => {
        const filteredData = resources.filter((data: any) => data[attribute] === prefix);
        config.push(new ResourceTabsConfiguration(`${labelPrefix}${prefix}${labelSuffix}`, filteredData));
      });

      return config;
    }
}
