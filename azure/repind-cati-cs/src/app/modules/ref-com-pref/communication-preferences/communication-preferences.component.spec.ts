import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';
import { MatDialogModule } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute } from '@angular/router';
import { TranslateModule } from '@ngx-translate/core';
import { ValidatorsCustom } from 'src/app/shared/widgets/validators/validators-custom.component';

import { CommunicationPreferencesComponent } from './communication-preferences.component';

describe('CommunicationPreferencesComponent', () => {
  let component: CommunicationPreferencesComponent;
  let fixture: ComponentFixture<CommunicationPreferencesComponent>;

  const fakeActivatedRoute = {
    snapshot: {
      data: {
        communicationsType: [],
        domains: [],
        groupTypes: [],
        countryMarket: [],
        media: []
      },

    }
  };

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [CommunicationPreferencesComponent],
      imports: [MatDialogModule, TranslateModule.forRoot(), HttpClientModule],
      providers: [ValidatorsCustom, { provide: ActivatedRoute, useValue: fakeActivatedRoute }, MatSnackBar]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CommunicationPreferencesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
