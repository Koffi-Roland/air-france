import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { AddPrefDataFormComponent } from './add-pref-data-form.component';

describe('AddPrefDataFormComponent', () => {
  let component: AddPrefDataFormComponent;
  let fixture: ComponentFixture<AddPrefDataFormComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ AddPrefDataFormComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddPrefDataFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
