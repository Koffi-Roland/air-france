import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { ResourceDetailsCardComponent } from './resource-details-card.component';

describe('ResourceDetailsCardComponent', () => {
  let component: ResourceDetailsCardComponent;
  let fixture: ComponentFixture<ResourceDetailsCardComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ ResourceDetailsCardComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ResourceDetailsCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
