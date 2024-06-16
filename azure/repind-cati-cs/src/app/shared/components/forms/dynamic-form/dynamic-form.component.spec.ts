import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';
import { UntypedFormBuilder } from '@angular/forms';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { TranslateModule } from '@ngx-translate/core';
import { MaterialModule } from 'src/app/modules/material/material.module';
import { AutoFill } from 'src/app/shared/models/contents/autofill';
import { OptionItem } from 'src/app/shared/models/contents/option-item';
import { FieldValueTuple } from 'src/app/shared/models/forms/field-value-tuple';
import { SharedModule } from 'src/app/shared/shared.module';

import { DynamicFormComponent } from './dynamic-form.component';

describe('DynamicFormComponent', () => {
  let component: DynamicFormComponent;
  let fixture: ComponentFixture<DynamicFormComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [DynamicFormComponent],
      providers: [UntypedFormBuilder],
      imports: [SharedModule, MaterialModule, TranslateModule.forRoot(), BrowserAnimationsModule]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DynamicFormComponent);
    component = fixture.componentInstance;
    component.fields = [
      {
        type: 'select',
        name: 'a',
        label: 'A',
        options: [new OptionItem("first"), new OptionItem("second")],
        autoFill: new AutoFill(new Map<string, FieldValueTuple[]>([
          ['first', [{ field: 'b', value: 1 }, { field: 'c', value: 3 }]],
        ]), ['b', 'c'])
      },
      {
        type: 'input',
        name: 'b',
        label: 'B'
      },
      {
        type: 'input',
        name: 'c',
        label: 'C'
      },
      {
        type: 'select',
        name: 'd',
        label: 'D',
        options: [new OptionItem("first"), new OptionItem("second")],
        autoFill: new AutoFill(new Map<string, FieldValueTuple[]>([
          ['second', [{ field: 'e', value: 2 }]],
        ]), ['e'])
      },
      {
        type: 'input',
        name: 'e',
        label: 'E'
      },
    ]
    fixture.autoDetectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should autofill the indicated attributes', () => {
    // select 'first' of a
    component.form.patchValue({ a: 'first' });
    // select 'second' of d
    component.form.patchValue({ d: 'second' });
    // value of b = 1, c = 3, e = 2
    const val = component.form.value;
    expect(val.b).toEqual(1);
    expect(val.c).toEqual(3);
    expect(val.e).toEqual(2);
  });

  it('should hide the autofilled value', () => {

    // select 'first' of a
    component.form.patchValue({ a: 'first' });
    // b and c are hidden
    expect(component.fields.find(e => e.name === 'b').isHidden).toBeTrue();
    expect(component.fields.find(e => e.name === 'c').isHidden).toBeTrue();
    // select 'second' of d
    component.form.patchValue({ d: 'second' });
    // e is hidden
    expect(component.fields.find(e => e.name === 'b').isHidden).toBeTrue();
    expect(component.fields.find(e => e.name === 'c').isHidden).toBeTrue();
    expect(component.fields.find(e => e.name === 'e').isHidden).toBeTrue();
  });

  it('should empty field when there is no autofill from filler field', () => {
    // select 'first' of a
    component.form.patchValue({ a: 'first' });

    // value of b = 1, c = 3, e = ''
    let val = component.form.value;
    expect(val.b).toEqual(1);
    expect(val.c).toEqual(3);
    expect(val.e).toBeUndefined();

    // select 'second' of a
    component.form.patchValue({ a: 'second' });

    val = component.form.value;
    // value of b = '' , c = '' 
    expect(val.b).toBeNull();
    expect(val.c).toBeNull();
    expect(val.e).toBeUndefined();

    // select 'second' of d
    component.form.patchValue({ d: 'second' });
    val = component.form.value;

    // value of b = '', c = '', e = '2'
    expect(val.b).toBeNull();
    expect(val.c).toBeNull();
    expect(val.e).toEqual(2);

    // select 'first' of a
    component.form.patchValue({ a: 'first' });
    val = component.form.value;

    // value of b = 1 , c = 3, e = 2
    expect(val.b).toEqual(1);
    expect(val.c).toEqual(3);
    expect(val.e).toEqual(2);

  });


});
