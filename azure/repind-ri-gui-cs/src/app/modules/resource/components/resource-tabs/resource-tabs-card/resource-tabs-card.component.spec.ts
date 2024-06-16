import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { ResourceTabsCardComponent } from './resource-tabs-card.component';

describe('ResourceTabsCardComponent', () => {
  let component: ResourceTabsCardComponent;
  let fixture: ComponentFixture<ResourceTabsCardComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ ResourceTabsCardComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ResourceTabsCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
