import { LabelConfiguration } from '../models/label-configuration';
import { ResourceTabsCardHeaderConfiguration } from '../models/resource-tabs-card-header-configuration';
import { ResourceTabsCardContentConfiguration } from '../models/resource-tabs-card-content-configuration';
import { CommonService } from '../../../../../../../core/services/common.service';
import { ReferenceDataType } from '../../../../../../../shared/models/references/ReferenceDataType.enum';

export class ConsentTabsCardConfiguration {

  private static labels = [
    new LabelConfiguration('', 'consentDataDateConsent', '', false, false, true),
    new LabelConfiguration('ID : ', 'id', '', false, false),

  ];

  private static chipLabels = [
  ];

  // 'hc' stands for 'header configuration'
  public static hc =
    new ResourceTabsCardHeaderConfiguration(
      new LabelConfiguration(
        CommonService.getTransformEnumTypeStat(ReferenceDataType.CONSENT_DATA_TYPE)
        , 'consentDataType', '', true, false), 'consentDataIsConsent');
  // 'cc' stands for 'content configuration'
  public static cc =
    new ResourceTabsCardContentConfiguration(ConsentTabsCardConfiguration.labels, ConsentTabsCardConfiguration.chipLabels);

}
