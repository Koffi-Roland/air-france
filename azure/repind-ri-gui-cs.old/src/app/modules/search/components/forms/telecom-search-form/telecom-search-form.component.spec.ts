import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { TelecomSearchFormComponent } from './telecom-search-form.component';

describe('TelecomSearchFormComponent', () => {
  let component: TelecomSearchFormComponent;
  let fixture: ComponentFixture<TelecomSearchFormComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ TelecomSearchFormComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TelecomSearchFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
