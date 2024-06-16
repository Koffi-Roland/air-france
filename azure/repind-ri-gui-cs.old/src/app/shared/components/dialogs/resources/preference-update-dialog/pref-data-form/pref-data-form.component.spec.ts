import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { PrefDataFormComponent } from './pref-data-form.component';

describe('PrefDataFormComponent', () => {
  let component: PrefDataFormComponent;
  let fixture: ComponentFixture<PrefDataFormComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ PrefDataFormComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PrefDataFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
