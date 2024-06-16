import { AdditionalInfo } from './models/additional-info';
import { IndividualResume } from '../../../../../shared/models/individual/individual-resume';
import { SectionCardContent } from './models/section-card-content';

export class CommunicationPreferencesConfig {

  private static title = 'COMM-PREF';
  private static color = '#FDFF67';
  private static mainIcon = 'question_answer';

  public static config(resume: IndividualResume): SectionCardContent {
    return new SectionCardContent(this.title, this.mainIcon, this.additionalInfo(resume), this.color);
  }



  private static additionalInfo(resume: IndividualResume): Array<AdditionalInfo> {
    const additionalInfo: Array<AdditionalInfo> = [];
    additionalInfo.push(this.optinCount(resume));
    additionalInfo.push(this.optoutCount(resume));
    return additionalInfo;
  }

  private static optinCount(resume: IndividualResume): AdditionalInfo {
    const optinCount = resume.optInCommPrefCount;
    return new AdditionalInfo('check', 'OPT-IN', optinCount);
  }

  private static optoutCount(resume: IndividualResume): AdditionalInfo {
    const optoutCount = resume.optOutCommPrefCount;
    return new AdditionalInfo('not_interested', 'OPT-OUT', optoutCount);
  }

}
