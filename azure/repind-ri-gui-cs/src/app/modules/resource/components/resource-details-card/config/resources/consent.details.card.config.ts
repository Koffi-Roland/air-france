import { ResourceDetailsCardConfiguration } from '../models/resrouce-details-card-configuration';
import { ResourceDetailsCardHeader } from '../models/resource-details-card-header';
import { ResourceDetailsCardContent } from '../models/resource-details-card-content';
import { ResourceDetailsKeyVal } from '../models/resource-details-key-val';
import { CommonService } from '../../../../../../core/services/common.service';
import { ReferenceDataType } from '../../../../../../shared/models/references/ReferenceDataType.enum';
import { Consent } from '../../../../../../shared/models/resources/consent';

export class ConsentDetailsCardConfig {

  private static contentTitle = 'GENERAL-INFORMATION';
  private static labelBottom = 'TYPE';

  public static loadConsentCardDetails(
    resource: Consent
  ): ResourceDetailsCardConfiguration {
    return new ResourceDetailsCardConfiguration(this.header(resource), this.content(resource));
  }

  private static header(resource: Consent): ResourceDetailsCardHeader {
    const icon = this.icon(resource);
    const labelTopLeft = this.labelTopLeft(resource);
    const buttonIcon = this.buttonIcon(resource);
    const buttonColor = this.buttonColor(resource);
    const buttonTooltipMsg = resource.consentDataIsConsent + '-CONSENT-TYPE';
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


  private static content(resource: Consent): ResourceDetailsCardContent {
    const title = this.contentTitle;
    const data = this.contentData(resource);
    return new ResourceDetailsCardContent(title, data, resource.consentData.creationSignature, resource.consentData.modificationSignature,
      true, false);
  }

  private static contentData(resource: Consent): Array<ResourceDetailsKeyVal> {
    const data: Array<ResourceDetailsKeyVal> = [];
    data.push(new ResourceDetailsKeyVal('ID', resource.id));
    data.push(
      new ResourceDetailsKeyVal(
        'CONSENT-TYPE',
        CommonService.getTransformEnumTypeStat(ReferenceDataType.CONSENT_TYPE) + resource.type)
    );
    data.push(
      new ResourceDetailsKeyVal(
        'CONSENT-DATA-TYPE',
        CommonService.getTransformEnumTypeStat(ReferenceDataType.CONSENT_DATA_TYPE) + resource.consentData.type, false)
    );
    data.push(new ResourceDetailsKeyVal('DATE-OF-CONSENT', resource.consentData.dateConsent, true));

    return data;
  }


  private static additionalInfo(resource: Consent): Array<string> {
    const info: Array<string> = [];
    info.push(CommonService.getTransformEnumTypeStat(ReferenceDataType.CONSENT_DATA_TYPE) + resource.consentData.type);
    return info;
  }

  private static labelTopLeft(resource: Consent): string {
    return CommonService.getTransformEnumTypeStat(ReferenceDataType.CONSENT_TYPE) + resource.type;
  }

  private static icon(resource: Consent): string {
    return '';
  }

  private static buttonColor(resource: Consent): string {
    switch (resource.consentData.isConsent) {
      case 'Y':
        return 'green';
      case 'N':
        return 'red';
      case 'P':
        return '#FFA800';
    }
  }

  private static buttonIcon(resource: Consent): string {
    switch (resource.consentData.isConsent) {
      case 'Y':
        return 'check';
      case 'N':
        return 'not_interested';
      case 'P':
        return 'hourglass_empty';
    }
  }

}
