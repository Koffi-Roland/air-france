import { ResourceTabsCardHeaderConfiguration } from '../models/resource-tabs-card-header-configuration';
import { ResourceTabsCardContentConfiguration } from '../models/resource-tabs-card-content-configuration';
import { LabelConfiguration } from '../models/label-configuration';

export class EmailTabsCardConfiguration {

  private static labels = [
    new LabelConfiguration('', 'emailAddress', '', false, false)
  ];

  private static chipLabels = [
  ];

  // 'hc' stands for 'header configuration'
  public static hc = new ResourceTabsCardHeaderConfiguration(new LabelConfiguration('', 'type', '-TYPE', true, false), 'type');
  // 'cc' stands for 'content configuration'
  public static cc = new ResourceTabsCardContentConfiguration(EmailTabsCardConfiguration.labels, EmailTabsCardConfiguration.chipLabels);

}
