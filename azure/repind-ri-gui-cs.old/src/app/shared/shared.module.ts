import { EmailUpdateDialogComponent } from './components/dialogs/resources/email-update-dialog/email-update-dialog.component';
import { CommPrefUpdateDialogComponent } from './components/dialogs/resources/comm-pref-update-dialog/comm-pref-update-dialog.component';
import { AddressUpdateDialogComponent } from './components/dialogs/resources/address-update-dialog/address-update-dialog.component';
import { AutofocusDirective } from './directives/autofocus.directive';
import { LocalizedDatePipe } from './pipes/localized-date.pipe';
import { CapitalizePipe } from './pipes/capitalize.pipe';
import { FirstLetterPipe } from './pipes/first-letter.pipe';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TranslateModule } from '@ngx-translate/core';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { CoreModule } from '../core/core.module';
import { CountryAutocompleteComponent } from './components/forms/country-autocomplete/country-autocomplete.component';
import { HighlightPipe } from './pipes/highlight.pipe';
import { OrderByPipe } from './pipes/orderBy.pipe';
import { InputComponent } from './components/forms/input/input.component';
import { ButtonComponent } from './components/forms/button/button.component';
import { SelectComponent } from './components/forms/select/select.component';
import { DateComponent } from './components/forms/date/date.component';
import { RadiobuttonComponent } from './components/forms/radiobutton/radiobutton.component';
import { CheckboxComponent } from './components/forms/checkbox/checkbox.component';
import { DynamicFieldDirective } from './directives/dynamic-field.directive';
import { DynamicFormComponent } from './components/forms/dynamic-form/dynamic-form.component';
import { AutocompleteComponent } from './components/forms/autocomplete/autocomplete.component';
import { MaterialModule } from '../modules/material/material.module';
import { IndividualCardComponent } from './components/individual-card-new/individual-card.component';
import { ClipboardModule } from 'ngx-clipboard';
import { IndividualDetailsComponent } from './components/dialogs/individual-details/individual-details.component';
import { IndividualUpdateDialogComponent } from './components/dialogs/individual-update-dialog/individual-update-dialog.component';
// tslint:disable-next-line: max-line-length
import { ResourceTypeSelectorDialogComponent } from './components/dialogs/resource-type-selector-dialog/resource-type-selector-dialog.component';
import { ResourcesNotFoundDialogComponent } from './components/dialogs/resources-not-found-dialog/resources-not-found-dialog.component';
// tslint:disable-next-line: max-line-length
import { IndividualProfileUpdateFormComponent } from './components/dialogs/individual-update-dialog/individual-profile-update-form/individual-profile-update-form.component';
// tslint:disable-next-line: max-line-length
import { IndividualUpdateFormComponent } from './components/dialogs/individual-update-dialog/individual-update-form/individual-update-form.component';
import { WarningModalComponent } from './components/warning-modal/warning-modal.component';
import { RouterModule } from '@angular/router';
import { IndividualActionsComponent } from './components/individualActions/individualActions.component';
import { BackButtonComponent } from './components/back-button/back-button.component';
import { ErrorSnackbarComponent } from './components/error-snackbar/error-snackbar.component';
import { StepperControllerComponent } from './components/stepper-controller/stepper-controller.component';
import { DialogToolbarComponent } from './components/dialog-toolbar/dialog-toolbar.component';
// tslint:disable-next-line: max-line-length
import { AddrComplementaryFormComponent } from './components/dialogs/resources/address-update-dialog/addr-complementary-form/addr-complementary-form.component';
// tslint:disable-next-line: max-line-length
import { AddrConfirmationStepComponent } from './components/dialogs/resources/address-update-dialog/addr-confirmation-step/addr-confirmation-step.component';
// tslint:disable-next-line: max-line-length
import { AddrDetailsFormComponent } from './components/dialogs/resources/address-update-dialog/addr-details-form/addr-details-form.component';
// tslint:disable-next-line: max-line-length
import { ResourceGeneralInfoFormComponent } from './components/dialogs/resources/resource-general-info-form/resource-general-info-form.component';
// tslint:disable-next-line: max-line-length
import { EmailDetailsFormComponent } from './components/dialogs/resources/email-update-dialog/email-details-form/email-details-form.component';
import { ConfirmationStepComponent } from './components/dialogs/resources/confirmation-step/confirmation-step.component';
// tslint:disable-next-line: max-line-length
import { ExternalIdentifierDataComponent } from './components/dialogs/resources/external-identifier-data/external-identifier-data.component';
// tslint:disable-next-line: max-line-length
import { DeleteConfirmationDialogComponent } from './components/dialogs/resources/delete-confirmation-dialog/delete-confirmation-dialog.component';
import { MarketLanguagesComponent } from './components/dialogs/resources/market-languages/market-languages.component';
// tslint:disable-next-line: max-line-length
import { PreferenceUpdateDialogComponent } from './components/dialogs/resources/preference-update-dialog/preference-update-dialog.component';
// tslint:disable-next-line: max-line-length
import { AddPrefDataFormComponent } from './components/dialogs/resources/preference-update-dialog/add-pref-data-form/add-pref-data-form.component';
import { PrefDataFormComponent } from './components/dialogs/resources/preference-update-dialog/pref-data-form/pref-data-form.component';
// tslint:disable-next-line: max-line-length
import { PreferenceDataListComponent } from './components/dialogs/resources/preference-update-dialog/preference-data-list/preference-data-list.component';
import { TelecomUpdateDialogComponent } from './components/dialogs/resources/telecom-update-dialog/telecom-update-dialog.component';
// tslint:disable-next-line: max-line-length
import { TelecomDetailsFormComponent } from './components/dialogs/resources/telecom-update-dialog/telecom-details-form/telecom-details-form.component';
import { MomentDateAdapter, MAT_MOMENT_DATE_ADAPTER_OPTIONS } from '@angular/material-moment-adapter';
import { DateAdapter, MAT_DATE_FORMATS, MAT_DATE_LOCALE } from '@angular/material/core';
import { LOCALIZED_DATE_FORMATS } from './utils/helpers/date-format';
import { DragDropModule } from '@angular/cdk/drag-drop';
import { UserModule } from '@airfranceklm/permission';
import { ToggleSliderComponent } from './components/forms/toggle-slider/toggle-slider.component';
import { MarketLanguageUpdateFormComponent } from './components/dialogs/resources/comm-pref-update-dialog/market-language-update-form/market-language-update-form.component';
import { ToggleSliderDetailsComponent } from './components/forms/toggle-slider/toggle-slider-details/toggle-slider-details.component';
import { ConsentUpdateDialogComponent } from './components/dialogs/resources/consent-update-dialog/consent-update-dialog.component';
import { PreferenceTypeFormFieldComponent } from './components/dialogs/resources/preference-update-dialog/preference-type-form-field/preference-type-form-field.component';
import { WarningExpandSnackbarComponent } from './components/warning-expand-snackbar/warning-expand-snackbar.component';
import { DelegationUpdateDialogComponent } from './components/dialogs/resources/delegation-update-dialog/delegation-update-dialog.component';
import { AlertUpdateDialogComponent } from './components/dialogs/resources/alert-update-dialog/alert-update-dialog.component';

