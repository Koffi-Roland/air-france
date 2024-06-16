import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { StepperControllerComponent } from './stepper-controller.component';

describe('StepperControllerComponent', () => {
  let component: StepperControllerComponent;
  let fixture: ComponentFixture<StepperControllerComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ StepperControllerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(StepperControllerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
