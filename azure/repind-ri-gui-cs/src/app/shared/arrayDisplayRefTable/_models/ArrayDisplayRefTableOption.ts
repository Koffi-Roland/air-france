import {ArrayDisplayRefTableActionOption} from './ArrayDisplayRefTableActionOption';
import {WarningIcon} from '../../models/WarningIcon';
import {ArrayDisplayRefTableColumn} from './ArrayDisplayRefTableColumn';

/**
 * Class to define option to apply to a ArrayDisplayRefTable
 * For now, options are use for action section
 */
export class ArrayDisplayRefTableOption {
  /**
   * ID of data displayed, usefull to give pass it throught the component
   */
  private _id: string;

  /**
   * Title to display
   */
  private _title: string;

  /**
   * List of Option for the action section - ArrayDisplayRefTableActionOption
   */
  private _actionsOption: Array<ArrayDisplayRefTableActionOption>;

  /**
   * Warning option
   */
  private _warning: WarningIcon[];

  /**
   * Global action
   */
  private _globalActionsOption: Array<ArrayDisplayRefTableActionOption>;

  /**
   * Columns to display
   */
  private _columns: Array<ArrayDisplayRefTableColumn>;

  /**
   * Enable row css class
   */

  private _enableRowClass: (element: any) => boolean | undefined;

  /**
   * Row css class
   */
  private _rowClass: string | undefined;

  /**
   * Header css class
   */
  private _headerClass: string |undefined;


  constructor(
    id: string,
    title: string,
    actionsOption: Array<ArrayDisplayRefTableActionOption> = new Array<ArrayDisplayRefTableActionOption>(),
    globalActionsOption: Array<ArrayDisplayRefTableActionOption> = new Array<ArrayDisplayRefTableActionOption>(),
    columns: Array<ArrayDisplayRefTableColumn> = new Array<ArrayDisplayRefTableColumn>(),
    warning: WarningIcon[] = [],
    enableRowClass?: (row: any) => boolean,
    rowClass?: string,
    headerClass?: string) {
    this._id = id;
    this._title = title;
    this._actionsOption = actionsOption;
    this._globalActionsOption = globalActionsOption;
    this._columns = columns;
    this._warning = warning;
    this._enableRowClass = enableRowClass;
    this._rowClass = rowClass;
    this._headerClass = headerClass;
  }

  public enableRowClass(row: any): boolean {
    return !!this._enableRowClass && this._enableRowClass(row);
  }

  get id(): string {
    return this._id;
  }

  get rowClass(): string {
    return this._rowClass;
  }

  get actionsOption(): Array<ArrayDisplayRefTableActionOption> {
    return this._actionsOption;
  }

  get warning(): WarningIcon[] {
    return this._warning;
  }

  get title(): string {
    return this._title;
  }

  get headerClass(): string | undefined {
    return this._headerClass;
  }

  set setTitle(title: string) {
    this._title = title;
  }

  set setWarning(warning: WarningIcon[]) {
    this._warning = warning;
  }

  set setId(id: string) {
    this._id = id;
  }

  set setActionsOption(actionsOption: Array<ArrayDisplayRefTableActionOption>) {
    this._actionsOption = actionsOption;
  }

  get globalActionsOption(): Array<ArrayDisplayRefTableActionOption> {
    return this._globalActionsOption;
  }

  set setGlobalActionsOption(globalActionsOption: Array<ArrayDisplayRefTableActionOption>) {
    this._globalActionsOption = globalActionsOption;
  }

  get columns(): Array<ArrayDisplayRefTableColumn> {
    return this._columns;
  }

  set setColumns(columns: Array<ArrayDisplayRefTableColumn>) {
    this._columns = columns;
  }

  set rowClass(rowClass: string) {
    this._rowClass = rowClass;
  }

  set enableRowClassSet(func: (element: any) => boolean) {
    this._enableRowClass = func;
  }

  set headerClass(headerClass: string) {
    this._headerClass = headerClass;
  }
}
