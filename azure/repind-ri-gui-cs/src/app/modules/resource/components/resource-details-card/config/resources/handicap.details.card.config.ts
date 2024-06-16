import { ResourceDetailsCardConfiguration } from '../models/resrouce-details-card-configuration';
import { ResourceDetailsCardHeader } from '../models/resource-details-card-header';
import { ResourceDetailsCardContent } from '../models/resource-details-card-content';
import { ResourceDetailsKeyVal } from '../models/resource-details-key-val';
import { HandicapData } from '../../../../../../shared/models/common/handicapData';
import { Handicap } from '../../../../../../shared/models/resources/handicap';
import { CommonService } from '../../../../../../core/services/common.service';
import { ReferenceDataType } from '../../../../../../shared/models/references/ReferenceDataType.enum';

export class HandicapDetailsCardConfig {

  private static contentTitle = 'GENERAL-INFORMATION';
  private static labelBottom = 'Code';

  public static loadHandicapCardDetails(
    resource: Handicap
  ): ResourceDetailsCardConfiguration {
    return new ResourceDetailsCardConfiguration(this.header(resource), this.content(resource));
  }

  private static header(resource: Handicap): ResourceDetailsCardHeader {
    const icon = this.icon(resource);
    const labelTopLeft = this.labelTopLeft(resource);
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

  private static isUpdatable(resource: Handicap): boolean {
    return false;
  }

  private static content(resource: Handicap): ResourceDetailsCardContent {
    const title = this.contentTitle;
    const data = this.contentData(resource);
    const isUpdatable = false;
    return new ResourceDetailsCardContent(title, data, resource.creationSignature, resource.modificationSignature, isUpdatable, false);
  }

  private static contentData(resource: Handicap): Array<ResourceDetailsKeyVal> {
    const data: Array<ResourceDetailsKeyVal> = [];
    resource.handicapData.forEach((pref: HandicapData) => {
      data.push(new ResourceDetailsKeyVal(CommonService.getTransformEnumTypeStat(ReferenceDataType.HANDICAP_DATA_KEY) +
      pref.key.toUpperCase(), pref.value));
    });
    return data;
  }

  private static additionalInfo(resource: Handicap): Array<string> {
    const info: Array<string> = [];
    info.push(CommonService.getTransformEnumTypeStat(ReferenceDataType.HANDICAP_CODE) + resource.code);
    return info;
  }

  private static labelTopLeft(resource: Handicap): string {
    return  CommonService.getTransformEnumTypeStat(ReferenceDataType.HANDICAP_TYPE) + resource.type;
  }

  private static icon(resource: Handicap): string {
    return '';
  }

}
