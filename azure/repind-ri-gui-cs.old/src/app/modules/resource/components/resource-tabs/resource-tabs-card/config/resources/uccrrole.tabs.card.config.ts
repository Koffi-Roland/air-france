import { LabelConfiguration } from '../models/label-configuration';
import { ResourceTabsCardHeaderConfiguration } from '../models/resource-tabs-card-header-configuration';
import { ResourceTabsCardContentConfiguration } from '../models/resource-tabs-card-content-configuration';

export class UCCRRoleTabsCardConfiguration {

  private static labels = [
    new LabelConfiguration('Type: ', 'type', '', false, false),
    new LabelConfiguration('CEID: ', 'ceid', '', false, false)
  ];

  private static chipLabels = [
  ];

  // 'hc' stands for 'header configuration'
  public static hc =
    new ResourceTabsCardHeaderConfiguration(new LabelConfiguration('', 'type', '-ROLE-UCCR-TYPE', true, false), '');
  // 'cc' stands for 'content configuration'
  public static cc =
    new ResourceTabsCardContentConfiguration(UCCRRoleTabsCardConfiguration.labels, UCCRRoleTabsCardConfiguration.chipLabels);

}
