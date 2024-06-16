import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { AddrConfirmationStepComponent } from './addr-confirmation-step.component';

describe('AddrConfirmationStepComponent', () => {
  let component: AddrConfirmationStepComponent;
  let fixture: ComponentFixture<AddrConfirmationStepComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ AddrConfirmationStepComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddrConfirmationStepComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
