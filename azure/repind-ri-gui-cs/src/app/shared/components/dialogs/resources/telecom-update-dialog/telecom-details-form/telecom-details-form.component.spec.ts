import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { TelecomDetailsFormComponent } from './telecom-details-form.component';

describe('TelecomDetailsFormComponent', () => {
  let component: TelecomDetailsFormComponent;
  let fixture: ComponentFixture<TelecomDetailsFormComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ TelecomDetailsFormComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TelecomDetailsFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
