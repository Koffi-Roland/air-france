import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';
import { UntypedFormBuilder } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { TranslateModule } from '@ngx-translate/core';
import { GroupService } from 'src/app/core/services/group.service';
import { ValidatorsCustom } from 'src/app/shared/widgets/validators/validators-custom.component';

import { GroupProductEditComponent } from './group-product-edit.component';

describe('GroupProductEditComponent', () => {
  let component: GroupProductEditComponent;
  let fixture: ComponentFixture<GroupProductEditComponent>;

  let mockConfigGroupService = {
    products: [],
    groups: []
  }

  beforeEach(waitForAsync(() => {


    TestBed.configureTestingModule({
      declarations: [GroupProductEditComponent],
      imports: [HttpClientModule, TranslateModule.forRoot()],
      providers: [{ provide: GroupService, useValue: mockConfigGroupService }, UntypedFormBuilder, ValidatorsCustom, MatSnackBar, { provide: MAT_DIALOG_DATA, useValue: { options: [], element: [] } }, { provide: MatDialogRef, useValue: {} }]

    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GroupProductEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
