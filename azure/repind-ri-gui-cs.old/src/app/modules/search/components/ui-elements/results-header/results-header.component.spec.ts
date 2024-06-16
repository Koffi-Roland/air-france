import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { ResultsHeaderComponent } from './results-header.component';

describe('ResultsHeaderComponent', () => {
  let component: ResultsHeaderComponent;
  let fixture: ComponentFixture<ResultsHeaderComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ ResultsHeaderComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ResultsHeaderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
