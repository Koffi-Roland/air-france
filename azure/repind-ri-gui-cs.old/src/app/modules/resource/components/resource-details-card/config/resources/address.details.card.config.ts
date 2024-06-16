import { ResourceDetailsCardConfiguration } from '../models/resrouce-details-card-configuration';
import { ResourceDetailsCardHeader } from '../models/resource-details-card-header';
import { ResourceDetailsCardContent } from '../models/resource-details-card-content';
import { ResourceDetailsKeyVal } from '../models/resource-details-key-val';
import { Status } from '../../../../../../shared/models/common/status';
import { Usage } from '../../../../../../shared/models/common/usage';
import { Type } from '../../../../../../shared/models/common/type';
import { Address } from '../../../../../../shared/models/resources/address';
import { CommonService } from '../../../../../../core/services/common.service';
import { ReferenceDataType } from '../../../../../../shared/models/references/ReferenceDataType.enum';

export class AddressDetailsCardConfig {

  private static contentTitle = 'ADDRESS';
  private static labelBottom = 'JOB-APPLICATION-USAGE';

  public static loadAddressCardDetails(
    resource: Address
  ): ResourceDetailsCardConfiguration {
    return new ResourceDetailsCardConfiguration(this.header(resource), this.content(resource));
  }

  private static header(resource: Address): ResourceDetailsCardHeader {
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

  private static content(resource: Address): ResourceDetailsCardContent {
    const title = this.contentTitle;
    const data = this.contentData(resource);
    const isUpdatable = this.isUpdatable(resource);
    const isDeletable = this.isDeletable(resource);
    return new ResourceDetailsCardContent(title, data, resource.signatureCreation,
      resource.signatureModification, isUpdatable, isDeletable);
  }

  private static isDeletable(resource: Address): boolean {
    return resource.status !== Status.Archived;
  }

  private static isUpdatable(resource: Address): boolean {
    return resource.status !== Status.Archived;
  }

  private static contentData(resource: Address): Array<ResourceDetailsKeyVal> {
    const data: Array<ResourceDetailsKeyVal> = [];
    data.push(new ResourceDetailsKeyVal('CORPORATENAME', resource.corporateName));
    data.push(new ResourceDetailsKeyVal('COMPLEMENT', resource.complement));
    data.push(new ResourceDetailsKeyVal('NO-AND-STREET', resource.numberAndStreet));
    data.push(new ResourceDetailsKeyVal('LOCALITY', resource.locality));
    data.push(new ResourceDetailsKeyVal('ZIP-CODE-AND-CITY', resource.zipCode + ', ' + resource.city));
    data.push(new ResourceDetailsKeyVal('STATE-AND-COUNTRY', CommonService.getTransformEnumTypeStat(ReferenceDataType.COUNTRY_CODES)
    + resource.country));
    data.push(new ResourceDetailsKeyVal('FORCED', (resource.forced) ? resource.forced + '-FORCED' : ''));
    return data;
  }

  private static additionalInfo(resource: Address): Array<string> {
    const info: Array<string> = [];
    resource.usages.forEach((u: Usage) => {
      info.push(u.toString());
    });
    return info;
  }

  private static buttonTooltipMsg(resource: Address): string {
    return resource.status + '-STATUS';
  }

  private static buttonColor(resource: Address): string {
    switch (resource.status) {
      case Status.Valid:
        return 'green';
      case Status.Archived:
        return 'orange';
      case Status.Invalid:
        return 'red';
      default:
        console.error('please give color for this status');
        break;
    }
  }

  private static buttonIcon(resource: Address): string {
    switch (resource.status) {
      case Status.Valid:
        return 'check';
      case Status.Archived:
        return 'archive';
      case Status.Invalid:
        return 'error_outline';
      default:
        console.error('please give icon for this status');
        break;
    }
  }

  private static labelTopLeft(resource: Address): string {
    return resource.type + '-TYPE';
  }

  private static icon(resource: Address): string {
    return resource.type === Type.Home ? 'home' : 'business';
  }
}
