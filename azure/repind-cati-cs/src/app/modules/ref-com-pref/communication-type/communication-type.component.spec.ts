import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';
import { MatDialogModule } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { BrowserModule } from '@angular/platform-browser';
import { ActivatedRoute } from '@angular/router';
import { TranslateModule } from '@ngx-translate/core';
import { ValidatorsCustom } from 'src/app/shared/widgets/validators/validators-custom.component';

import { CommunicationTypeComponent } from './communication-type.component';

describe('CommunicationTypeComponent', () => {
  let component: CommunicationTypeComponent;
  let fixture: ComponentFixture<CommunicationTypeComponent>;

  const fakeActivatedRoute = {
    snapshot: {
      data: {
        communicationsType: [],
      },

    }
  };

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [CommunicationTypeComponent],
      imports: [MatDialogModule, TranslateModule.forRoot(), BrowserModule, HttpClientModule],
      providers: [ValidatorsCustom, { provide: ActivatedRoute, useValue: fakeActivatedRoute }, MatSnackBar]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CommunicationTypeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
