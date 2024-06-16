import { AdditionalInfo } from './models/additional-info';
import { IndividualResume } from '../../../../../shared/models/individual/individual-resume';
import { SectionCardContent } from './models/section-card-content';

export class AlertsConfig {

  private static title = 'ALERTS';
  private static mainIcon = 'notifications_active';
  private static color = '#96AFBE';

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
    const optinCount = resume.optInAlertsCount;
    return new AdditionalInfo('check', 'OPT-IN', optinCount);
  }

  private static optoutCount(resume: IndividualResume): AdditionalInfo {
    const optoutCount = resume.optOutAlertsCount;
    return new AdditionalInfo('not_interested', 'OPT-OUT', optoutCount);
  }

}
