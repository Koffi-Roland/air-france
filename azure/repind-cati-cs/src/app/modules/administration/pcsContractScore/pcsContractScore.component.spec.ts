import { OverlayContainer } from '@angular/cdk/overlay';
import { HttpClientModule } from '@angular/common/http';
import { DebugElement } from '@angular/core';
import { ComponentFixture, ComponentFixtureAutoDetect, TestBed, waitForAsync } from '@angular/core/testing';
import { MatDialogModule } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ActivatedRoute } from '@angular/router';
import { TranslateModule } from '@ngx-translate/core';
import { SharedModule } from 'src/app/shared/shared.module';
import { ValidatorsCustom } from 'src/app/shared/widgets/validators/validators-custom.component';

import { PcsContractScoreComponent } from './pcsContractScore.component';

describe('PcsContractScoreComponent', () => {
  let component: PcsContractScoreComponent;
  let fixture: ComponentFixture<PcsContractScoreComponent>;

  let debugElement: DebugElement;

  const fakeActivatedRoute = {
    snapshot: {
      data: {
        pcsContractScore: []
      },
    }
  };

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [PcsContractScoreComponent],
      imports: [MatDialogModule, TranslateModule.forRoot(), BrowserModule, BrowserAnimationsModule, HttpClientModule, SharedModule],
      providers: [{ provide: ComponentFixtureAutoDetect, useValue: true }, ValidatorsCustom, { provide: ActivatedRoute, useValue: fakeActivatedRoute }, MatSnackBar]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PcsContractScoreComponent);
    component = fixture.componentInstance;
    debugElement = fixture.debugElement;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

});
