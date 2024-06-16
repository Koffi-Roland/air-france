import { ResourceDetailsCardConfiguration } from '../models/resrouce-details-card-configuration';
import { ResourceDetailsCardHeader } from '../models/resource-details-card-header';
import { ResourceDetailsCardContent } from '../models/resource-details-card-content';
import { ResourceDetailsKeyVal } from '../models/resource-details-key-val';
import { Status } from '../../../../../../shared/models/common/status';
import { Type } from '../../../../../../shared/models/common/type';
import { Email } from '../../../../../../shared/models/resources/email';
import { CommonService } from '../../../../../../core/services/common.service';
import { ReferenceDataType } from '../../../../../../shared/models/references/ReferenceDataType.enum';

export class EmailDetailsCardConfig {

  private static contentTitle = 'EMAILS';
  private static labelBottom = 'AUTHORIZATIONMAILING';

  public static loadEmailCardDetails(
    resource: Email
  ): ResourceDetailsCardConfiguration {
    return new ResourceDetailsCardConfiguration(this.header(resource), this.content(resource));
  }

  private static header(resource: Email): ResourceDetailsCardHeader {
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

  private static content(resource: Email): ResourceDetailsCardContent {
    const title = this.contentTitle;
    const data = this.contentData(resource);
    const isUpdatable = this.isUpdatable(resource);
    const isDeletable = this.isDeletable(resource);
    return new ResourceDetailsCardContent
      (title, data, resource.signatureCreation, resource.signatureModification, isUpdatable, isDeletable);
  }

  private static isDeletable(resource: Email): boolean {
    return resource.status !== Status.Deleted;
  }

  private static isUpdatable(resource: Email): boolean {
    return resource.status !== Status.Deleted;
  }

  private static contentData(resource: Email): Array<ResourceDetailsKeyVal> {
    const data: Array<ResourceDetailsKeyVal> = [];
    data.push(new ResourceDetailsKeyVal('EMAIL', resource.emailAddress));
    return data;
  }

  private static additionalInfo(resource: Email): Array<string> {
    const info: Array<string> = [];
    info.push(CommonService.getTransformEnumTypeStat(ReferenceDataType.NAT) + resource.mailingAuthorization);
    return info;
  }

  private static buttonTooltipMsg(resource: Email): string {
    return resource.status + '-STATUS';
  }

  private static buttonColor(resource: Email): string {
    switch (resource.status) {
      case Status.Valid:
        return 'green';
      case Status.Archived:
        return 'orange';
      case Status.Invalid:
        return 'red';
      case Status.Deleted:
        return 'darkred';
      default:
        console.error('please give color for this status');
        break;
    }
  }

  private static buttonIcon(resource: Email): string {
    switch (resource.status) {
      case Status.Valid:
        return 'check';
      case Status.Archived:
        return 'archive';
      case Status.Invalid:
        return 'error';
      case Status.Deleted:
        return 'delete';
      default:
        console.error('please give icon for this status');
        break;
    }
  }

  private static labelTopLeft(resource: Email): string {
    return resource.type + '-TYPE';
  }

  private static icon(resource: Email): string {
    return resource.type === Type.Home ? 'home' : 'business';
  }
}
