import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { FilterExpansionPanelComponent } from './filter-expansion-panel.component';

describe('FilterExpansionPanelComponent', () => {
  let component: FilterExpansionPanelComponent;
  let fixture: ComponentFixture<FilterExpansionPanelComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ FilterExpansionPanelComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FilterExpansionPanelComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
