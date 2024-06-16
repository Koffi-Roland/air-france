import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { PreferenceDataListComponent } from './preference-data-list.component';

describe('PreferenceDataListComponent', () => {
  let component: PreferenceDataListComponent;
  let fixture: ComponentFixture<PreferenceDataListComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ PreferenceDataListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PreferenceDataListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
