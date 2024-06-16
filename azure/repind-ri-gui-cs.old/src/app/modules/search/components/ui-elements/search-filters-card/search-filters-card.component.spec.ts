import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { SearchFiltersCardComponent } from './search-filters-card.component';

describe('SearchFiltersCardComponent', () => {
  let component: SearchFiltersCardComponent;
  let fixture: ComponentFixture<SearchFiltersCardComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ SearchFiltersCardComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SearchFiltersCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
