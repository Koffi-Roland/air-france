import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { SearchResultsArrayComponent } from './search-results-array.component';

describe('SearchResultsArrayComponent', () => {
  let component: SearchResultsArrayComponent;
  let fixture: ComponentFixture<SearchResultsArrayComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ SearchResultsArrayComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SearchResultsArrayComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
