import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { ResourcesNotFoundDialogComponent } from './resources-not-found-dialog.component';

describe('ResourcesNotFoundDialogComponent', () => {
  let component: ResourcesNotFoundDialogComponent;
  let fixture: ComponentFixture<ResourcesNotFoundDialogComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ ResourcesNotFoundDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ResourcesNotFoundDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
