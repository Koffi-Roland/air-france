import { LabelConfiguration } from '../models/label-configuration';
import { ResourceTabsCardHeaderConfiguration } from '../models/resource-tabs-card-header-configuration';
import { ResourceTabsCardContentConfiguration } from '../models/resource-tabs-card-content-configuration';

export class TelecomTabsCardConfiguration {

  private static labels = [
    new LabelConfiguration('', 'phoneNumberNotNormalized', '', false, false),
    new LabelConfiguration('', 'countryCode', '', false, false)
  ];

  private static chipLabels = [
    new LabelConfiguration('', 'phoneNumber', '', false, false)
  ];

  // 'hc' stands for 'header configuration'
  public static hc = new ResourceTabsCardHeaderConfiguration(new LabelConfiguration('', 'type', '-TYPE', true, false), 'type');
  // 'cc' stands for 'content configuration'
  public static cc = new ResourceTabsCardContentConfiguration(TelecomTabsCardConfiguration.labels, TelecomTabsCardConfiguration.chipLabels);

}
