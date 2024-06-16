import { NgModule } from '@angular/core';
import { MatTooltipModule } from '@angular/material/tooltip';
import { MatInputModule } from '@angular/material/input';

import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatDialogModule } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';

import { SearchByColumnComponent } from './widgets/search-by-column/search-by-column.component';
import { ExportExcelComponent } from './widgets/export-excel/export-excel.component';
import { DisplayConnectedUserComponent } from './widgets/display-connected-user/display-connected-user.component';
import { ArrayDisplayRefTableComponent } from './arrayDisplayRefTable/arrayDisplayRefTable.component';
import {
  ArrayDisplayRefTableActionComponent
} from './arrayDisplayRefTable/arrayDisplayRefTableAction/arrayDisplayRefTableAction.component';
import {
  DeleteActionComponent
} from './arrayDisplayRefTable/arrayDisplayRefTableAction/genericActions/deleteAction/deleteAction.component';
import {
  CreateUpdateActionComponent
} from './arrayDisplayRefTable/arrayDisplayRefTableAction/genericActions/createUpdateAction/createUpdateAction.component';
import { MaterialModule } from '../modules/material/material.module';
import { CommonModule } from '@angular/common';
import { TranslateModule } from '@ngx-translate/core';
import { InputComponent } from './components/forms/input/input.component';
import { ButtonComponent } from './components/forms/button/button.component';
import { SelectComponent } from './components/forms/select/select.component';
import { DateComponent } from './components/forms/date/date.component';
import { RadiobuttonComponent } from './components/forms/radiobutton/radiobutton.component';
import { CheckboxComponent } from './components/forms/checkbox/checkbox.component';
import { DynamicFieldDirective } from './widgets/dynamic-field/dynamic-field.directive';
import { DynamicFormComponent } from './components/forms/dynamic-form/dynamic-form.component';
import { AutocompleteComponent } from './components/forms/autocomplete/autocomplete.component';
import { AutofocusDirective } from './widgets/autofocus/autofocus.directive';
import { UpperCaseDirective } from './widgets/uppercase/uppercase.directive';
import { HighlightPipe } from './pipes/highlight.pipe';
import { WarningIconComponent } from './widgets/warning-icon/warning-icon.component';
import { ClickOutsideDirective } from './widgets/click-outside/click-outside.directive';
import { UserModule } from '@airfranceklm/permission';
import { CoreModule } from '../core/core.module';
import { SelectTableComponent } from './components/select-table/select-table.component';
import { AddRemoveManyActionComponent } from './arrayDisplayRefTable/arrayDisplayRefTableAction/genericActions/addRemoveManyAction/addRemoveManyAction.component';
import { TogglesliderComponent } from './components/forms/toggleslider/toggleslider.component';
import { TableMultiselectComponent } from './components/forms/table-multiselect/table-multiselect.component';


@NgModule({
  declarations: [
    SearchByColumnComponent,
    ExportExcelComponent,
    DisplayConnectedUserComponent,
    ArrayDisplayRefTableComponent,
    ArrayDisplayRefTableActionComponent,
    DeleteActionComponent,
    CreateUpdateActionComponent,
    SelectTableComponent,
    AddRemoveManyActionComponent,
    InputComponent,
    ButtonComponent,
    SelectComponent,
    DateComponent,
    RadiobuttonComponent,
    TogglesliderComponent,
    CheckboxComponent,
    DynamicFieldDirective,
    AutofocusDirective,
    UpperCaseDirective,
    ClickOutsideDirective,
    DynamicFormComponent,
    HighlightPipe,
    AutocompleteComponent,
    WarningIconComponent,
    TableMultiselectComponent
  ],
  imports: [
    CommonModule,
    TranslateModule,
    MatTooltipModule,
    MaterialModule,
    FormsModule,
    CoreModule,
    ReactiveFormsModule,
    MatDialogModule,
    MatFormFieldModule,
    MatInputModule
  ],
  exports: [
    MaterialModule,
    TranslateModule,
    FormsModule,
    ReactiveFormsModule,
    SearchByColumnComponent,
    ExportExcelComponent,
    AutofocusDirective,
    UpperCaseDirective,
    ClickOutsideDirective,
    CoreModule,
    UserModule,
    DynamicFormComponent,
    DisplayConnectedUserComponent,
    ArrayDisplayRefTableComponent,
    ArrayDisplayRefTableActionComponent,
    HighlightPipe,
    DeleteActionComponent,
    CreateUpdateActionComponent,
    AddRemoveManyActionComponent,
    SelectTableComponent,
    WarningIconComponent,
    TableMultiselectComponent
  ]
})
export class SharedModule { }
