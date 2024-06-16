import { CommonService } from '../common.service';
import { Injectable } from '@angular/core';
import { ReferenceData } from '../../../shared/models/references/ReferenceData';
import { Individual } from '../../../shared/models/individual/individual';
import { OptionItem } from '../../../shared/models/contents/option-item';

@Injectable({
  providedIn: 'root'
})
export class AutocompleteFilterService {

  constructor(private common: CommonService) { }

  /**
   * Given an array of `Individual` this method returns an array of `OptionItem` that's gonna be used
   * in the autocomplete list. The filter is made on the fullname of the individual. If the fullname
   * attribute includes the `value` in parameter, then it's gonna be in the result array.
   * @param value (string typed by the user)
   * @param arr (array of individuals)
   */
  public filterIndividualFullname(value: string, arr: Individual[]): OptionItem[] {
    return arr
      .filter((i: Individual) => i.fullname.toLowerCase().includes(value))
      .map((i: Individual) => new OptionItem(i.gin, i.fullname, i.gin));
  }

  /**
   * Given an array of `ReferenceData` (about country codes) this method returns an array of `OptionItem` that's
   * gonna be used in the autocomplete list. The filter is made on the country code only when the `value` length
   * is less than 3 and on the country name if the `value` length is more or equals to 3.
   * @param value (string typed by the user)
   * @param arr (array of ReferenceData)
   */
  public filterCountryCodes(value: string, arr: ReferenceData[]): OptionItem[] {
    const currentLang = this.common.getCurrentLocal();
    return arr
      // Filter the source array
      .filter((ref: ReferenceData) => {
        if (value.length < 3) {
          // Filter only on country code
          return ref.code.includes(value.toUpperCase());
        } else {
          // Filter only on country name
          const label = (currentLang === 'fr') ? ref.labelFR : ref.labelEN;
          return label.includes(value.toUpperCase());
        }
      })
      // Convert all the references to OptionItem objects
      .map((ref: ReferenceData) => {
        const mainText = (currentLang === 'fr') ? ref.labelFR : ref.labelEN;
        return new OptionItem(ref.code, mainText, ref.code);
      })
      // Sort the OptionItem objects by name
      .sort((a: OptionItem, b: OptionItem) => (a.mainText < b.mainText) ? -1 : 1);
  }

}
