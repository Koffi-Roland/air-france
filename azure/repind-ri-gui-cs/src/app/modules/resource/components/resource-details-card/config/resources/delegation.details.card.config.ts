import { ResourceDetailsCardConfiguration } from '../models/resrouce-details-card-configuration';
import { ResourceDetailsCardHeader } from '../models/resource-details-card-header';
import { ResourceDetailsCardContent } from '../models/resource-details-card-content';
import { ResourceDetailsKeyVal } from '../models/resource-details-key-val';
import { Individual } from '../../../../../../shared/models/individual/individual';
import { Delegation } from '../../../../../../shared/models/resources/delegation/delegation';

export class DelegationDetailsCardConfig {

  private static contentTitle = 'GENERAL-INFO';
  private static labelBottom = 'SENDER';

  public static loadDelegationCardDetails(
    resource: Delegation
  ): ResourceDetailsCardConfiguration {
    return new ResourceDetailsCardConfiguration(this.header(resource), this.content(resource));
  }

  private static header(resource: Delegation): ResourceDetailsCardHeader {
    const icon = '';
    const labelTopLeft = this.labelTopLeft(resource);
    const buttonIcon = '';
    const buttonColor = '';
    const buttonTooltipMsg = '';
    const labelBottom = this.labelBottom;
    const additionalInformation = this.additionalInformation(resource);
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

  private static content(resource: Delegation): ResourceDetailsCardContent {
    const title = this.contentTitle;
    const data = this.contentData(resource);
    const isUpdatable = true;
    const isDeletable = true;
    return new ResourceDetailsCardContent(title, data, resource.creationSignature, resource.modificationSignature, isUpdatable, isDeletable);
  }

  private static contentData(resource: Delegation): Array<ResourceDetailsKeyVal> {
    const data: Array<ResourceDetailsKeyVal> = [];
    const delegate: Individual = resource.delegate;
    data.push(new ResourceDetailsKeyVal('STATUS', resource.status + '-DELEGATION-STATUS'));
    data.push(new ResourceDetailsKeyVal('DELEGATE', `${delegate.civility} ${delegate.firstname} ${delegate.lastname}`));
    data.push(new ResourceDetailsKeyVal('DELEGATE-GIN', `${delegate.gin}`));
    const delegator: Individual = resource.delegator;
    data.push(new ResourceDetailsKeyVal('DELEGATOR', `${delegator.civility} ${delegator.firstname} ${delegator.lastname}`));
    data.push(new ResourceDetailsKeyVal('DELEGATOR-GIN', `${delegator.gin}`));
    return data;
  }

  private static additionalInformation(resource: Delegation): string[] {
    const info = [];
    if (resource.sender) { info.push(resource.sender); }
    return info;
  }

  private static labelTopLeft(resource: Delegation): string {
    return resource.type + '-DELEGATION-TYPE';
  }
}
