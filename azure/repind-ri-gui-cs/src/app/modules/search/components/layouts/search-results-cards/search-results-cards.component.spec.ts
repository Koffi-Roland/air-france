import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { SearchResultsCardsComponent } from './search-results-cards.component';

describe('SearchResultsCardsComponent', () => {
  let component: SearchResultsCardsComponent;
  let fixture: ComponentFixture<SearchResultsCardsComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ SearchResultsCardsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SearchResultsCardsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
