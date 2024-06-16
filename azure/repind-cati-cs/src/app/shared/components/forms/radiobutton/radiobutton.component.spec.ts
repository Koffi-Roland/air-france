import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';
import { TranslateModule } from '@ngx-translate/core';

import { RadiobuttonComponent } from './radiobutton.component';

describe('RadiobuttonComponent', () => {
  let component: RadiobuttonComponent;
  let fixture: ComponentFixture<RadiobuttonComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [RadiobuttonComponent],
      imports: [TranslateModule.forRoot()]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RadiobuttonComponent);
    fixture.componentInstance.field = { type: '', name: '', label: '' };
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
