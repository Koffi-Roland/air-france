import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { TelecomUpdateDialogComponent } from './telecom-update-dialog.component';

describe('TelecomUpdateDialogComponent', () => {
  let component: TelecomUpdateDialogComponent;
  let fixture: ComponentFixture<TelecomUpdateDialogComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ TelecomUpdateDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TelecomUpdateDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
