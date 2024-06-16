import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { AddressUpdateDialogComponent } from './address-update-dialog.component';

describe('AddressUpdateDialogComponent', () => {
  let component: AddressUpdateDialogComponent;
  let fixture: ComponentFixture<AddressUpdateDialogComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ AddressUpdateDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddressUpdateDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
