import { UntypedFormGroup, UntypedFormControl } from '@angular/forms';
import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { CommonService } from '../../../../../core/services/common.service';
import { RefProviderService } from '../../../../../core/services/references/ref-provider.service';
import { ReferenceDataType } from '../../../../models/references/ReferenceDataType.enum';
import { PreferenceData } from '../../../../models/common/preference-data';
import { PreferencesService } from '../../../../../core/services/resources/preferences.service';
import { Preference } from '../../../../models/resources/preference';
import { ReferenceData } from '../../../../models/references/ReferenceData';

@Component({
  selector: 'app-preference-update-dialog',
  templateUrl: './preference-update-dialog.component.html',
  styleUrls: ['./preference-update-dialog.component.scss']
})
export class PreferenceUpdateDialogComponent implements OnInit {

  public currentLocal = '';

  /** The preference in the data (if update) */
  public preference: Preference;

  /** For the preference creation */
  public allPreferenceTypes = [];
  public isPreferenceTypeLocked = false;
  public selectedType: string;
  public createdPreference: Preference;

  /** Form group and controllers */
  public preferencesFormGroup: UntypedFormGroup;
  public preferenceTypeCtrl: UntypedFormControl;

  public isPreferenceDataDisplayed = false;

  public unsavedPreferenceDataCount = 0;

  constructor(public dialogRef: MatDialogRef<PreferenceUpdateDialogComponent>, @Inject(MAT_DIALOG_DATA) public data: any,
    private common: CommonService, private _preferenceService: PreferencesService, private _refProviderService: RefProviderService) {
  }

  ngOnInit() {
    this.currentLocal = this.common.getCurrentLocal();
    if (this.data.isUpdate) {
      this.preference = this.data.resource;
    } else {
      this.allPreferenceTypes = this._refProviderService.findReferenceData(ReferenceDataType.PREFERENCE_TYPES);
      this.orderPreferenceTypes();
      this.initFormControls();
      this.initFormGroup();
    }
  }

  public deletePrefData(data: PreferenceData): void {
    if (this.preference) { this.preference.deletePreferenceData(data); }
    if (this.createdPreference) { this.createdPreference.deletePreferenceData(data); }
  }

  public onValueChanged(): void {
    this.unsavedPreferenceDataCount++;
  }

  public onValueSaved(): void {
    this.unsavedPreferenceDataCount--;
  }

  public lockPreferenceType(): void {
    if (!this.preferenceTypeCtrl.value) { return; }
    if (!this.isPreferenceTypeLocked) {
      this.selectedType = this.preferenceTypeCtrl.value.code;
      const preferenceData = [];
      this.createdPreference = new Preference(null, this.selectedType, null, null, preferenceData);
      this.isPreferenceTypeLocked = true;
      this.preferenceTypeCtrl.disable();
    } else {
      this.createdPreference = null;
      this.isPreferenceTypeLocked = false;
      this.preferenceTypeCtrl.enable();
    }
  }

  public confirmAndSave(): void {
    if (this.data.isUpdate) {
      if (this.preference.preferenceDataCount === 0) {
        this.common.showMessage('CANNOT-UPDATE-PREFERENCE');
      } else {
        this.updatePreference(this.preference);
      }
    } else {
      if (this.createdPreference.preferenceDataCount === 0) {
        this.common.showMessage('CANNOT-CREATE-PREFERENCE');
      } else {
        this.createPreference(this.createdPreference);
      }
    }
  }

  public addPreferenceData(data: PreferenceData): void {
    const isDataAdded = this.preference.addPreferenceData(data);
    const msg = (isDataAdded) ? 'PREFERENCE-DATA-ADDED' : 'PREFERENCE-DATA-UPDATED';
    this.common.showMessage(msg);
  }

  private initFormControls(): void {
    this.preferenceTypeCtrl = new UntypedFormControl();
  }

  private initFormGroup(): void {
    this.preferencesFormGroup = new UntypedFormGroup({
      type: this.preferenceTypeCtrl
    });
  }

  private createPreference(preference: Preference): void {
    this._preferenceService.create(preference).then(() => {
      this.dialogRef.close();
    });
  }

  private updatePreference(preference: Preference): void {
    this._preferenceService.update(preference).then((data: any) => {
      this.dialogRef.close();
    });
  }

  public deletePreference(preference: Preference): void {
    this._preferenceService.delete(preference).then(() => {
      this.dialogRef.close();
    });
  }

  /**
   * Order the preference types alphabetically
   */
  private orderPreferenceTypes(): void {
    this.allPreferenceTypes.sort((a: ReferenceData, b: ReferenceData) => {
      if (this.currentLocal === 'fr') {
        return (a.labelFR < b.labelFR) ? -1 : 1;
      } else {
        return (a.labelEN < b.labelEN) ? -1 : 1;
      }
    });
  }

}
