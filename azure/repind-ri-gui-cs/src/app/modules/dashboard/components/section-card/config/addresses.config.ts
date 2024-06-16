import { AdditionalInfo } from './models/additional-info';
import { SectionCardContent } from './models/section-card-content';
import { IndividualResume } from '../../../../../shared/models/individual/individual-resume';

export class AddressesConfig {

  private static title = 'ADDRESSES';
  private static mainIcon = 'place';
  private static color = '#83C1FF';

  public static config(resume: IndividualResume): SectionCardContent {
    return new SectionCardContent(this.title, this.mainIcon, this.additionalInfo(resume), this.color);
  }



  private static additionalInfo(resume: IndividualResume): Array<AdditionalInfo> {
    const additionalInfo: Array<AdditionalInfo> = [];
    additionalInfo.push(this.directAddressesInfo(resume));
    additionalInfo.push(this.professionalAddressesInfo(resume));
    return additionalInfo;
  }

  private static directAddressesInfo(resume: IndividualResume): AdditionalInfo {
    const directAddressesCount = resume.personnalAddressesCount;
    return new AdditionalInfo('home', 'PERSO', directAddressesCount);
  }

  private static professionalAddressesInfo(resume: IndividualResume): AdditionalInfo {
    const professionalAddressesCount = resume.professionalAddressesCount;
    return new AdditionalInfo('business', 'PRO', professionalAddressesCount);
  }

}
