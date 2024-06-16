import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { ResultsViewPickerComponent } from './results-view-picker.component';

describe('ResultsViewPickerComponent', () => {
  let component: ResultsViewPickerComponent;
  let fixture: ComponentFixture<ResultsViewPickerComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ ResultsViewPickerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ResultsViewPickerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
