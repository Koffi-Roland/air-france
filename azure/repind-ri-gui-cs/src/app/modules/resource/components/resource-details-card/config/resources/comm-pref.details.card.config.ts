import { ResourceDetailsCardConfiguration } from '../models/resrouce-details-card-configuration';
import { ResourceDetailsCardHeader } from '../models/resource-details-card-header';
import { ResourceDetailsCardContent } from '../models/resource-details-card-content';
import { ResourceDetailsKeyVal } from '../models/resource-details-key-val';
import { CommunicationPreference } from '../../../../../../shared/models/resources/communication-preference';
import { CommonService } from '../../../../../../core/services/common.service';
import { ReferenceDataType } from '../../../../../../shared/models/references/ReferenceDataType.enum';

export class CommPrefDetailsCardConfig {

  private static contentTitle = 'GENERAL-INFORMATION';
  private static labelBottom = 'GROUP-TYPE';
  private static buttonLabel = 'CONSULT-MARKET-LANGUAGES';

  public static loadCommPrefCardDetails(
    resource: CommunicationPreference
  ): ResourceDetailsCardConfiguration {
    return new ResourceDetailsCardConfiguration(this.header(resource), this.content(resource));
  }

  private static header(resource: CommunicationPreference): ResourceDetailsCardHeader {
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

  private static content(resource: CommunicationPreference): ResourceDetailsCardContent {
    const title = this.contentTitle;
    const data = this.contentData(resource);
    const isUpdatable = this.isUpdatable(resource);
    return new ResourceDetailsCardContent(title, data, resource.creationSignature, resource.modificationSignature,
      isUpdatable, false, this.buttonLabel);
  }

  private static isUpdatable(resource: CommunicationPreference): boolean {
    return (resource.type !== 'FB_ESS');
  }

  private static contentData(resource: CommunicationPreference): Array<ResourceDetailsKeyVal> {
    const data: Array<ResourceDetailsKeyVal> = [];
    data.push(new ResourceDetailsKeyVal('TYPE', CommonService.getTransformEnumTypeStat(ReferenceDataType.COMM_PREF_TYPE) + resource.type));
    data.push(new ResourceDetailsKeyVal('DATE-OF-CONSENT', (resource.dateOfConsent) ? resource.dateOfConsent : '', true));
    return data;
  }

  private static additionalInfo(resource: CommunicationPreference): Array<string> {
    const info: Array<string> = [];
    info.push(CommonService.getTransformEnumTypeStat(ReferenceDataType.COMM_PREF_GTYPE) + resource.groupType);
    return info;
  }

  private static buttonTooltipMsg(resource: CommunicationPreference): string {
    const msg = (resource.subscribe === 'Y') ? 'OPT-IN' : 'OPT-OUT';
    return msg;
  }

  private static buttonColor(resource: CommunicationPreference): string {
    const color = (resource.optin === 'Y') ? 'green' : 'red';
    return color;
  }

  private static buttonIcon(resource: CommunicationPreference): string {
    const icon = (resource.optin === 'Y') ? 'check' : 'not_interested';
    return icon;
  }

  private static labelTopLeft(resource: CommunicationPreference): string {
    return CommonService.getTransformEnumTypeStat(ReferenceDataType.COMM_PREF_DOMAIN) + resource.domain;
  }

  private static icon(resource: CommunicationPreference): string {
    return '';
  }
}
