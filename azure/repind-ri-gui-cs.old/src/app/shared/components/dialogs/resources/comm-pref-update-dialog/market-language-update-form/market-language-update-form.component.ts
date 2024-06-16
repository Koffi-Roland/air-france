import { AfterViewInit, Component, Input, OnInit, QueryList, ViewChild, ViewChildren, ViewEncapsulation } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { TranslateService } from '@ngx-translate/core';
import { CommonService } from '../../../../../../core/services/common.service';
import { MarketLanguage } from '../../../../../models/common/market-language';
import { ToggleSliderOption } from '../../../../../models/contents/toggle-slider-option';
import { FieldConfig } from '../../../../../models/forms/field-config';
import { CommunicationPreference } from '../../../../../models/resources/communication-preference';
import { DynamicFormComponent } from '../../../../forms/dynamic-form/dynamic-form.component';

@Component({
  selector: 'app-market-language-update-form',
  templateUrl: './market-language-update-form.component.html',
  styleUrls: ['./market-language-update-form.component.scss']
})
export class MarketLanguageUpdateFormComponent implements OnInit {

  @Input()
  communicationPref: CommunicationPreference;

  @ViewChild('globalOptin', { static: true })
  private _globalOptinForm: DynamicFormComponent;

  @ViewChildren('marketLanguageOptin')
  private _marketLanguageForms: QueryList<DynamicFormComponent>;

  public languageByMarket: Map<string, MarketLanguage[]> = new Map();
  public marketLanguageConfigs: Map<string, FieldConfig[]> = new Map();

  public comprefOptinConfig: FieldConfig[];


  constructor(public common: CommonService, public translateService: TranslateService) { }

  ngOnInit(): void {
    this.classifyLanguageByMarket();
    this.initializeFormConfiguration();
  }

  /**
   * fill languageByMarket in order to display market-language by market
   */
  private classifyLanguageByMarket() {
    this.communicationPref.marketLanguages.forEach(ml => {
      if (this.languageByMarket.has(ml.market)) {
        this.languageByMarket.get(ml.market).push(ml);
      } else {
        this.languageByMarket.set(ml.market, [ml]);
      }
    });
  }

  get globalForm() {
    return this._globalOptinForm;
  }

  get forms() {
    let forms: DynamicFormComponent[] = []
    forms.push(this._globalOptinForm);
    this._marketLanguageForms?.forEach(f => { forms.push(f) });
    return forms;
  }

  get subscriptionValue() {
    return this._globalOptinForm.value.optin;
  }

  /**
   * returns the list of marketlanguages with their new value of optin
   */
  get marketLanguages() {
    let newMarketLanguage: MarketLanguage[] = [];
    const remoteMarketLanguage = this.communicationPref.marketLanguages;
    this._marketLanguageForms.forEach(ml => {
      const formVal = ml.form.value;
      Object.keys(formVal).forEach(key => {
        let marketLanguage = remoteMarketLanguage.find(r => (r.market + r.language) === key).copyAndUpdateCopy(formVal[key]);
        newMarketLanguage.push(marketLanguage);
      });
    });

    return newMarketLanguage;
  }

  private initializeFormConfiguration(): void {

    const optinOptions = [
      new ToggleSliderOption('Y', 'N')
    ];

    const fieldValidators = [
      {
        name: 'required',
        validator: Validators.required,
        message: 'FIELD-REQUIRED'
      }
    ];

    this.comprefOptinConfig = [
      {
        type: 'toggleslider',
        name: 'optin',
        label: 'OPT-IN',
        details: new Map().set('COMM-PREF', []),
        value: this.communicationPref.optin,
        options: optinOptions,
        validations: fieldValidators
      }
    ];

    this.languageByMarket.forEach((value, key) => {
      this.marketLanguageConfigs.set(
        key,
        value.map(ml => ({
          type: 'toggleslider',
          name: ml.market + ml.language,
          label: 'OPT-IN',
          value: ml.optin,
          options: optinOptions,
          validations: fieldValidators,
          details: this.buildDetails(ml),
          width: '100vw'
        }))
      );
    });

  }

  /**
   * build details object for a market language
   * 
   * @param ml a market-language pair
   * @returns map of key and optin
   */
  private buildDetails(ml: MarketLanguage): Map<string, Map<string, any>> {
    let titleWithMoreDetails = new Map();
    const title = this.common.getTransformEnumType(this.common.referenceDataType.LANGUAGES) + ml.language;

    let moreDetails = new Map<string, any>();
    moreDetails.set("CHANNEL", ml.channel);
    moreDetails.set("DATE-OF-CONSENT",
      ml.dateOfConsent +
      ', GMT' + (-1 * new Date().getTimezoneOffset() / 60));
    // we have to multiply by -1 since offset is positive if the local timezone is behind UTC and negative if it is ahead. (e.g. GMT+1 is an offset of -60)

    titleWithMoreDetails.set(title, moreDetails);

    return titleWithMoreDetails;
  }

}



