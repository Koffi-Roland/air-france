import { ResourceTabsCardHeaderConfiguration } from '../models/resource-tabs-card-header-configuration';
import { ResourceTabsCardContentConfiguration } from '../models/resource-tabs-card-content-configuration';
import { LabelConfiguration } from '../models/label-configuration';
import { CommonService } from '../../../../../../../core/services/common.service';
import { ReferenceDataType } from '../../../../../../../shared/models/references/ReferenceDataType.enum';

export class AccountTabsCardConfiguration {

  private static labels = [
    new LabelConfiguration('', 'identifier', '', false, false),
  ];

  private static chipLabels = [
  ];

  // 'hc' stands for 'header configuration'
  public static hc = new ResourceTabsCardHeaderConfiguration(new LabelConfiguration('ACCOUNT-', 'type', '-TYPE', true, false), 'type');
  // 'cc' stands for 'content configuration'
  public static cc = new ResourceTabsCardContentConfiguration(AccountTabsCardConfiguration.labels, AccountTabsCardConfiguration.chipLabels);

}
