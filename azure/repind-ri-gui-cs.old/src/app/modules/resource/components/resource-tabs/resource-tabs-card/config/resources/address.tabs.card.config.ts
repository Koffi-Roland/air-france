import { ResourceTabsCardHeaderConfiguration } from '../models/resource-tabs-card-header-configuration';
import { ResourceTabsCardContentConfiguration } from '../models/resource-tabs-card-content-configuration';
import { LabelConfiguration } from '../models/label-configuration';
import { CommonService } from '../../../../../../../core/services/common.service';
import { ReferenceDataType } from '../../../../../../../shared/models/references/ReferenceDataType.enum';

export class AddressTabsCardConfiguration {

  private static labels = [
    new LabelConfiguration('', 'numberAndStreet', '', false, false),
    new LabelConfiguration(CommonService.getTransformEnumTypeStat(ReferenceDataType.COUNTRY_CODES), 'country', '', true, false)
  ];

  private static chipLabels = [
    new LabelConfiguration('', 'usagesCount', ' usages', false, false)
  ];

  // 'hc' stands for 'header configuration'
  public static hc = new ResourceTabsCardHeaderConfiguration(new LabelConfiguration('', 'type', '-TYPE', true, false), 'type');
  // 'cc' stands for 'content configuration'
  public static cc = new ResourceTabsCardContentConfiguration(AddressTabsCardConfiguration.labels, AddressTabsCardConfiguration.chipLabels);

}
