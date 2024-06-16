import { AdditionalInfo } from './models/additional-info';
import { IndividualResume } from '../../../../../shared/models/individual/individual-resume';
import { SectionCardContent } from './models/section-card-content';

export class PreferencesConfig {

  private static title = 'PREFERENCES';
  private static color = '#FFD94A';
  private static mainIcon = 'favorite';

  public static config(resume: IndividualResume): SectionCardContent {
    return new SectionCardContent(this.title, this.mainIcon, this.additionalInfo(resume), this.color);
  }

  private static additionalInfo(resume: IndividualResume): Array<AdditionalInfo> {
    const additionalInfo: Array<AdditionalInfo> = [];
    additionalInfo.push(this.preferencesCountInfo(resume));
    return additionalInfo;
  }

  private static preferencesCountInfo(resume: IndividualResume): AdditionalInfo {
    const preferencesCountInfo = resume.preferencesCount;
    return new AdditionalInfo('local_offer', 'NUMBER', preferencesCountInfo);
  }

}
