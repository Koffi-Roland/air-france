import { AdditionalInfo } from './models/additional-info';
import { IndividualResume } from '../../../../../shared/models/individual/individual-resume';
import { SectionCardContent } from './models/section-card-content';

export class ExternalIdentifiersConfig {

  private static title = 'EXTERNAL-IDENTIFIERS';
  private static color = '#FF9379';
  private static mainIcon = 'language';

  public static config(resume: IndividualResume): SectionCardContent {
    return new SectionCardContent(this.title, this.mainIcon, this.additionalInfo(resume), this.color);
  }

  private static additionalInfo(resume: IndividualResume): Array<AdditionalInfo> {
    const additionalInfo: Array<AdditionalInfo> = [];
    additionalInfo.push(this.externalIdCountInfo(resume));
    return additionalInfo;
  }

  private static externalIdCountInfo(resume: IndividualResume): AdditionalInfo {
    const externalIdCountInfo = resume.externalIdentifiersCount;
    return new AdditionalInfo('local_offer', 'NUMBER', externalIdCountInfo);
  }

}
