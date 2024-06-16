import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { EmailUpdateDialogComponent } from './email-update-dialog.component';

describe('EmailUpdateDialogComponent', () => {
  let component: EmailUpdateDialogComponent;
  let fixture: ComponentFixture<EmailUpdateDialogComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ EmailUpdateDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EmailUpdateDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
