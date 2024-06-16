import { CommonService } from '../../../../core/services/common.service';
import { Component, OnInit, OnDestroy, Input } from '@angular/core';
import { RefProviderService } from '../../../../core/services/references/ref-provider.service';
import { ReferenceDataType } from '../../../models/references/ReferenceDataType.enum';
import { ReferenceData } from '../../../models/references/ReferenceData';
import { TranslateService } from '@ngx-translate/core';
import { Subscription, Observable } from 'rxjs';
import { UntypedFormControl } from '@angular/forms';
import { map, startWith } from 'rxjs/operators';

@Component({
  selector: 'app-country-autocomplete',
  templateUrl: './country-autocomplete.component.html',
  styleUrls: ['./country-autocomplete.component.scss']
})
export class CountryAutocompleteComponent implements OnInit, OnDestroy {

  @Input() required: boolean;

  public countryCodeFormControl = new UntypedFormControl();
  public filteredCountries: Observable<ReferenceData[]>;
  public currentLocal = '';
  public toHighLight = '';

  private references: ReferenceData[];
  private subscriptionLangChange: Subscription;

  constructor(private refProviderService: RefProviderService, private common: CommonService, private translate: TranslateService) { }

  ngOnInit() {
    this.currentLocal = this.common.getCurrentLocal();
    this.subscriptionLangChange = this.translate.onLangChange.subscribe((data: any) => this.currentLocal = data.lang);
    this.references = this.refProviderService.findReferenceData(ReferenceDataType.COUNTRY_CODES);
    this.filteredCountries = this.countryCodeFormControl.valueChanges
      .pipe(startWith(''), map((value: string) => this.filter(value)));
  }

  ngOnDestroy(): void {
    this.subscriptionLangChange.unsubscribe();
  }

  get countryCode(): string {
    return this.countryCodeFormControl.value;
  }

  get formControl(): UntypedFormControl {
    return this.countryCodeFormControl;
  }

  private filter(value: string): ReferenceData[] {
    this.toHighLight = value;
    const filteredReferences = (value.length < 3) ? this.filterByCountryCode(value) : this.filterByCountry(value);
    filteredReferences.sort((a: ReferenceData, b: ReferenceData) => this.compareByCountry(a, b, this.currentLocal));
    return filteredReferences;
  }

  private filterByCountryCode(value: string): ReferenceData[] {
    return this.references.filter((data: ReferenceData) => data.code.includes(value.toUpperCase()));
  }

  private filterByCountry(value: string): ReferenceData[] {
    return this.references.filter((data: ReferenceData) => {
      if (this.currentLocal === 'fr') {
        return data.labelFR.includes(value.toUpperCase());
      } else {
        return data.labelEN.includes(value.toUpperCase());
      }
    });
  }

  private compareByCountry(a: ReferenceData, b: ReferenceData, lang: string): number {
    let result: number;
    if (lang === 'fr') {
      result = (a.labelFR < b.labelFR) ? -1 : 1;
    } else {
      result = (a.labelEN < b.labelEN) ? -1 : 1;
    }
    return result;
  }

}
