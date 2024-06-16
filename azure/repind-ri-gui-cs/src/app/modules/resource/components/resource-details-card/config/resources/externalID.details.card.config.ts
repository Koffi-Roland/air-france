import { ResourceDetailsCardConfiguration } from '../models/resrouce-details-card-configuration';
import { ResourceDetailsCardHeader } from '../models/resource-details-card-header';
import { ResourceDetailsCardContent } from '../models/resource-details-card-content';
import { ResourceDetailsKeyVal } from '../models/resource-details-key-val';
import { ExternalIdentifier } from '../../../../../../shared/models/resources/external-identifier';

export class ExternalIDDetailsCardConfig {

  private static contentTitle = 'GENERAL-INFORMATION';
  private static labelBottom = 'IDENTIFIER';
  private static buttonLabel = 'VISUALIZE-FUNCTIONAL-DATA';

  public static loadExternalIDCardDetails(
    resource: ExternalIdentifier
  ): ResourceDetailsCardConfiguration {
    return new ResourceDetailsCardConfiguration(this.header(resource), this.content(resource));
  }

  private static header(resource: ExternalIdentifier): ResourceDetailsCardHeader {
    const icon = '';
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

  private static content(resource: ExternalIdentifier): ResourceDetailsCardContent {
    const title = this.contentTitle;
    const data = this.contentData(resource);
    const isUpdatable = this.isUpdatable(resource);
    return new ResourceDetailsCardContent(title, data, resource.creationSignature, resource.modificationSignature,
      isUpdatable, false, this.buttonLabel);
  }

  private static isUpdatable(resource: ExternalIdentifier): boolean {
    return false;
  }

  private static contentData(resource: ExternalIdentifier): Array<ResourceDetailsKeyVal> {
    const data: Array<ResourceDetailsKeyVal> = [];
    data.push(new ResourceDetailsKeyVal('TYPE', resource.type));
    data.push(new ResourceDetailsKeyVal('LAST-SEEN-DATE', (resource.lastSeenDate) ? resource.lastSeenDate : '', true));
    return data;
  }

  private static additionalInfo(resource: ExternalIdentifier): Array<string> {
    const info: Array<string> = [];
    info.push(resource.identifier);
    return info;
  }

  private static labelTopLeft(resource: ExternalIdentifier): string {
    return resource.type + '-EXTERNAL-IDENTIFIER-TYPE';
  }
}
