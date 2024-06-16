import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ToggleSliderDetailsComponent } from './toggle-slider-details.component';

describe('ToggleSliderDetailsComponent', () => {
  let component: ToggleSliderDetailsComponent;
  let fixture: ComponentFixture<ToggleSliderDetailsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ToggleSliderDetailsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ToggleSliderDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
