import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { MulticriteriaSearchFormComponent } from './multicriteria-search-form.component';

describe('MulticriteriaSearchFormComponent', () => {
  let component: MulticriteriaSearchFormComponent;
  let fixture: ComponentFixture<MulticriteriaSearchFormComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ MulticriteriaSearchFormComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MulticriteriaSearchFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
