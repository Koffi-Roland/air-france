import { AdditionalInfo } from './models/additional-info';
import { IndividualResume } from '../../../../../shared/models/individual/individual-resume';
import { SectionCardContent } from './models/section-card-content';

export class EmailsConfig {

  private static title = 'EMAILS';
  private static color = '#516DBC';
  private static mainIcon = 'email';

  public static config(resume: IndividualResume): SectionCardContent {
    return new SectionCardContent(this.title, this.mainIcon, this.additionalInfo(resume), this.color);
  }

  private static additionalInfo(resume: IndividualResume): Array<AdditionalInfo> {
    const additionalInfo: Array<AdditionalInfo> = [];
    additionalInfo.push(this.directEmailsInfo(resume));
    additionalInfo.push(this.professionalEmailsInfo(resume));
    return additionalInfo;
  }

  private static directEmailsInfo(resume: IndividualResume): AdditionalInfo {
    const directEmailsCount = resume.personnalEmailsCount;
    return new AdditionalInfo('home', 'PERSO', directEmailsCount);
  }

  private static professionalEmailsInfo(resume: IndividualResume): AdditionalInfo {
    const professionalEmailsCount = resume.professionalEmailsCount;
    return new AdditionalInfo('business', 'PRO', professionalEmailsCount);
  }

}
