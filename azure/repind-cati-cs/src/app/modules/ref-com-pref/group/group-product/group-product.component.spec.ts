import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';
import { MatDialogModule } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute } from '@angular/router';
import { TranslateModule } from '@ngx-translate/core';

import { GroupProductComponent } from './group-product.component';

describe('GroupProductComponent', () => {
  let component: GroupProductComponent;
  let fixture: ComponentFixture<GroupProductComponent>;

  const fakeActivatedRoute = {
    snapshot: {
      data: {
        groupProduct: [],
      },

    }
  };

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [GroupProductComponent],
      imports: [MatDialogModule, TranslateModule.forRoot(), HttpClientModule],
      providers: [MatSnackBar, { provide: ActivatedRoute, useValue: fakeActivatedRoute }]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GroupProductComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
