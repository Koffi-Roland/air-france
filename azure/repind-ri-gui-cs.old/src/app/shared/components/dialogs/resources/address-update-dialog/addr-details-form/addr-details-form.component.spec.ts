import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { AddrDetailsFormComponent } from './addr-details-form.component';

describe('AddrDetailsFormComponent', () => {
  let component: AddrDetailsFormComponent;
  let fixture: ComponentFixture<AddrDetailsFormComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ AddrDetailsFormComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddrDetailsFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
