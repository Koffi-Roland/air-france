import { LabelConfiguration } from '../models/label-configuration';
import { ResourceTabsCardHeaderConfiguration } from '../models/resource-tabs-card-header-configuration';
import { ResourceTabsCardContentConfiguration } from '../models/resource-tabs-card-content-configuration';

export class DelegationTabsCardConfiguration {

  private static labels = [
    new LabelConfiguration('', 'delegatorCivilityAndLastName', '', false, false),
    new LabelConfiguration('', 'delegateCivilityAndLastName', '', false, false)
  ];

  private static chipLabels = [
    new LabelConfiguration('', 'sender', '', false, false)
  ];

  // 'hc' stands for 'header configuration'
  public static hc =
    new ResourceTabsCardHeaderConfiguration(new LabelConfiguration('', 'status', '-DELEGATION-STATUS', true, false), '');
  // 'cc' stands for 'content configuration'
  public static cc =
    new ResourceTabsCardContentConfiguration(DelegationTabsCardConfiguration.labels, DelegationTabsCardConfiguration.chipLabels);

}
