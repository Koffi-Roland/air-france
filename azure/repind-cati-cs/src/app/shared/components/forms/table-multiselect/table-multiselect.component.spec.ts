import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';
import { UntypedFormBuilder } from '@angular/forms';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { TranslateModule } from '@ngx-translate/core';
import { MaterialModule } from 'src/app/modules/material/material.module';
import { SharedModule } from 'src/app/shared/shared.module';

import { TableMultiselectComponent } from './table-multiselect.component';

describe('TableMultiselectComponent', () => {
  let component: TableMultiselectComponent;
  let fixture: ComponentFixture<TableMultiselectComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [TableMultiselectComponent],
      imports: [MaterialModule, TranslateModule.forRoot(), SharedModule, BrowserAnimationsModule]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TableMultiselectComponent);
    fixture.componentInstance.group = new UntypedFormBuilder().group({ hello: [] });
    fixture.componentInstance.field = {
      type: '', name: 'hello', label: '', options: [{ code: 'a', second: 'test', third: 'test', fourth: 0 },
      { code: 'b', second: 'test', third: 'test', fourth: 0 },
      { code: 'c', second: 'test', third: 'test', fourth: 0 },
      { code: 'd', second: 'test', third: 'test', fourth: 0 }], tableMultiselectIdName: ''
    };
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
