import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';
import { UntypedFormBuilder, FormGroup } from '@angular/forms';
import { TranslateModule } from '@ngx-translate/core';
import { ToggleSliderOption } from 'src/app/shared/models/contents/toggle-slider-option';

import { TogglesliderComponent } from './toggleslider.component';

describe('TogglesliderComponent', () => {
  let component: TogglesliderComponent;
  let fixture: ComponentFixture<TogglesliderComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [TogglesliderComponent],
      imports: [TranslateModule.forRoot()]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TogglesliderComponent);
    fixture.componentInstance.group = new UntypedFormBuilder().group({ testControl: [] });
    fixture.componentInstance.field = { type: "", name: 'testControl', options: [new ToggleSliderOption("", "")] };
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
