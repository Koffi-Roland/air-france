import { ResourceDetailsCardConfiguration } from '../models/resrouce-details-card-configuration';
import { ResourceDetailsCardHeader } from '../models/resource-details-card-header';
import { ResourceDetailsCardContent } from '../models/resource-details-card-content';
import { ResourceDetailsKeyVal } from '../models/resource-details-key-val';
import { InferredData } from '../../../../../../shared/models/common/inferredData';
import { Inferred } from '../../../../../../shared/models/resources/inferred';

export class InferredDetailsCardConfig {

  private static contentTitle = 'INFERRED';
  private static labelBottom = 'TYPE';

  public static loadInferredCardDetails(
    resource: Inferred
  ): ResourceDetailsCardConfiguration {
    return new ResourceDetailsCardConfiguration(this.header(resource), this.content(resource));
  }

  private static header(resource: Inferred): ResourceDetailsCardHeader {
    const icon = this.icon(resource);
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

  private static isUpdatable(resource: Inferred): boolean {
    return false;
  }

  private static content(resource: Inferred): ResourceDetailsCardContent {
    const title = this.contentTitle;
    const data = this.contentData(resource);
    const isUpdatable = false;
    return new ResourceDetailsCardContent(title, data, resource.creationSignature, resource.modificationSignature, isUpdatable, false);
  }

  private static contentData(resource: Inferred): Array<ResourceDetailsKeyVal> {
    const data: Array<ResourceDetailsKeyVal> = [];
    resource.inferredData.forEach((pref: InferredData) => {
      data.push(new ResourceDetailsKeyVal(pref.key + '-INFERRED-KEY', pref.value));
    });
    return data;
  }

  private static additionalInfo(resource: Inferred): Array<string> {
    const info: Array<string> = [];
    info.push(resource.type + '-INFERRED-TYPE');
    return info;
  }

  private static labelTopLeft(resource: Inferred): string {
    return resource.type;
  }

  private static buttonTooltipMsg(resource: Inferred): string {
    let msg = '';
    switch(resource.status) {
      case 'C':
         msg = 'C-INFERRED-TYPE';
      break;
      case 'V':
          msg = 'V-INFERRED-TYPE';
      break;
      case 'I':
          msg = 'I-INFERRED-TYPE';
      break;
      case 'R':
          msg = 'R-INFERRED-TYPE';
      break;
      case 'X':
          msg = 'X-INFERRED-TYPE';
      break;
    }
    return msg;
  }

  private static buttonColor(resource: Inferred): string {
    let color = '';
    switch(resource.status) {
      case 'C':
        color = 'cornflowerblue';
      break;
      case 'V':
        color = 'green';
      break;
      case 'I':
         color = 'coral';
      break;
      case 'R':
        color = 'red';
      break;
      case 'X':
        color = 'lightgray';
      break;
    }
    return color;
  }

  private static buttonIcon(resource: Inferred): string {
    let icon = '';
    switch(resource.status) {
      case 'C':
        icon = 'phonelink_setup';
      break;
      case 'V':
         icon = 'check';
      break;
      case 'I':
        icon = 'phonelink_erase';
      break;
      case 'R':
        icon = 'not_interested';
      break;
      case 'X':
        icon = 'delete';
      break;
    }
    return icon;
  }

  private static icon(resource: Inferred): string {
    return '';
  }

}
