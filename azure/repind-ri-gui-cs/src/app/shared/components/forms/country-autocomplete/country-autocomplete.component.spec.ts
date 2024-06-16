import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { CountryAutocompleteComponent } from './country-autocomplete.component';

describe('CountryAutocompleteComponent', () => {
  let component: CountryAutocompleteComponent;
  let fixture: ComponentFixture<CountryAutocompleteComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ CountryAutocompleteComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CountryAutocompleteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
