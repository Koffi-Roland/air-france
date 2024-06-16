import { ComponentFixture, TestBed } from '@angular/core/testing';

import { WarningExpandSnackbarComponent } from './warning-expand-snackbar.component';

describe('WarningExpandSnackbarComponent', () => {
  let component: WarningExpandSnackbarComponent;
  let fixture: ComponentFixture<WarningExpandSnackbarComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ WarningExpandSnackbarComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(WarningExpandSnackbarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
