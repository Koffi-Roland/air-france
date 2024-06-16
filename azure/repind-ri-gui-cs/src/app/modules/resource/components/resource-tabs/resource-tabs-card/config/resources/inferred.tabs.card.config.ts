import { LabelConfiguration } from '../models/label-configuration';
import { ResourceTabsCardHeaderConfiguration } from '../models/resource-tabs-card-header-configuration';
import { ResourceTabsCardContentConfiguration } from '../models/resource-tabs-card-content-configuration';

export class InferredTabsCardConfiguration {

  private static labels = [
    new LabelConfiguration('', 'type', '-INFERRED-TYPE', true, false),
    new LabelConfiguration('ID: ', 'id', '', false, false)
  ];

  private static chipLabels = [
    new LabelConfiguration('', 'inferredDataCount', ' inferred data', false, false)
  ];

  // 'hc' stands for 'header configuration'
  public static hc =
    new ResourceTabsCardHeaderConfiguration(new LabelConfiguration('', 'type', '', false, false), 'status');
  // 'cc' stands for 'content configuration'
  public static cc =
    new ResourceTabsCardContentConfiguration(InferredTabsCardConfiguration.labels, InferredTabsCardConfiguration.chipLabels);

}
