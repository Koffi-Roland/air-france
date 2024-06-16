import { throwError } from 'rxjs';


/**
 * Enum to define authorized condition
 */
export class ConditionActionEnum {
  public static EQUALS = 'EQUALS';

  public static messageEnum: ReadonlyArray<string> = [
    ConditionActionEnum.EQUALS
  ];

  public static isMessageEnum(message: string) {
    return ConditionActionEnum.messageEnum.indexOf(message) > -1;
  }
}

/**
 * Enum to define authorized condition
 */
export class Condition {

  /**
   * Property to check
   */
  private _action: string;

  /**
   * Message to display
   */
  private _property: string;

  /**
   * Value to compare.
   * Here 'any' is tolerated because we didn't know datas we can received
   */
  private _value: any;

  constructor(property: string, action: string, value: any = null) {
    if (ConditionActionEnum.isMessageEnum(action)) {
      this._action = action;
    } else {
      throwError('NOT-A-VALID-MESSAGE');
    }
    this._action = action;
    this._property = property;
    this._value = value;
  }

  get action(): string {
    return this._action;
  }

  set setAction(action: string) {
    if (ConditionActionEnum.isMessageEnum(action)) {
      this._action = action;
    } else {
      throwError('NOT-A-VALID-MESSAGE');
    }
  }

  get property(): string {
    return this._property;
  }

  set setProperty(property: any) {
    this._property = property;
  }

  get value(): any {
    return this._value;
  }

  set setValue(value: any) {
    this._value = value;
  }

}


