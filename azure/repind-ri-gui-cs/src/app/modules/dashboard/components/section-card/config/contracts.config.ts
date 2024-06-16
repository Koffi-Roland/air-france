import { AdditionalInfo } from './models/additional-info';
import { IndividualResume } from '../../../../../shared/models/individual/individual-resume';
import { SectionCardContent } from './models/section-card-content';

export class ContractsConfig {

  private static title = 'CONTRACTS';
  private static color = '#FFC9E4';
  private static mainIcon = 'account_balance_wallet';

  public static config(resume: IndividualResume): SectionCardContent {
    return new SectionCardContent(this.title, this.mainIcon, this.additionalInfo(resume), this.color);
  }



  private static additionalInfo(resume: IndividualResume): Array<AdditionalInfo> {
    const additionalInfo: Array<AdditionalInfo> = [];
    additionalInfo.push(this.flyingBlueContractsCount(resume));
    additionalInfo.push(this.otherContractsCount(resume));
    return additionalInfo;
  }

  private static flyingBlueContractsCount(resume: IndividualResume): AdditionalInfo {
    const flyingBlueContractsCount = resume.flyingBlueContractsCount;
    return new AdditionalInfo('credit_card', 'PRODUCT-TYPE-FP', flyingBlueContractsCount);
  }

  private static otherContractsCount(resume: IndividualResume): AdditionalInfo {
    const otherContractsCount = resume.otherContractsCount;
    return new AdditionalInfo('business_center', 'OTHERS', otherContractsCount);
  }

}
