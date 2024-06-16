import { ResourceDetailsCardConfiguration } from '../models/resrouce-details-card-configuration';
import { ResourceDetailsCardHeader } from '../models/resource-details-card-header';
import { ResourceDetailsCardContent } from '../models/resource-details-card-content';
import { ResourceDetailsKeyVal } from '../models/resource-details-key-val';
import { PreferenceData } from '../../../../../../shared/models/common/preference-data';
import { Preference } from '../../../../../../shared/models/resources/preference';
import { CommonService } from '../../../../../../core/services/common.service';
import { ReferenceDataType } from '../../../../../../shared/models/references/ReferenceDataType.enum';

export class PreferenceDetailsCardConfig {

  private static contentTitle = 'PREFERENCES';
  private static labelBottom = 'TYPE';

  constructor() {}

  public static loadPreferenceCardDetails(resource: Preference): ResourceDetailsCardConfiguration {
    return new ResourceDetailsCardConfiguration(this.header(resource), this.content(resource));
  }

  private static header(resource: Preference): ResourceDetailsCardHeader {
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

  private static content(resource: Preference): ResourceDetailsCardContent {
    const title = this.contentTitle;
    const data = this.contentData(resource);
    const isUpdatable = true;
    const isDeletable = true;
    return new ResourceDetailsCardContent
    (title, data, resource.creationSignature, resource.modificationSignature, isUpdatable, isDeletable);
  }

  private static contentData(resource: Preference): Array<ResourceDetailsKeyVal> {
    const data: Array<ResourceDetailsKeyVal> = [];
    data.push(new ResourceDetailsKeyVal('ID', resource.id));
    resource.preferenceData.forEach((pref: PreferenceData) => {
      data.push(new ResourceDetailsKeyVal(CommonService.getTransformEnumTypeStat(ReferenceDataType.PREFERENCE_KEYS)
      + pref.key.toUpperCase(), pref.value));
    });
    return data;
  }

  private static additionalInfo(resource: Preference): Array<string> {
    const info: Array<string> = [];
    info.push(CommonService.getTransformEnumTypeStat(ReferenceDataType.PREFERENCE_TYPES) + resource.type);
    return info;
  }

  private static labelTopLeft(resource: Preference): string {
    return resource.type;
  }

}
