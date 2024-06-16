import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DelegationUpdateDialogComponent } from './delegation-update-dialog.component';

describe('DelegationUpdateDialogComponent', () => {
  let component: DelegationUpdateDialogComponent;
  let fixture: ComponentFixture<DelegationUpdateDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DelegationUpdateDialogComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DelegationUpdateDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
