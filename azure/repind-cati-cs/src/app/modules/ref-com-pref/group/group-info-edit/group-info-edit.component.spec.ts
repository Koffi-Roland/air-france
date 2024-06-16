import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';
import { UntypedFormBuilder } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { TranslateModule } from '@ngx-translate/core';
import { ValidatorsCustom } from 'src/app/shared/widgets/validators/validators-custom.component';

import { GroupInfoEditComponent } from './group-info-edit.component';

describe('GroupInfoEditComponent', () => {
  let component: GroupInfoEditComponent;
  let fixture: ComponentFixture<GroupInfoEditComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [GroupInfoEditComponent],
      imports: [HttpClientModule, TranslateModule.forRoot()],
      providers: [UntypedFormBuilder, ValidatorsCustom, MatSnackBar, { provide: MAT_DIALOG_DATA, useValue: { options: [], element: [] } }, { provide: MatDialogRef, useValue: {} }]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GroupInfoEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
