import { LabelConfiguration } from '../models/label-configuration';
import { ResourceTabsCardHeaderConfiguration } from '../models/resource-tabs-card-header-configuration';
import { ResourceTabsCardContentConfiguration } from '../models/resource-tabs-card-content-configuration';

export class AlertTabsCardConfiguration {

  private static labels = [
    new LabelConfiguration('ID: ', 'id', '', false, false),
    new LabelConfiguration('Type: ', 'type', '', false, false)
  ];

  private static chipLabels = [
    new LabelConfiguration('', 'alertDataCount', ' alert data', false, false)
  ];

  // 'hc' stands for 'header configuration'
  public static hc =
    new ResourceTabsCardHeaderConfiguration(new LabelConfiguration('', 'type', '-ALERT-TYPE', true, false), 'optin');
  // 'cc' stands for 'content configuration'
  public static cc =
    new ResourceTabsCardContentConfiguration(AlertTabsCardConfiguration.labels, AlertTabsCardConfiguration.chipLabels);

}
