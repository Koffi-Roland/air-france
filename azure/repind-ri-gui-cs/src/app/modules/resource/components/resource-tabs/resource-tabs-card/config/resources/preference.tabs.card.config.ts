import { Preference } from './../../../../../../../shared/models/resources/preference';
import { LabelConfiguration } from '../models/label-configuration';
import { ResourceTabsCardHeaderConfiguration } from '../models/resource-tabs-card-header-configuration';
import { ResourceTabsCardContentConfiguration } from '../models/resource-tabs-card-content-configuration';
import { CommonService } from '../../../../../../../core/services/common.service';
import { ReferenceDataType } from '../../../../../../../shared/models/references/ReferenceDataType.enum';

export class PreferenceTabsCardConfiguration {

  private static labels = [
    new LabelConfiguration(CommonService.getTransformEnumTypeStat(ReferenceDataType.PREFERENCE_TYPES), 'type', '', true, false),
    new LabelConfiguration('ID: ', 'id', '', false, false)
  ];

  private static chipLabels = [
    new LabelConfiguration('', 'preferenceDataCount', ' preference data', false, false)
  ];

  // 'hc' stands for 'header configuration'
  public static hc =
    new ResourceTabsCardHeaderConfiguration(new LabelConfiguration('', 'type', '', false, false), '');
  // 'cc' stands for 'content configuration'
  public static cc =
    new ResourceTabsCardContentConfiguration(PreferenceTabsCardConfiguration.labels, PreferenceTabsCardConfiguration.chipLabels);

  public static getCardContent(resource: Preference): ResourceTabsCardContentConfiguration {
    const labelUsed = (resource.preferenceDataCount <= 1) ? ' Preference Data' : ' Preferences Data';
    const labelConf = [new LabelConfiguration('', 'preferenceDataCount', labelUsed, false, false)];
    return new ResourceTabsCardContentConfiguration(PreferenceTabsCardConfiguration.labels, labelConf);
  }

}
