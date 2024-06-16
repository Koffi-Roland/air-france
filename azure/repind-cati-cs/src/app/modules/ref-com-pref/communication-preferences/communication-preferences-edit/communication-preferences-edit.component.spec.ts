import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { MatDialogModule, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { TranslateModule } from '@ngx-translate/core';
import { ValidatorsCustom } from 'src/app/shared/widgets/validators/validators-custom.component';

import { CommunicationPreferencesEditComponent } from './communication-preferences-edit.component';

describe('CommunicationPreferencesEditComponent', () => {
  let component: CommunicationPreferencesEditComponent;
  let fixture: ComponentFixture<CommunicationPreferencesEditComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [CommunicationPreferencesEditComponent],
      imports: [MatDialogModule, TranslateModule.forRoot(), HttpClientModule, MatAutocompleteModule],
      providers: [ValidatorsCustom, MatSnackBar, { provide: MAT_DIALOG_DATA, useValue: { options: [], element: [] } }, { provide: MatDialogRef, useValue: {} }]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CommunicationPreferencesEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
