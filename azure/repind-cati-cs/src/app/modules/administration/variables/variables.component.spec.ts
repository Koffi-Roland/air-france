/* tslint:disable:no-unused-variable */
import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';
import { BrowserModule, By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';

import { VariablesComponent } from './variables.component';
import { MatDialogModule } from '@angular/material/dialog';
import { TranslateModule } from '@ngx-translate/core';
import { HttpClientModule } from '@angular/common/http';
import { ValidatorsCustom } from 'src/app/shared/widgets/validators/validators-custom.component';
import { ActivatedRoute } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';
import { PreferenceService } from 'src/app/core/services/preference.service';

describe('VariablesComponent', () => {
  let component: VariablesComponent;
  let fixture: ComponentFixture<VariablesComponent>;

  const fakeActivatedRoute = {
    snapshot: {
      data: {
        variables: []
      },

    }
  };

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [VariablesComponent],
      imports: [MatDialogModule, TranslateModule.forRoot(), BrowserModule, HttpClientModule],
      providers: [ValidatorsCustom, { provide: ActivatedRoute, useValue: fakeActivatedRoute }, MatSnackBar, PreferenceService]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(VariablesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
