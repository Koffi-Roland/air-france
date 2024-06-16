import { Component, OnInit, Inject, AfterViewChecked, ChangeDetectorRef } from '@angular/core';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import {UntypedFormControl, UntypedFormGroup, FormGroupDirective, NgForm, Validators} from '@angular/forms';
import { CommunicationPreferencesService } from '../../../../core/services/communication-preferences.service';
import { EventActionService } from '../../../../shared/arrayDisplayRefTable/_services/eventAction.service';
import { ValidatorsCustom } from '../../../../shared/widgets/validators/validators-custom.component';
import { TranslateService } from '@ngx-translate/core';
import { MyErrorStateMatcher } from '../../../../shared/widgets/my-error-state-matcher/my-error-state-matcher.component';
import {map, startWith} from 'rxjs/operators';
import {Observable} from 'rxjs';
import {CdkDragDrop, moveItemInArray} from '@angular/cdk/drag-drop';
import { EventActionMessage, EventActionMessageEnum } from '../../../../shared/models/EventActionMessage';

@Component({
  selector: 'app-communication-preferences-edit',
  templateUrl: './communication-preferences-edit.component.html',
  styleUrls: ['./communication-preferences-edit.component.css']
})
export class CommunicationPreferencesEditComponent implements OnInit, AfterViewChecked {

  title: string;
  communicationPreferencesForm: UntypedFormGroup;
  communicationPreferences: any;
  matcher = new MyErrorStateMatcher();
  checkList: any;
  user: any;
  listDomain: any;
  listType: any;
  listGType: any;
  listMedia: any;
  listComPref: any;
  listCountryMarket: any;
  editLanguage: boolean;
  filteredCountryMarket: Observable<any>;
  action: string;

  constructor(private snackBar: MatSnackBar,
    private translate: TranslateService,
    public dialogRef: MatDialogRef<CommunicationPreferencesEditComponent>,
    private service: CommunicationPreferencesService,
    private validatorsCustom: ValidatorsCustom,
    @Inject(MAT_DIALOG_DATA) public data: any,
    public dialog: MatDialog,
    private cdRef: ChangeDetectorRef,
    private eventActionService: EventActionService) {}

  ngOnInit() {

    this.communicationPreferences = {};

    this.communicationPreferencesForm = new UntypedFormGroup({
      domain: new UntypedFormControl('', Validators.required),
      comGroupeType: new UntypedFormControl('', Validators.required),
      comType: new UntypedFormControl('', Validators.required),
      market: new UntypedFormControl(''),
      media: new UntypedFormControl(''),
      languageToAdd: new UntypedFormControl(''),
      defaultLanguages: new UntypedFormControl([])
    });

    this.listDomain = this.service.listDomains;
    this.listType = this.service.listTypes;
    this.listGType = this.service.listGTypes;
    this.listCountryMarket = this.service.listCountryMarkets;
    this.listMedia = this.service.listMedias;
    this.listComPref = this.service.listComPref;

    this.action = this.data.label;

    if (this.action === 'UPDATE') {
      this.communicationPreferences = JSON.parse(JSON.stringify(this.data.element));
      // this.transformValueForIHM();
      this.communicationPreferencesForm.patchValue(this.communicationPreferences);
      this.removeActualElementFromList(this.communicationPreferences.refComprefId);

      this.transformDefaultLanguageToList();
    }

    this.communicationPreferencesForm.controls['languageToAdd'].setValidators(
      Validators.compose([
      // ValidatorsCustom.isUnique(this.communicationPreferencesForm.value.defaultLanguages),
      ValidatorsCustom.isUnique(),
      Validators.minLength(2),
      Validators.maxLength(2)
      ])
    );

    this.updateTitle();

    this.filteredCountryMarket = this.communicationPreferencesForm.controls['market'].valueChanges
    .pipe(
      startWith(''),
      map(codePays => codePays.length >= 1 ? this._filterCountry(codePays) : [])
    );

    this.communicationPreferencesForm.setValidators(
      Validators.compose([
        this.validatorsCustom.isDefaultLanguagesValid(),
        this.validatorsCustom.isMarketValid(this.listCountryMarket, this.listComPref)
      ])
    );
  }

