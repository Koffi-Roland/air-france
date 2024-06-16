import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { ResourceTabsComponent } from './resource-tabs.component';

describe('ResourceTabsComponent', () => {
  let component: ResourceTabsComponent;
  let fixture: ComponentFixture<ResourceTabsComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ ResourceTabsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ResourceTabsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
