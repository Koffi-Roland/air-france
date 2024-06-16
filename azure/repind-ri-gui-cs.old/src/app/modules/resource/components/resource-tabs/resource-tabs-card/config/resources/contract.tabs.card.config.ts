import { LabelConfiguration } from '../models/label-configuration';
import { ResourceTabsCardHeaderConfiguration } from '../models/resource-tabs-card-header-configuration';
import { ResourceTabsCardContentConfiguration } from '../models/resource-tabs-card-content-configuration';

export class ContractTabsCardConfiguration {

  private static labels = [
    new LabelConfiguration('', 'contractNumber', '', false, false)
  ];

  private static chipLabels = [
  ];

  // 'hc' stands for 'header configuration'
  public static hc =
    new ResourceTabsCardHeaderConfiguration(new LabelConfiguration('PRODUCT-TYPE-', 'productType', '', true, false), 'type');
  // 'cc' stands for 'content configuration'
  public static cc =
    new ResourceTabsCardContentConfiguration(ContractTabsCardConfiguration.labels, ContractTabsCardConfiguration.chipLabels);

}
