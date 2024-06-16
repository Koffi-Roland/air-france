import { LabelConfiguration } from '../models/label-configuration';
import { ResourceTabsCardHeaderConfiguration } from '../models/resource-tabs-card-header-configuration';
import { ResourceTabsCardContentConfiguration } from '../models/resource-tabs-card-content-configuration';

export class ExternalIDTabsCardConfiguration {

  private static labels = [
    new LabelConfiguration('', 'type', '-EXTERNAL-IDENTIFIER-TYPE', true, false),
    new LabelConfiguration('', 'identifier', '', false, false)
  ];

  private static chipLabels = [
    new LabelConfiguration('', 'functionalDataCount', ' Ext. ID data', false, false)
  ];

  // 'hc' stands for 'header configuration'
  public static hc =
    new ResourceTabsCardHeaderConfiguration(new LabelConfiguration('', 'type', '', false, false), '');
  // 'cc' stands for 'content configuration'
  public static cc =
    new ResourceTabsCardContentConfiguration(ExternalIDTabsCardConfiguration.labels, ExternalIDTabsCardConfiguration.chipLabels);

}
