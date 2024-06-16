import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AlertUpdateDialogComponent } from './alert-update-dialog.component';

describe('AlertUpdateComponentComponent', () => {
  let component: AlertUpdateDialogComponent;
  let fixture: ComponentFixture<AlertUpdateDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [AlertUpdateDialogComponent]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AlertUpdateDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
