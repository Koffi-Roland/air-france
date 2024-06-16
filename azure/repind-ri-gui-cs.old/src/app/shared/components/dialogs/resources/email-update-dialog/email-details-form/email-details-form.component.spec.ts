import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { EmailDetailsFormComponent } from './email-details-form.component';

describe('EmailDetailsFormComponent', () => {
  let component: EmailDetailsFormComponent;
  let fixture: ComponentFixture<EmailDetailsFormComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ EmailDetailsFormComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EmailDetailsFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
