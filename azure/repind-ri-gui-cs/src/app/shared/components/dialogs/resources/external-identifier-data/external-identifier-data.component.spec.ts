import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { ExternalIdentifierDataComponent } from './external-identifier-data.component';

describe('ExternalIdentifierDataComponent', () => {
  let component: ExternalIdentifierDataComponent;
  let fixture: ComponentFixture<ExternalIdentifierDataComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ ExternalIdentifierDataComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ExternalIdentifierDataComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
