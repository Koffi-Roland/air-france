import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { EmailSearchFormComponent } from './email-search-form.component';

describe('EmailSearchFormComponent', () => {
  let component: EmailSearchFormComponent;
  let fixture: ComponentFixture<EmailSearchFormComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ EmailSearchFormComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EmailSearchFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
