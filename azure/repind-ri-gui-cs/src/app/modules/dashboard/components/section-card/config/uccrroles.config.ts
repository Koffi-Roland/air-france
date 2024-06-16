import { AdditionalInfo } from './models/additional-info';
import { IndividualResume } from '../../../../../shared/models/individual/individual-resume';
import { SectionCardContent } from './models/section-card-content';

export class UCCRRolesConfig {

  private static title = 'UCCR-ROLES';
  private static color = '#4491FE';
  private static mainIcon = 'fingerprint';

  public static config(resume: IndividualResume): SectionCardContent {
    return new SectionCardContent(this.title, this.mainIcon, this.additionalInfo(resume), this.color);
  }

  private static additionalInfo(resume: IndividualResume): Array<AdditionalInfo> {
    const additionalInfo: Array<AdditionalInfo> = [];
    additionalInfo.push(this.gprolesCountInfo(resume));
    return additionalInfo;
  }

  private static gprolesCountInfo(resume: IndividualResume): AdditionalInfo {
    const uccrolesCount = resume.UCCRRolesCount;
    return new AdditionalInfo('local_offer', 'NUMBER', uccrolesCount);
  }

}
