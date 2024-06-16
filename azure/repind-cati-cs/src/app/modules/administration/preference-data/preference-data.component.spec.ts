/* tslint:disable:no-unused-variable */
import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';
import { BrowserModule, By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';

import { PreferenceDataComponent } from './preference-data.component';
import { PreferenceService } from 'src/app/core/services/preference.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ValidatorsCustom } from 'src/app/shared/widgets/validators/validators-custom.component';
import { MatDialogModule } from '@angular/material/dialog';
import { TranslateModule } from '@ngx-translate/core';
import { HttpClientModule } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';

describe('PreferenceDataComponent', () => {
  let component: PreferenceDataComponent;
  let fixture: ComponentFixture<PreferenceDataComponent>;

  const fakeActivatedRoute = {
    snapshot: {
      data: {
        preferenceData: []
      },

    }
  };

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [PreferenceDataComponent],
      imports: [MatDialogModule, TranslateModule.forRoot(), BrowserModule, HttpClientModule],
      providers: [ValidatorsCustom, { provide: ActivatedRoute, useValue: fakeActivatedRoute }, MatSnackBar, PreferenceService]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PreferenceDataComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
