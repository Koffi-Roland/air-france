import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';
import { MatTable } from '@angular/material/table';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { TranslateModule } from '@ngx-translate/core';
import { MaterialModule } from 'src/app/modules/material/material.module';
import { ArrayDisplayRefTableColumn } from '../../arrayDisplayRefTable/_models/ArrayDisplayRefTableColumn';
import { SharedModule } from '../../shared.module';

import { SelectTableComponent } from './select-table.component';

describe('SelectTableComponent', () => {
  let component: SelectTableComponent;
  let fixture: ComponentFixture<SelectTableComponent>;
  const tableData = [
    { code: 'a', second: 'test', third: 'test', fourth: 0 },
    { code: 'b', second: 'test', third: 'test', fourth: 0 },
    { code: 'c', second: 'test', third: 'test', fourth: 0 },
    { code: 'd', second: 'test', third: 'test', fourth: 0 },
    { code: 'e', second: 'test', third: 'test', fourth: 0 },
    { code: 'f', second: 'test', third: 'test', fourth: 0 },
    { code: 'g', second: 'test', third: 'test', fourth: 0 },
    { code: 'h', second: 'test', third: 'test', fourth: 0 },
    { code: 'i', second: 'test', third: 'test', fourth: 0 },
    { code: 'j', second: 'test', third: 'test', fourth: 0 },
    { code: 'k', second: 'test', third: 'test', fourth: 0 },
    { code: 'l', second: 'test', third: 'test', fourth: 0 },
    { code: 'm', second: 'test', third: 'test', fourth: 0 },
    { code: 'n', second: 'test', third: 'test', fourth: 0 },
    { code: 'o', second: 'test', third: 'test', fourth: 0 },
  ];

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      imports: [MaterialModule, TranslateModule.forRoot(), BrowserAnimationsModule, SharedModule],
      declarations: [SelectTableComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SelectTableComponent);
    component = fixture.componentInstance;
    component.idName = 'code';
    component.listToFillRow = [...tableData];
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should have 15 element displayed in table', () => {
    expect(component.dataSource.data.length).toEqual(15);
  });

  it('should display all fields when not mentioned ', () => {
    const tableHeaders: HTMLElement[] = fixture.nativeElement.querySelectorAll('mat-header-cell div span');
    const columnNames = [...tableHeaders].map(e => e.textContent.trim());
    expect(columnNames.length).toEqual(5);
    expect(columnNames.includes('SELECT')).toBeTrue();
    expect(columnNames.includes('code')).toBeTrue();
    expect(columnNames.includes('second')).toBeTrue();
    expect(columnNames.includes('third')).toBeTrue();
    expect(columnNames.includes('fourth')).toBeTrue();
  });

  it('should display only mentioned fields', () => {
    const fixtureUnit = TestBed.createComponent(SelectTableComponent);
    const componentUnit = fixtureUnit.componentInstance;
    componentUnit.idName = 'code';
    componentUnit.listToFillRow = [...tableData];
    componentUnit.dataColumnNames = [new ArrayDisplayRefTableColumn('second', 'SECOND'), new ArrayDisplayRefTableColumn('third', 'THIRD')];
    fixtureUnit.detectChanges();

    const tableHeaders: HTMLElement[] = fixtureUnit.nativeElement.querySelectorAll('mat-header-cell div span');
    const columnNames = [...tableHeaders].map(e => e.textContent.trim());

    expect(columnNames.length).toEqual(3);
    expect(columnNames.includes('CODE')).toBeFalse();
    expect(columnNames.includes('SECOND')).toBeTrue();
    expect(columnNames.includes('THIRD')).toBeTrue();
    expect(columnNames.includes('FOURTH')).toBeFalse();
  });

  it('should have nothing selected when selectedIds is not provided', () => {
    expect(component.selection.selected.length).toEqual(0);
  });

  it('should have all rows indicated in selectedIds checked', () => {
    const fixtureUnit = TestBed.createComponent(SelectTableComponent);
    const componentUnit = fixtureUnit.componentInstance;
    componentUnit.idName = 'code';
    componentUnit.listToFillRow = [...tableData];
    componentUnit.selectedIds = ['g', 'd'];
    fixtureUnit.detectChanges();

    const selected = componentUnit.selection.selected.map(e => e.code);

    expect(selected.length).toEqual(2);
    expect(selected.includes('g')).toBeTrue();
    expect(selected.includes('d')).toBeTrue();
  });

  it('should have all checked rows remaining checked when table page is changed', () => {
    let rows = fixture.nativeElement.querySelectorAll('div > mat-table > mat-row');
    const btnNext = fixture.nativeElement.querySelector('div > mat-paginator > div > div > div.mat-paginator-range-actions > button.mat-focus-indicator.mat-paginator-navigation-next.mat-icon-button.mat-button-base');
    const btnPrevious = fixture.nativeElement.querySelector('div > mat-paginator > div > div > div.mat-paginator-range-actions > button.mat-focus-indicator.mat-paginator-navigation-previous.mat-icon-button.mat-button-base');

    expect(component.selection.selected.length).toEqual(0);

    // select row with code 'd'
    rows.forEach(element => {
      if (element.textContent.includes(' d ')) {
        const selectBox = element.querySelector('mat-checkbox input[type="checkbox"]');
        selectBox.click();
      }
    });

    expect(component.selection.selected.length).toEqual(1);

    // go to page 2
    btnNext.click();
    fixture.detectChanges();
    rows = fixture.nativeElement.querySelectorAll('div > mat-table > mat-row');

    // select row with code 'g'
    rows.forEach(element => {
      if (element.textContent.includes(' g ')) {
        const selectBox = element.querySelector('mat-checkbox input[type="checkbox"]');
        selectBox.click();
      }
    });

    expect(component.selection.selected.length).toEqual(2);

    // go to page 1
    btnPrevious.click();
    fixture.detectChanges();

    const selected = component.selection.selected.map(e => e.code);

    // row 'g' is still checked
    expect(selected.includes('g')).toBeTrue();

    // row 'd' is still checked
    expect(selected.includes('d')).toBeTrue();
  });

  it('should display only searched data', () => {
    const codeSearch = fixture.nativeElement.querySelectorAll('div > mat-table > mat-footer-row > mat-footer-cell.mat-footer-cell.cdk-footer-cell.cdk-column-code.mat-column-code.ng-star-inserted > search-by-column');

    // click on search of code column
    codeSearch[0].click();
    const inputField: HTMLInputElement = codeSearch[0].querySelector('input');

    // simulate user writing 'b' in input search field
    inputField.value = 'b'
    inputField.dispatchEvent(new Event('input'));
    fixture.detectChanges();

    let rows = fixture.nativeElement.querySelectorAll('div > mat-table > mat-row');
    expect(rows.length).toEqual(1);

    // simulate user erasing in input search field
    inputField.value = ''
    inputField.dispatchEvent(new Event('input'));
    fixture.detectChanges();

    rows = fixture.nativeElement.querySelectorAll('div > mat-table > mat-row');
    expect(rows.length).toEqual(5);
  });

  it('should display selectedIds rows first', () => {
    const fixtureUnit = TestBed.createComponent(SelectTableComponent);
    const componentUnit = fixtureUnit.componentInstance;
    componentUnit.idName = 'code';
    componentUnit.listToFillRow = [...tableData];
    componentUnit.selectedIds = ['g', 'd'];
    fixtureUnit.detectChanges();

    let firstRow = fixtureUnit.nativeElement.querySelector('div > mat-table > mat-row:nth-child(2)');
    let secondRow = fixtureUnit.nativeElement.querySelector('div > mat-table > mat-row:nth-child(3)');

    expect(firstRow.textContent.includes(' d ')).toBeTrue();
    expect(secondRow.textContent.includes(' g ')).toBeTrue();

  });

});
