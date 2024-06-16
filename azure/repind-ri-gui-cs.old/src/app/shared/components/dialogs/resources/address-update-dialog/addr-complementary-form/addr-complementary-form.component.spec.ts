import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { AddrComplementaryFormComponent } from './addr-complementary-form.component';

describe('AddrComplementaryFormComponent', () => {
  let component: AddrComplementaryFormComponent;
  let fixture: ComponentFixture<AddrComplementaryFormComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ AddrComplementaryFormComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddrComplementaryFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
