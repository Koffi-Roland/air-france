import { AdditionalInfo } from './models/additional-info';
import { IndividualResume } from '../../../../../shared/models/individual/individual-resume';
import { SectionCardContent } from './models/section-card-content';

export class TelecomsConfig {

  private static title = 'TELECOMS';
  private static color = '#E1CFFF';
  private static mainIcon = 'phone';

  public static config(resume: IndividualResume): SectionCardContent {
    return new SectionCardContent(this.title, this.mainIcon, this.additionalInfo(resume), this.color);
  }

  private static additionalInfo(resume: IndividualResume): Array<AdditionalInfo> {
    const additionalInfo: Array<AdditionalInfo> = [];
    additionalInfo.push(this.directTelecomsInfo(resume));
    additionalInfo.push(this.professionalTelecomsInfo(resume));
    return additionalInfo;
  }

  private static directTelecomsInfo(resume: IndividualResume): AdditionalInfo {
    const directTelecomsCount = resume.personnalTelecomsCount;
    return new AdditionalInfo('home', 'PERSO', directTelecomsCount);
  }

  private static professionalTelecomsInfo(resume: IndividualResume): AdditionalInfo {
    const professionalTelecomsCount = resume.professionalTelecomsCount;
    return new AdditionalInfo('business', 'PRO', professionalTelecomsCount);
  }

}