  ngAfterViewChecked() {
    this.cdRef.detectChanges();
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  drop(event: CdkDragDrop<string[]>) {
    moveItemInArray(this.communicationPreferencesForm.value.defaultLanguages, event.previousIndex, event.currentIndex);
  }

  setValue(e, field) {
    if (e.checked) {
      this.communicationPreferences[field] = 'Y';
    } else {
      this.communicationPreferences[field] = 'N';
    }
  }

  /**
   * For validation, remove actual element from the list.
   */
  removeActualElementFromList(param) {
      // For copy object without reference
      this.listComPref = JSON.parse(JSON.stringify(this.listComPref));
      // Polyfill for IE 11
      if (!Array.prototype.findIndex) {
        let index = -1;
        this.listComPref.some(function(item, i) {
            if (item.refComprefId === param) {
                index = i;
                return true;
            }
        });
        if (index !== -1) {
          this.listComPref.splice(index, 1);
        }
    } else {
        // For other browsers
        const index = this.listComPref.findIndex(item => item.refComprefId === param);
        if (index !== -1) {
          this.listComPref.splice(index, 1);
        }
    }
  }

  // /**
  //  * For GUI, change value 'N' to false and 'Y' to true
  //  */
  // transformValueForIHM() {
  //   if (this.communicationPreferences.mandatoryOptin === 'Y') {
  //     this.communicationPreferences.mandatoryOptin = true;
  //   } else if (this.communicationPreferences.mandatoryOptin === 'N') {
  //     this.communicationPreferences.mandatoryOptin = false;
  //   }

  //   if (this.communicationPreferences.fieldT === 'Y') {
  //     this.communicationPreferences.fieldT = true;
  //   } else if (this.communicationPreferences.fieldT === 'N') {
  //     this.communicationPreferences.fieldT = false;
  //   }

  //   if (this.communicationPreferences.fieldA === 'Y') {
  //     this.communicationPreferences.fieldA = true;
  //   } else if (this.communicationPreferences.fieldA === 'N') {
  //     this.communicationPreferences.fieldA = false;
  //   }

  //   if (this.communicationPreferences.fieldN === 'Y') {
  //     this.communicationPreferences.fieldN = true;
  //   } else if (this.communicationPreferences.fieldN === 'N') {
  //     this.communicationPreferences.fieldN = false;
  //   }

  // }

  // /**
  //  * For GUI, change value 'N' to false and 'Y' to true
  //  */
  // transformValueForBackend(values) {
  //   if (values.mandatoryOptin === true) {
  //     this.communicationPreferences.mandatoryOptin = 'Y';
  //   } else if (values.mandatoryOptin === false || values.mandatoryOptin === '') {
  //     this.communicationPreferences.mandatoryOptin = 'N';
  //   }

  //   if (values.fieldT === true) {
  //     this.communicationPreferences.fieldT = 'Y';
  //   } else if (values.fieldT === false || values.fieldT === '') {
  //     this.communicationPreferences.fieldT = 'N';
  //   }

  //   if (values.fieldA === true) {
  //     this.communicationPreferences.fieldA = 'Y';
  //   } else if (values.fieldA === false || values.fieldA === '') {
  //     this.communicationPreferences.fieldA = 'N';
  //   }

  //   if (values.fieldN === true) {
  //     this.communicationPreferences.fieldN = 'Y';
  //   } else if (values.fieldN === false || values.fieldN === '') {
  //     this.communicationPreferences.fieldN = 'N';
  //   }

  // }

  /**
   * Updates the title of the page depending on if we update or create a resource.
   */
  updateTitle() {
    if (this.data !== undefined && this.data.id !== undefined) {
        this.title = 'UPDATE_TYPE';
    } else {
        this.title = 'CREATE_NEW_TYPE';
    }
  }


  /**
   * Add default languages from object communicationPreference to list
   *
   */
  transformDefaultLanguageToList() {

    for (let i = 1; i < 11; i++) {
        const name = 'defaultLanguage' + i;
        if (this.communicationPreferences[name] != null && this.communicationPreferences[name] !== '*') {
            this.communicationPreferencesForm.value.defaultLanguages.push(this.communicationPreferences[name]);
        }
    }

  }

  /**
   * Add list of default languages to object communicationPreference
   *
   */
  transformListToDefaultLanguage() {
      for (let i = 1; i < 11; i++) {
          const name = 'defaultLanguage' + i;
          if (i > this.communicationPreferencesForm.value.defaultLanguages.length) {
              this.communicationPreferences[name] = null;
          } else {
              this.communicationPreferences[name] = this.communicationPreferencesForm.value.defaultLanguages[i - 1];
          }
      }
  }


    /**
     * Add a language to default languages table
     */
  addLanguage() {
    if (!this.communicationPreferencesForm.get('languageToAdd').hasError('isUnique')
    && !this.communicationPreferencesForm.controls['languageToAdd'].hasError('maxlength')
    && !this.communicationPreferencesForm.controls['languageToAdd'].hasError('minlength')
    && this.communicationPreferencesForm.controls['languageToAdd'].value !== ''
    && !this.communicationPreferencesForm.pristine) {
      this.communicationPreferencesForm.value.defaultLanguages.push(this.communicationPreferencesForm.value.languageToAdd);
      this.communicationPreferencesForm.controls['languageToAdd'].setValue('');
    }
  }

  /**
   * Remove a language : If it is an edit, display warning before delete
   */
  removeLanguage(language) {

    this.removeFromDefaultLanguage(language);

  }

  /**
   * Remove a language from default languages table
   */
  removeFromDefaultLanguage(language) {
    const index = this.communicationPreferencesForm.controls['defaultLanguages'].value.indexOf(language);
    if (index !== -1) {
        this.communicationPreferencesForm.controls['defaultLanguages'].value.splice(index, 1);
    }
    if (this.communicationPreferencesForm.controls['defaultLanguages'].value.length === 0) {
      this.communicationPreferencesForm.setErrors({'isDefaultLanguagesValid': true});
    }

  }

  /**
   * Remove a language from default languages table
   */
  checkIfLanguageDisabled() {
    if (this.communicationPreferencesForm.controls['languageToAdd'].hasError('isUnique')
    || this.communicationPreferencesForm.controls['languageToAdd'].hasError('maxlength')
    || this.communicationPreferencesForm.controls['languageToAdd'].hasError('minlength')
    || this.communicationPreferencesForm.controls['languageToAdd'].value === ''
    || this.communicationPreferencesForm.pristine === true) {
      return true;
    }
    return false;
  }

  submit() {
    const values = this.communicationPreferencesForm.value;

    this.communicationPreferences.domain = values.domain;
    this.communicationPreferences.comGroupeType = values.comGroupeType;
    this.communicationPreferences.comType = values.comType;
    this.communicationPreferences.media = values.media;

    if (!this.communicationPreferences.mandatoryOptin) {
      this.communicationPreferences.mandatoryOptin = 'N';
    }
    if (!this.communicationPreferences.fieldA) {
      this.communicationPreferences.fieldA = 'N';
    }
    if (!this.communicationPreferences.fieldT) {
      this.communicationPreferences.fieldT = 'N';
    }
    if (!this.communicationPreferences.fieldN) {
      this.communicationPreferences.fieldN = 'N';
    }

    // this.transformValueForBackend(values);

    this.transformListToDefaultLanguage();
    if (!values.market || values.market === '') {
        this.communicationPreferences.market = '*';
    } else {
      this.communicationPreferences.market = values.market;
    }

    if (!this.communicationPreferences.defaultLanguage1 || this.communicationPreferences.defaultLanguage1 === '') {
        this.communicationPreferences.defaultLanguage1 = '*';
    }

    this.eventActionService.actionSentToBeApply(new EventActionMessage(this.action.toString(), this.communicationPreferences));
    this.dialogRef.close(true);

  }

  private _filterCountry(value: string) {
    const filterValue = value.toLowerCase();
    return this.listCountryMarket.filter(country => country.mainText.toLowerCase().indexOf(filterValue) === 0);
  }

}
