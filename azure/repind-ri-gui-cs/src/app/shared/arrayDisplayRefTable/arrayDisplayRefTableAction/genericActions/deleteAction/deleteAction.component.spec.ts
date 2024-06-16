/* tslint:disable:no-unused-variable */
import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';

import { DeleteActionComponent } from './deleteAction.component';
import { TranslateModule } from '@ngx-translate/core';
import { MatDialogModule, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

describe('DeleteActionComponent', () => {
  let component: DeleteActionComponent;
  let fixture: ComponentFixture<DeleteActionComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [DeleteActionComponent],
      imports: [TranslateModule.forRoot(), MatDialogModule],
      providers: [{ provide: MAT_DIALOG_DATA, useValue: { options: [], element: [] } }, { provide: MatDialogRef, useValue: {} }]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DeleteActionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
