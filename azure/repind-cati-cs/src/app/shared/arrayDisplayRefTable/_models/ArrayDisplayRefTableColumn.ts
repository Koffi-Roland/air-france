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
   * Is this column displayed by default
   */
  private _isDisplayed: boolean;

  constructor(field: string, label: string, isDisplayed: boolean = true) {
    this._field = field;
    this._label = label;
    this._isDisplayed = isDisplayed;
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
