import { ResourceDetailsCardConfiguration } from '../models/resrouce-details-card-configuration';
import { ResourceDetailsCardHeader } from '../models/resource-details-card-header';
import { ResourceDetailsCardContent } from '../models/resource-details-card-content';
import { ResourceDetailsKeyVal } from '../models/resource-details-key-val';
import { RoleUccr } from '../../../../../../shared/models/resources/role-uccr';

export class RoleUCCRDetailsCardConfig {

  private static contentTitle = 'GENERAL-INFO';

  public static loadRoleUccrCardDetails(
    resource: RoleUccr
  ): ResourceDetailsCardConfiguration {
    return new ResourceDetailsCardConfiguration(this.header(resource), this.content(resource));
  }

  private static header(resource: RoleUccr): ResourceDetailsCardHeader {
    const icon = '';
    const labelTopLeft = this.labelTopLeft(resource);
    const buttonIcon = '';
    const buttonColor = '';
    const buttonTooltipMsg = '';
    const labelBottom = '';
    const additionalInformation = [];
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

  private static content(resource: RoleUccr): ResourceDetailsCardContent {
    const title = this.contentTitle;
    const data = this.contentData(resource);
    const isUpdatable = false;
    return new ResourceDetailsCardContent(title, data, resource.signatureCreation, resource.signatureModification, isUpdatable, false);
  }

  private static contentData(resource: RoleUccr): Array<ResourceDetailsKeyVal> {
    const data: Array<ResourceDetailsKeyVal> = [];
    data.push(new ResourceDetailsKeyVal('TYPE', resource.type));
    data.push(new ResourceDetailsKeyVal('STATUS', resource.status + '-STATUS'));
    data.push(new ResourceDetailsKeyVal('STARTINGDATE', resource.startValidityDate, true));
    data.push(new ResourceDetailsKeyVal('ENDING-DATE', resource.endValidityDate, true));
    return data;
  }

  private static labelTopLeft(resource: RoleUccr): string {
    return resource.type + '-ROLE-UCCR-TYPE';
  }

}
