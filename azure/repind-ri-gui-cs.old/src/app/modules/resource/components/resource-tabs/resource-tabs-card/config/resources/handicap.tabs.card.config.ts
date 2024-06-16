import { LabelConfiguration } from '../models/label-configuration';
import { ResourceTabsCardHeaderConfiguration } from '../models/resource-tabs-card-header-configuration';
import { ResourceTabsCardContentConfiguration } from '../models/resource-tabs-card-content-configuration';
import { CommonService } from '../../../../../../../core/services/common.service';
import { ReferenceDataType } from '../../../../../../../shared/models/references/ReferenceDataType.enum';

export class HandicapTabsCardConfiguration {

  private static labels = [
    new LabelConfiguration(CommonService.getTransformEnumTypeStat(ReferenceDataType.HANDICAP_TYPE), 'type', '', true, false),
    new LabelConfiguration(CommonService.getTransformEnumTypeStat(ReferenceDataType.HANDICAP_CODE), 'code', '', true, false)
  ];

  private static chipLabels = [
    new LabelConfiguration('', 'handicapDataCount', ' HDC data', false, false)
  ];

  // 'hc' stands for 'header configuration'
  public static hc =
    new ResourceTabsCardHeaderConfiguration(new LabelConfiguration(''
    , 'type', '', false, false), 'status');
  // 'cc' stands for 'content configuration'
  public static cc =
    new ResourceTabsCardContentConfiguration(HandicapTabsCardConfiguration.labels, HandicapTabsCardConfiguration.chipLabels);

}
