import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { SearchPickerComponent } from './search-picker.component';

describe('SearchPickerComponent', () => {
  let component: SearchPickerComponent;
  let fixture: ComponentFixture<SearchPickerComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ SearchPickerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SearchPickerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
