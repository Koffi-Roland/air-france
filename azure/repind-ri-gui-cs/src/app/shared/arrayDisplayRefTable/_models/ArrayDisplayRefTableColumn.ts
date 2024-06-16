/**
 * Class to define column to display in ArrayDisplayRefTable
 */
export class ArrayDisplayRefTableColumn {
  /**
   * Name of column to display
   */
  private _field: string;

  /**
   * Label to translate for the column to display
   */
  private _label: string;

  /**
   * Enable cell css class
   */

  private _enableCellClass: (element: any) => boolean | undefined;

  /**
   * Cell css class
   */
  private _cellClass: string | undefined;

  /**
   * Enable tooltip
   */

  private _enableTooltip: (element: any) => boolean | undefined;

  /**
   * Tool tip key translate
   */
  private _toolTip: string | undefined;

  /**
   * Is this column displayed by default
   */
  private _isDisplayed: boolean;


  constructor(
    field: string,
    label: string,
    isDisplayed: boolean = true,
    cellClass?: string,
    enableCellClass?: (element: any) => boolean,
    toolTip?: string,
    enableTooltip?: (element: any) => boolean) {
    this._field = field;
    this._label = label;
    this._isDisplayed = isDisplayed;
    this._enableCellClass = enableCellClass;
    this._cellClass = cellClass;
    this._enableTooltip = enableTooltip;
    this._toolTip = toolTip;
  }


  get cellClass(): string | undefined {
    return this._cellClass;
  }

  get tooltip(): string | undefined {
    return this._toolTip;
  }

  public enableCellClass(element: any): boolean {
    return !!this._enableCellClass && this._enableCellClass(element);
  }

  public enableTooltip(element: any): boolean {
    return !!this._enableTooltip && this._enableTooltip(element);
  }

  get field(): string {
    return this._field;
  }

  set setField(field: string) {
    this._field = field;
  }

  get label(): string {
    return this._label;
  }

  set setLabel(label: string) {
    this._label = label;
  }

  get isDisplayed(): boolean {
    return this._isDisplayed;
  }

  set setIsDisplayed(isDisplayed: boolean) {
    this._isDisplayed = isDisplayed;
  }
}
