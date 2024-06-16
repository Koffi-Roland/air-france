import { SelectionModel } from '@angular/cdk/collections';
import { AfterViewChecked, ChangeDetectorRef, Component, Input, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { ArrayDisplayRefTableColumn } from '../../arrayDisplayRefTable/_models/ArrayDisplayRefTableColumn';

@Component({
  selector: 'app-select-table',
  templateUrl: './select-table.component.html',
  styleUrls: ['./select-table.component.scss']
})
export class SelectTableComponent implements OnInit, AfterViewChecked {

  /**
   * columns name to display in the table modal (first name is 'SELECT' for the selectBoxes)
   */
  @Input()
  dataColumnNames?: ArrayDisplayRefTableColumn[];

  @Input()
  selectedIds?: string[];

  @Input()
  idName: string;

  @Input()
  listToFillRow: any[];

  columnNameDefinition: string[];

  dataSource: MatTableDataSource<any>;

  selection: SelectionModel<any>;

  filterValues: any;
  search: any;
  @ViewChild(MatSort) sort: MatSort;
  @ViewChild(MatPaginator) paginator: MatPaginator;

  /**
   * Only way to have paginator with *ngIf column, without this function, it doesn't works
   */
  @ViewChild(MatPaginator) set matPaginator(mp: MatPaginator) {
    this.paginator = mp;
    if (this.paginator) {
      this.dataSource.paginator = this.paginator;
    }
  }

  /**
   * Only way to have sorting with *ngIf column, without this function, it doesn't works
   */
  @ViewChild(MatSort) set content(content: any) {
    this.sort = content;
    if (this.sort) {
      this.dataSource.sort = this.sort;
    }
  }

  constructor(private cdRef: ChangeDetectorRef) { }

  ngAfterViewChecked(): void {
    this.cdRef.detectChanges();
  }

  ngOnInit(): void {

    this.filterValues = [];
    this.search = [];

    // display only indicated columns, or use all by default
    this.dataColumnNames = this.dataColumnNames ?? Object.keys(this.listToFillRow[0]).map(e => (new ArrayDisplayRefTableColumn(e, e)));

    // add select column for checklist
    this.dataColumnNames = [new ArrayDisplayRefTableColumn('select', 'SELECT')].concat(this.dataColumnNames);

    this.columnNameDefinition = this.dataColumnNames.map(e => e.field);

    this.selectedIds = this.selectedIds ?? [];

    this.selection = new SelectionModel<any>(true, []);

    this.setDataSource();

    this.checkByDefault();
  }

  ngAfterViewInit() {
    this.dataSource.sort = this.sort;
  }

  checkByDefault() {
    for (let index = 0; index < this.selectedIds.length; index++) {
      this.dataSource.data.forEach(row => {
        if (row[this.idName] === this.selectedIds[index]) {
          this.selection.select(row);
        }
      })
    }
  }

  applyFilter(search: any) {
    search.filterValue = search.filterValue.trim(); // Remove whitespace
    search.filterValue = search.filterValue.toLowerCase(); // Datasource defaults to lowercase matches

    this.filterValues[search.columnName] = search.filterValue;

    this.dataSource.filter = this.filterValues;
    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }

  setDataSource() {
    this.dataSource = new MatTableDataSource(this.listToFillRow);
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;

    this.dataSource.sortingDataAccessor = (item, property) => {
      switch (property) {
        case 'select':
          return this.selection.selected.map(s => s[this.idName]).includes(item[this.idName]);
        default: return item[property];
      }
    };

    this.dataSource.filterPredicate = (data: any, filters: any) => {

      const matchFilter = [];

      for (let i = 0; i < Object.keys(filters).length; i++) {

        const filterName = Object.keys(filters)[i];
        // check for null values!
        const filterNames = filterName.split('.');
        let val;
        if (filterNames.length > 1) {
          val = data[filterNames[0]][filterNames[1]] === null ? '' : data[filterNames[0]][filterNames[1]];
        } else {
          val = data[filterName] === null ? '' : data[filterName];
        }
        const filterValue = filters[filterName];
        matchFilter.push(val.toString().toLowerCase().includes(filterValue));
      }

      // Choose one
      return matchFilter.every(Boolean); // AND condition
      // return matchFilter.some(Boolean); // OR condition
    };

  }

}
