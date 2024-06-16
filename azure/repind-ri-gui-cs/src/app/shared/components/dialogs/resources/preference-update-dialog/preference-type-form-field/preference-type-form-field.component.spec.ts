import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PreferenceTypeFormFieldComponent } from './preference-type-form-field.component';

describe('PreferenceTypeFormComponent', () => {
  let component: PreferenceTypeFormFieldComponent;
  let fixture: ComponentFixture<PreferenceTypeFormFieldComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [PreferenceTypeFormFieldComponent]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PreferenceTypeFormFieldComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
