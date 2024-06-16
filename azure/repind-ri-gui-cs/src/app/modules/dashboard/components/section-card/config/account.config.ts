import { AdditionalInfo } from './models/additional-info';
import { SectionCardContent } from './models/section-card-content';
import { IndividualResume } from '../../../../../shared/models/individual/individual-resume';

export class AccountConfig {

  private static title = 'ACCOUNT-IDENTIFIER';
  private static mainIcon = 'person';
  private static color = '#66ff33';

  public static config(resume: IndividualResume): SectionCardContent {
    return new SectionCardContent(this.title, this.mainIcon, this.additionalInfo(resume), this.color);
  }

  private static additionalInfo(resume: IndividualResume): Array<AdditionalInfo> {
    const additionalInfo: Array<AdditionalInfo> = [];
    additionalInfo.push(new AdditionalInfo('mail', 'ACCOUNT-E-TYPE', 0));
    additionalInfo.push(new AdditionalInfo('description', 'ACCOUNT-F-TYPE', 0));
    return additionalInfo;
  }
}
