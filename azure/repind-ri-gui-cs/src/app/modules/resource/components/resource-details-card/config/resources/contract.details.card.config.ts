import { ResourceDetailsCardConfiguration } from '../models/resrouce-details-card-configuration';
import { ResourceDetailsCardHeader } from '../models/resource-details-card-header';
import { ResourceDetailsCardContent } from '../models/resource-details-card-content';
import { ResourceDetailsKeyVal } from '../models/resource-details-key-val';
import { Contract } from '../../../../../../shared/models/resources/contract';
import { CommonService } from '../../../../../../core/services/common.service';
import { ReferenceDataType } from '../../../../../../shared/models/references/ReferenceDataType.enum';

export class ContractDetailsCardConfig {

  private static contentTitle = 'GENERAL-INFORMATION';
  private static labelBottom = 'CONTRACTNUMBER';

  public static loadContractCardDetails(
    resource: Contract
  ): ResourceDetailsCardConfiguration {
    return new ResourceDetailsCardConfiguration(this.header(resource), this.content(resource));
  }

  private static header(resource: Contract): ResourceDetailsCardHeader {
    const icon = this.icon(resource);
    const labelTopLeft = this.labelTopLeft(resource);
    const labelBottom = this.labelBottom;
    const additionalInformation = this.additionalInfo(resource);
    const isChip = true;
    return new ResourceDetailsCardHeader(
      icon,
      labelTopLeft,
      null,
      null,
      null,
      labelBottom,
      additionalInformation,
      isChip,
      resource.backgroundColor
    );
  }

  private static content(resource: Contract): ResourceDetailsCardContent {
    const title = this.contentTitle;
    const data = this.contentData(resource);
    // Cannot update or delete a MyAccount contract: read-only.
    const isUpdatable = (resource.productType !== 'MA');
    const isDeletable = (resource.productType !== 'MA');
    return new ResourceDetailsCardContent(title, data, resource.creationSignature, resource.modificationSignature,
      isUpdatable, isDeletable);
  }

  private static contentData(resource: Contract): Array<ResourceDetailsKeyVal> {
    const data: Array<ResourceDetailsKeyVal> = [];
    data.push(new ResourceDetailsKeyVal('CONTRACTNUMBER', resource.contractNumber));
    data.push(new ResourceDetailsKeyVal('STATUS', CommonService.getTransformEnumTypeStat(ReferenceDataType.STATES_ROLE_CONTRACT)
    + resource.status));
    data.push(new ResourceDetailsKeyVal('PRODUCTTYPE', resource.productType));
    data.push(new ResourceDetailsKeyVal('PRODUCT-SUB-TYPE', resource.productSubType));
    data.push(new ResourceDetailsKeyVal('COMPANY', resource.company));
    data.push(new ResourceDetailsKeyVal('CONTRACT-VALIDITY-START', resource.startingDate, true));
    data.push(new ResourceDetailsKeyVal('CONTRACT-VALIDITY-END', (resource.endingDate) ? resource.endingDate : '', true));
    return data;
  }

  private static additionalInfo(resource: Contract): Array<string> {
    const info: Array<string> = [];
    info.push(resource.contractNumber);
    return info;
  }

  private static buttonTooltipMsg(resource: Contract): string {
    return CommonService.getTransformEnumTypeStat(ReferenceDataType.STATES_ROLE_CONTRACT) + resource.status;
  }

  private static buttonColor(resource: Contract): string {
    switch (resource.status) {
      case 'C':
        return 'green';
      case 'A':
        return 'red';
      case 'T':
        return 'orange';
      case 'U':
        return 'grey';
      case 'S':
        return 'darkred';
      default:
        console.error(`please give color for this status, ${resource.status}`);
        break;
    }
  }

  private static buttonIcon(resource: Contract): string {
    switch (resource.status) {
      case 'C':
        return 'check';
      case 'A':
        return 'cancel';
      case 'T':
        return 'hourglass_empty';
      case 'U':
        return 'not_interested';
      case 'S':
        return 'delete_forever';
      default:
        console.error(`please give icon for this status, ${resource.status}`);
        break;
    }
  }

  private static labelTopLeft(resource: Contract): string {
    return 'PRODUCT-TYPE-' + resource.productType;
  }

  private static icon(resource: Contract): string {
    return '';
  }
}
