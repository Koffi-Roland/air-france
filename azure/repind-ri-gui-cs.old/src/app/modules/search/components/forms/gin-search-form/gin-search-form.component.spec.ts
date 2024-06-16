import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { GinSearchFormComponent } from './gin-search-form.component';

describe('GinSearchFormComponent', () => {
  let component: GinSearchFormComponent;
  let fixture: ComponentFixture<GinSearchFormComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ GinSearchFormComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GinSearchFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
