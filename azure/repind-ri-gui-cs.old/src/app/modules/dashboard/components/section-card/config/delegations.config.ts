import { AdditionalInfo } from './models/additional-info';
import { IndividualResume } from '../../../../../shared/models/individual/individual-resume';
import { SectionCardContent } from './models/section-card-content';

export class DelegationsConfig {

  private static title = 'DELEGATIONS';
  private static color = '#FF5234';
  private static mainIcon = 'swap_horiz';

  public static config(resume: IndividualResume): SectionCardContent {
    return new SectionCardContent(this.title, this.mainIcon, this.additionalInfo(resume), this.color);
  }

  private static additionalInfo(resume: IndividualResume): Array<AdditionalInfo> {
    const additionalInfo: Array<AdditionalInfo> = [];
    additionalInfo.push(this.delegatesCount(resume));
    additionalInfo.push(this.delegatorsCount(resume));
    return additionalInfo;
  }

  private static delegatesCount(resume: IndividualResume): AdditionalInfo {
    const delegatesCount = resume.delegatesCount;
    return new AdditionalInfo('arrow_downward', 'DELEGATE', delegatesCount);
  }

  private static delegatorsCount(resume: IndividualResume): AdditionalInfo {
    const delegatorsCount = resume.delegatorsCount;
    return new AdditionalInfo('arrow_upward', 'DELEGATOR', delegatorsCount);
  }

}