@NgModule({
  declarations: [IndividualCardComponent, CountryAutocompleteComponent, HighlightPipe, FirstLetterPipe,
    CapitalizePipe, OrderByPipe, LocalizedDatePipe, AutofocusDirective, InputComponent, ButtonComponent,
    SelectComponent, DateComponent, RadiobuttonComponent, CheckboxComponent, DynamicFieldDirective, DynamicFormComponent,
    AutocompleteComponent, IndividualDetailsComponent, IndividualUpdateDialogComponent,
    ResourceTypeSelectorDialogComponent, ResourcesNotFoundDialogComponent, IndividualProfileUpdateFormComponent,
    IndividualUpdateFormComponent, WarningModalComponent, IndividualActionsComponent, BackButtonComponent,
    ErrorSnackbarComponent, StepperControllerComponent, DialogToolbarComponent, AddressUpdateDialogComponent,
    AddrComplementaryFormComponent, AddrConfirmationStepComponent, AddrDetailsFormComponent, ResourceGeneralInfoFormComponent,
    CommPrefUpdateDialogComponent, EmailUpdateDialogComponent, EmailDetailsFormComponent,
    ConfirmationStepComponent, ExternalIdentifierDataComponent,
    DeleteConfirmationDialogComponent, MarketLanguagesComponent, PreferenceUpdateDialogComponent, AddPrefDataFormComponent,
    PrefDataFormComponent, PreferenceDataListComponent, TelecomUpdateDialogComponent, TelecomDetailsFormComponent,
    ToggleSliderComponent, ToggleSliderDetailsComponent, MarketLanguageUpdateFormComponent, WarningExpandSnackbarComponent,
    DelegationUpdateDialogComponent, AlertUpdateDialogComponent, ConsentUpdateDialogComponent, PreferenceTypeFormFieldComponent,
  ],
  imports: [
    CommonModule,
    TranslateModule,
    ReactiveFormsModule,
    FormsModule,
    CoreModule,
    MaterialModule,
    ClipboardModule,
    RouterModule,
    DragDropModule,
    UserModule.forRoot({
      url: '/rigui-api/api/rest/resources/me'
    }),
  ],
  exports: [
    TranslateModule,
    ReactiveFormsModule,
    CoreModule,
    IndividualCardComponent,
    CountryAutocompleteComponent,
    FirstLetterPipe,
    HighlightPipe,
    FormsModule,
    CapitalizePipe,
    OrderByPipe,
    LocalizedDatePipe,
    AutofocusDirective,
    DynamicFormComponent,
    MaterialModule,
    ClipboardModule,
    UserModule,
    BackButtonComponent,
    StepperControllerComponent,
    DialogToolbarComponent,
    DragDropModule,
    WarningExpandSnackbarComponent
  ],
  providers: [
    {
      provide: DateAdapter,
      useClass: MomentDateAdapter,
      deps: [MAT_DATE_LOCALE, MAT_MOMENT_DATE_ADAPTER_OPTIONS]
    },
    { provide: MAT_DATE_FORMATS, useValue: LOCALIZED_DATE_FORMATS }
  ]
})
export class SharedModule { }
