import { ArrayDisplayRefTableActionOption } from './ArrayDisplayRefTableActionOption';
import { WarningIcon } from '../../models/WarningIcon';
import { ArrayDisplayRefTableColumn } from './ArrayDisplayRefTableColumn';

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


    constructor(id: string, title: string,
      actionsOption: Array<ArrayDisplayRefTableActionOption> = new Array<ArrayDisplayRefTableActionOption>(),
      globalActionsOption: Array<ArrayDisplayRefTableActionOption> = new Array<ArrayDisplayRefTableActionOption>(),
      columns: Array<ArrayDisplayRefTableColumn> = new Array<ArrayDisplayRefTableColumn>(),
      warning: WarningIcon[] = []) {
      this._id = id;
      this._title = title;
      this._actionsOption = actionsOption;
      this._globalActionsOption = globalActionsOption;
      this._columns = columns;
      this._warning = warning;
    }

    get id(): string {
      return this._id;
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
}
