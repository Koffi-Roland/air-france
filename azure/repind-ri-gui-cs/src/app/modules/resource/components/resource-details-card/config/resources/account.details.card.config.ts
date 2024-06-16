import {ResourceDetailsCardConfiguration} from '../models/resrouce-details-card-configuration';
import {ResourceDetailsCardHeader} from '../models/resource-details-card-header';
import {ResourceDetailsCardContent} from '../models/resource-details-card-content';
import {ResourceDetailsKeyVal} from '../models/resource-details-key-val';
import {Account} from '../../../../../../shared/models/resources/account';

export class AccountDetailsCardConfig {

  private static contentTitle = 'GENERAL-INFO';
  private static labelBottom = 'IDENTIFIER';

  public static loadAccountCardDetailsCard(
    resource: Account
  ): ResourceDetailsCardConfiguration {
    return new ResourceDetailsCardConfiguration(this.header(resource), this.content(resource));
  }

  private static header(resource: Account): ResourceDetailsCardHeader {
    const icon = '';
    const labelTopLeft = 'ACCOUNT-IDENTIFIER';
    const buttonIcon = '';
    const buttonColor = '';
    const buttonTooltipMsg = '';
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

  private static content(resource: Account): ResourceDetailsCardContent {
    const title = this.contentTitle;
    const data = this.contentData(resource);
    return new ResourceDetailsCardContent(
      title,
      data,
      null,
      null,
      false,
      false);
  }

  private static contentData(resource: Account): Array<ResourceDetailsKeyVal> {
    const data: Array<ResourceDetailsKeyVal> = [];
    data.push(new ResourceDetailsKeyVal('ACCOUNT-' + resource.type + '-TYPE', resource.identifier));
    return data;
  }

  private static additionalInfo(resource: Account): Array<string> {
    const info: Array<string> = [];
    info.push(resource.id);
    return info;
  }

  private static buttonTooltipMsg(resource: Account): string {
    return 'OPTIN';
  }

  private static buttonColor(resource: Account): string {
    return 'green';
  }

  private static buttonIcon(resource: Account): string {
    return 'check';
  }
}
