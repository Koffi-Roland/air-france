import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ToggleSliderComponent } from './toggle-slider.component';

describe('ToggleSliderComponent', () => {
  let component: ToggleSliderComponent;
  let fixture: ComponentFixture<ToggleSliderComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ToggleSliderComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ToggleSliderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
