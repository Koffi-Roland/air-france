import { Component, OnInit, Inject, ViewChild } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { CommunicationPreferenceService } from '../../../../../core/services/resources/communication-preference.service';
import { CommunicationPreference } from '../../../../models/resources/communication-preference';
import { MarketLanguageUpdateFormComponent } from './market-language-update-form/market-language-update-form.component';
import { MarketLanguage } from '../../../../models/common/market-language';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-comm-pref-update-dialog',
  templateUrl: './comm-pref-update-dialog.component.html',
  styleUrls: ['./comm-pref-update-dialog.component.scss']
})
export class CommPrefUpdateDialogComponent implements OnInit {

  @ViewChild('marketLanguagesForm', { static: true }) marketLanguageForm: MarketLanguageUpdateFormComponent;

  public commPref: CommunicationPreference;
  public isChanged: boolean;

  constructor(public dialogRef: MatDialogRef<CommPrefUpdateDialogComponent>, @Inject(MAT_DIALOG_DATA) public data: any,
    private _commPrefService: CommunicationPreferenceService) { }

  ngOnInit() {
    this.commPref = this.data.resource;
  }

  public updateCommPref() {
    const commPrefUpdated = this.getUpdatedCommPref();

    this._commPrefService.update(commPrefUpdated)
      .then(() => {
        this.dialogRef.close();
      })
      .catch((err: HttpErrorResponse) => {
        this.manageHttpError(err);
      });
  }

  /** Method to manage http error in response */
  private manageHttpError(err: HttpErrorResponse): void {
    // Check if the error is due to an invalid email entered by the user
    if (err.error.restError.code === 'business.400.001.932') {
      // Set the error 'invalidChoice' for the optinCtrl
      this.marketLanguageForm.globalForm.setError('optin', { 'invalidChoice': true });
    }
  }

  /**
   * apply update rules for optin values and return the new CommunicationPreference
   * 
   * @returns the updated communication preferences
   */
  private getUpdatedCommPref(): CommunicationPreference {
    let copyUpdated: CommunicationPreference;

    // global optin changed, so every market-languages has to be changed accordingly
    if (this.commPref.subscribe !== this.marketLanguageForm.subscriptionValue) {
      const newMarketLanguageValues = this.commPref.marketLanguages.map(ml => ml.copyAndUpdateCopy(this.marketLanguageForm.subscriptionValue));
      copyUpdated = this.commPref.copyAndUpdateCopy(this.marketLanguageForm.subscriptionValue, newMarketLanguageValues);
    }
    // the market-language changed, so global optin may have to be updated accordingly
    else if (this.isMarketLanguagesOptinChanged(this.commPref.marketLanguages, this.marketLanguageForm.marketLanguages)) {
      let subscribe = this.isAnyMarketLanguages(this.marketLanguageForm.marketLanguages, 'Y') ? 'Y' : 'N';
      copyUpdated = this.commPref.copyAndUpdateCopy(subscribe, this.marketLanguageForm.marketLanguages);
    }

    return copyUpdated ?? this.commPref;
  }

  /**
   * check the presence of 'Y' or 'N' as optin value in the list of MarketLanguages
   * 
   * @param ml all market languages
   * @param val optin value
   * @returns boolean
   */
  private isAnyMarketLanguages(ml: MarketLanguage[], val: 'Y' | 'N'): boolean {
    return ml.reduce((accumulator, current) => accumulator || current.optin === val, false);
  }

  /**
   * compare two list of MarketLanguages of same order (comparison is based on index)
   * detect if there is any change for optin values
   * 
   * @param remote the MarketLanguages as received from the backend
   * @param current the MarketLanguages edited
   * @returns boolean
   */
  private isMarketLanguagesOptinChanged(remote: MarketLanguage[], current: MarketLanguage[]): boolean {
    return remote.map((val, index) => val.optin !== current[index].optin).reduce((acc, current) => acc || current, false);
  }

  /**
   * called on click of the component market-language-update
   */
  public checkChange() {
    setTimeout(() => {
      let marketLanguageChange = this.isMarketLanguagesOptinChanged(this.marketLanguageForm.marketLanguages, this.commPref.marketLanguages);
      let globalChange = this.commPref.subscribe !== this.marketLanguageForm.globalForm.value.optin;
      this.isChanged = globalChange || marketLanguageChange;
    });
  }

}
