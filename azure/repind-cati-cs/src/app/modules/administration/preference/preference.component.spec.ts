/* tslint:disable:no-unused-variable */
import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';
import { BrowserModule, By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';

import { PreferenceComponent } from './preference.component';
import { ValidatorsCustom } from 'src/app/shared/widgets/validators/validators-custom.component';
import { ActivatedRoute } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatDialogModule } from '@angular/material/dialog';
import { ErrorService } from 'src/app/core/services/error.service';
import { TranslateModule } from '@ngx-translate/core';
import { PreferenceService } from 'src/app/core/services/preference.service';
import { HttpClient, HttpClientModule } from '@angular/common/http';

describe('PreferenceComponent', () => {
  let component: PreferenceComponent;
  let fixture: ComponentFixture<PreferenceComponent>;

  const fakeActivatedRoute = {
    snapshot: {
      data: {
        preference: []
      },

    }
  };

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [PreferenceComponent],
      imports: [MatDialogModule, TranslateModule.forRoot(), BrowserModule, HttpClientModule],
      providers: [ValidatorsCustom, { provide: ActivatedRoute, useValue: fakeActivatedRoute }, MatSnackBar, PreferenceService]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PreferenceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
