import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { ResourceGeneralInfoFormComponent } from './resource-general-info-form.component';

describe('ResourceGeneralInfoFormComponent', () => {
  let component: ResourceGeneralInfoFormComponent;
  let fixture: ComponentFixture<ResourceGeneralInfoFormComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ ResourceGeneralInfoFormComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ResourceGeneralInfoFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
