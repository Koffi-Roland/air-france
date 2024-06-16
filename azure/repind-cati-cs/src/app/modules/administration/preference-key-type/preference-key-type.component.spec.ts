import { HttpClientModule } from '@angular/common/http';
import { DebugElement } from '@angular/core';
import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';
import { MatDialogModule } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { BrowserModule } from '@angular/platform-browser';
import { ActivatedRoute } from '@angular/router';
import { TranslateModule } from '@ngx-translate/core';
import { PreferenceService } from 'src/app/core/services/preference.service';
import { ValidatorsCustom } from 'src/app/shared/widgets/validators/validators-custom.component';

import { PreferenceKeyTypeComponent } from './preference-key-type.component';

describe('PreferenceKeyTypeComponent', () => {
  let component: PreferenceKeyTypeComponent;
  let fixture: ComponentFixture<PreferenceKeyTypeComponent>;

  let preferenceService: PreferenceService;
  let debugElement: DebugElement;

  const fakeActivatedRoute = {
    snapshot: {
      data: {
        preferenceKeyType: []
      },

    }
  };

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [PreferenceKeyTypeComponent],
      imports: [MatDialogModule, TranslateModule.forRoot(), BrowserModule, HttpClientModule],
      providers: [ValidatorsCustom, { provide: ActivatedRoute, useValue: fakeActivatedRoute }, MatSnackBar, PreferenceService]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PreferenceKeyTypeComponent);
    component = fixture.componentInstance;
    debugElement = fixture.debugElement;
    preferenceService = debugElement.injector.get(PreferenceService);
    spyOn(preferenceService, 'getPreferences').and.returnValue(new Promise((resolve, reject) => { resolve([]) }));
    spyOn(preferenceService, 'getPreferenceDatas').and.returnValue(new Promise((resolve, reject) => { resolve([]) }));
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
