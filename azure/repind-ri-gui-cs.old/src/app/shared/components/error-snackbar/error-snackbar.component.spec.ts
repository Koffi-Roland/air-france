import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { ErrorSnackbarComponent } from './error-snackbar.component';

describe('ErrorSnackbarComponent', () => {
  let component: ErrorSnackbarComponent;
  let fixture: ComponentFixture<ErrorSnackbarComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ ErrorSnackbarComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ErrorSnackbarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
