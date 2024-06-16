import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';
import { MatDialogModule } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { BrowserModule } from '@angular/platform-browser';
import { ActivatedRoute } from '@angular/router';
import { TranslateModule } from '@ngx-translate/core';
import { ValidatorsCustom } from 'src/app/shared/widgets/validators/validators-custom.component';

import { CommunicationPreferencesDgtComponent } from './communication-preferences-dgt.component';

describe('CommunicationPreferencesDgtComponent', () => {
  let component: CommunicationPreferencesDgtComponent;
  let fixture: ComponentFixture<CommunicationPreferencesDgtComponent>;

  const fakeActivatedRoute = {
    snapshot: {
      data: {
        communicationsPreferencesDgt: [],
        communicationsType: [],
        domains: [],
        groupTypes: []
      },

    }
  };

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [CommunicationPreferencesDgtComponent],
      imports: [MatDialogModule, TranslateModule.forRoot(), BrowserModule, HttpClientModule],
      providers: [ValidatorsCustom, { provide: ActivatedRoute, useValue: fakeActivatedRoute }, MatSnackBar]

    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CommunicationPreferencesDgtComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
