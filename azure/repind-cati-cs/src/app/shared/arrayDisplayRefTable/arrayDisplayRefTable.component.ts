import { Component, OnInit, Input, ViewChild } from '@angular/core';
import { DataSource } from '@angular/cdk/table';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { ArrayDisplayRefTableActionOption } from './_models/ArrayDisplayRefTableActionOption';
import { ArrayDisplayRefTableOption } from './_models/ArrayDisplayRefTableOption';
import { CommonArrayDisplayRefTableService } from './_services/commonArrayDisplayRefTable.service';
import { EventActionService } from './_services/eventAction.service';
import { EventActionMessageEnum } from '../models/EventActionMessage';
import {
  CreateUpdateActionComponent
} from '../arrayDisplayRefTable/arrayDisplayRefTableAction/genericActions/createUpdateAction/createUpdateAction.component';
import { ArrayDisplayRefTableColumn } from './_models/ArrayDisplayRefTableColumn';


@Component({
  selector: 'app-array-display-ref-table',
  templateUrl: './arrayDisplayRefTable.component.html',
  styleUrls: ['./arrayDisplayRefTable.component.scss']
})
export class ArrayDisplayRefTableComponent implements OnInit {

  /**
   * All data to display in table
   */
  @Input()
  public data: any;

  /**
   * Option for table
   */
  @Input()
  public option: ArrayDisplayRefTableOption;

  /**
   * Title to display
   */
  public title: string;

  /**
   * Datasource of table, use for display data
   */
  public dataSource: MatTableDataSource<any>;

  /**
   * All columns to available for each data for the table
   */
  public columns: Array<ArrayDisplayRefTableColumn>;

  /**
   * Only columns to display for the table
   */
  public displayedColumns: Array<ArrayDisplayRefTableColumn>;

  /**
   * Columns displayed definition
   */
  public displayedColumnsDefinition: Array<string>;

  /**
   * Filter definition
   */
  public filterDefinition: Array<string>;


  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;
  search: any;
  filterValues: any;
  displaySearch = false;

  /**
   * Only way to have sorting with *ngIf column, without this function, it's doesn't works
   */
  @ViewChild(MatSort) set content(content: any) {
    this.sort = content;
    if (this.sort) {
      this.dataSource.sort = this.sort;
    }
  }

  /**
   * Only way to have paginator with *ngIf column, without this function, it's doesn't works
   */
  @ViewChild(MatPaginator) set matPaginator(mp: MatPaginator) {
    this.paginator = mp;
    if (this.paginator) {
      this.dataSource.paginator = this.paginator;
    }
  }

  constructor(private eventActionService: EventActionService, public dialog: MatDialog) {
    // Watch event of type REFRESH for refreshing datas when needed
    eventActionService.actionExecuted$.subscribe(
      message => {
        if (message.message === EventActionMessageEnum.REFRESH) {
          this.dataSource.data = message.data;
        }
      });
  }

  ngOnInit() {
    if (this.data) {
      this.filterValues = [];
      this.search = {};
      if (this.option && this.option.title) {
        this.title = this.option.title;
      }
      // Create the datasource with data provided
      this.dataSource = new MatTableDataSource(this.data);
      // Get all column available for each data (all key for an JS object pass)
      if (this.option && this.option.columns && this.option.columns.length > 0) {
        this.displayedColumns = new Array<ArrayDisplayRefTableColumn>();
        this.columns = this.option.columns;
        this.columns.forEach(column => {
          if (column.isDisplayed) {
            this.displayedColumns.push(column);
          }
        });
        if (this.option.actionsOption.length > 0) {
          this.displayedColumns.push(new ArrayDisplayRefTableColumn('action', 'ACTION', true));
        }
      } else {
        this.columns = new Array<ArrayDisplayRefTableColumn>();
        const columnsKey = Object.keys(this.dataSource.data[0]);
        columnsKey.forEach(column => {
          this.columns.push(new ArrayDisplayRefTableColumn(column, column, true));
        });
        this.displayedColumns = CommonArrayDisplayRefTableService.getNotNullKeyForObject(
          this.dataSource.data[0], this.option.actionsOption.length > 0);
      }
      this.displayedColumnsDefinition = this.displayedColumns.map(column => column.field);
      this.filterDefinition = this.displayedColumns.map(column => 'filter-' + column.field);
      // Selected column to display
      // Initialise the paginator
      this.dataSource.paginator = this.paginator;
      // Initialise the sort for each column
      this.dataSource.sort = this.sort;
      this.dataSource.sortingDataAccessor = (data, sortHeaderId) => {
        const sortNames = sortHeaderId.split('.');

        if (sortNames.length > 1) {
          return data[sortNames[0]][sortNames[1]].toLocaleLowerCase();
        } else {
          if (data[sortHeaderId]) {
            return data[sortHeaderId].toString().toLocaleLowerCase();
          } else {
            return data[sortHeaderId];
          }
        }
      };

      this.dataSource.filterPredicate = (data: Element, filters: string) => {

        const matchFilter = [];

        for (let i = 0; i < Object.keys(filters).length; i++) {

          const filterName = Object.keys(filters)[i];
          // check for null values!
          const val = data[filterName] === null ? '' : data[filterName];
          const filterValue = filters[filterName];
          if (val) {
            matchFilter.push(val.toString().toLowerCase().includes(filterValue));
          } else {
            matchFilter.push('');
          }
        }

        // Choose one
        return matchFilter.every(Boolean); // AND condition
        // return matchFilter.some(Boolean); // OR condition
      };

    }
  }

  /**
   * Method to hide a column in the display
   * @param field, Column to hide
   */
  public hideColumn(field): void {
    const column = this.displayedColumns.find(col => col.field === field);
    if (column) {
      const index = this.displayedColumns.indexOf(column);
      this.displayedColumns.splice(index, 1);
      this.displayedColumnsDefinition.splice(index, 1);
      this.filterDefinition.splice(index, 1);
    } else {
      const columnToAdd = this.columns.find(col => col.field === field);
      let start = this.columns.indexOf(columnToAdd);
      if (start >= this.displayedColumns.length) {
        start = this.displayedColumns.length - 1;
      }
      this.displayedColumns.splice(start, 0, columnToAdd);
      this.displayedColumnsDefinition.splice(start, 0, columnToAdd.field);
      this.filterDefinition.splice(start, 0, 'filter-' + columnToAdd.field);
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

  /**
   * Method to execute the action, here open a modal and pass mandatory datas
   * @param option Option to provide to the action
   */
  public exec(option: ArrayDisplayRefTableActionOption) {
    if (option.modal) {
      const dialogRef = this.dialog.open(option.modal, {
        width: '75vw',
        data: { element: this.data, options: option.configs, label: option.label },
        autoFocus: false
      });
    }
  }
}
