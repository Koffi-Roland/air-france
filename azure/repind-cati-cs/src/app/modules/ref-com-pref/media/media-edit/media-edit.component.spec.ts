import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';
import { MatDialogModule, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { TranslateModule } from '@ngx-translate/core';
import { ValidatorsCustom } from 'src/app/shared/widgets/validators/validators-custom.component';

import { MediaEditComponent } from './media-edit.component';

describe('MediaEditComponent', () => {
  let component: MediaEditComponent;
  let fixture: ComponentFixture<MediaEditComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [MediaEditComponent],
      imports: [MatDialogModule, TranslateModule.forRoot(), HttpClientModule],
      providers: [ValidatorsCustom, MatSnackBar, { provide: MAT_DIALOG_DATA, useValue: { options: [], element: [] } }, { provide: MatDialogRef, useValue: {} }]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MediaEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
