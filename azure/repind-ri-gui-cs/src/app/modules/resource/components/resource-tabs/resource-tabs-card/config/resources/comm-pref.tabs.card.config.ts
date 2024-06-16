import { LabelConfiguration } from '../models/label-configuration';
import { ResourceTabsCardHeaderConfiguration } from '../models/resource-tabs-card-header-configuration';
import { ResourceTabsCardContentConfiguration } from '../models/resource-tabs-card-content-configuration';
import { CommonService } from '../../../../../../../core/services/common.service';
import { ReferenceDataType } from '../../../../../../../shared/models/references/ReferenceDataType.enum';

export class CommunicationPreferenceTabsCardConfiguration {

  private static labels = [
    new LabelConfiguration(CommonService.getTransformEnumTypeStat(ReferenceDataType.COMM_PREF_TYPE), 'type', '', true, false)
  ];

  private static chipLabels = [
    new LabelConfiguration('', 'marketLanguagesCount', ' market languages', false, false)
  ];

  // 'hc' stands for 'header configuration'
  public static hc =
    new ResourceTabsCardHeaderConfiguration(new LabelConfiguration(CommonService.getTransformEnumTypeStat(ReferenceDataType.COMM_PREF_DOMAIN), 'domain', '', true, false), 'subscribe');
  // 'cc' stands for 'content configuration'
  public static cc =
    new ResourceTabsCardContentConfiguration(CommunicationPreferenceTabsCardConfiguration.labels,
      CommunicationPreferenceTabsCardConfiguration.chipLabels);

}
