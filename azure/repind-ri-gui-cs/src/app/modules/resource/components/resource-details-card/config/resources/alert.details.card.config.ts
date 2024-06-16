import { ResourceDetailsCardConfiguration } from '../models/resrouce-details-card-configuration';
import { ResourceDetailsCardHeader } from '../models/resource-details-card-header';
import { ResourceDetailsCardContent } from '../models/resource-details-card-content';
import { ResourceDetailsKeyVal } from '../models/resource-details-key-val';
import { AlertData } from '../../../../../../shared/models/common/alert-data';
import { Alert } from '../../../../../../shared/models/resources/alert';

export class AlertDetailsCardConfig {

  private static contentTitle = 'GENERAL-INFO';
  private static labelBottom = 'PRIMARY-KEY';

  public static loadAlertCardDetails(
    resource: Alert
  ): ResourceDetailsCardConfiguration {
    return new ResourceDetailsCardConfiguration(this.header(resource), this.content(resource));
  }

  private static header(resource: Alert): ResourceDetailsCardHeader {
    const icon = '';
    const labelTopLeft = this.labelTopLeft(resource);
    const buttonIcon = this.buttonIcon(resource);
    const buttonColor = this.buttonColor(resource);
    const buttonTooltipMsg = this.buttonTooltipMsg(resource);
    const labelBottom = this.labelBottom;
    const additionalInformation = this.additionalInfo(resource);
    const isChip = true;
    return new ResourceDetailsCardHeader(
      icon,
      labelTopLeft,
      buttonIcon,
      buttonColor,
      buttonTooltipMsg,
      labelBottom,
      additionalInformation,
      isChip,
      resource.backgroundColor
    );
  }

  private static content(resource: Alert): ResourceDetailsCardContent {
    const title = this.contentTitle;
    const data = this.contentData(resource);
    const isUpdatable = true;
    return new ResourceDetailsCardContent(title, data, resource.creationSignature, resource.modificationSignature, isUpdatable, false);
  }

  private static contentData(resource: Alert): Array<ResourceDetailsKeyVal> {
    const data: Array<ResourceDetailsKeyVal> = [];
    resource.alertdata.forEach((ad: AlertData) => {
      data.push(new ResourceDetailsKeyVal(ad.key, ad.value));
    });
    return data;
  }

  private static additionalInfo(resource: Alert): Array<string> {
    const info: Array<string> = [];
    info.push(resource.id);
    return info;
  }

  private static buttonTooltipMsg(resource: Alert): string {
    const tooltipMsg = (resource.optin === 'Y') ? 'OPT-IN' : 'OPT-OUT';
    return tooltipMsg;
  }

  private static buttonColor(resource: Alert): string {
    const color = (resource.optin === 'Y') ? 'green' : 'red';
    return color;
  }

  private static buttonIcon(resource: Alert): string {
    const icon = (resource.optin === 'Y') ? 'check' : 'not_interested';
    return icon;
  }

  private static labelTopLeft(resource: Alert): string {
    return resource.type + '-ALERT-TYPE';
  }
}
