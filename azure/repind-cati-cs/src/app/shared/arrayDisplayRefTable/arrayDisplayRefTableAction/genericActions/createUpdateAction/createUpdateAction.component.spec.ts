/* tslint:disable:no-unused-variable */
import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';

import { CreateUpdateActionComponent } from './createUpdateAction.component';
import { MatDialogModule, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { TranslateModule } from '@ngx-translate/core';

describe('CreateUpdateActionComponent', () => {
  let component: CreateUpdateActionComponent;
  let fixture: ComponentFixture<CreateUpdateActionComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [CreateUpdateActionComponent],
      imports: [TranslateModule.forRoot(), MatDialogModule],
      providers: [{
        provide: MAT_DIALOG_DATA, useValue: {
          options: [],
          element: []
        }
      }, { provide: MatDialogRef, useValue: {} }]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateUpdateActionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
