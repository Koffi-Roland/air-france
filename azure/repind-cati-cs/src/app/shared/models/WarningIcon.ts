import { throwError } from 'rxjs';
import { Condition } from './Condition';

/**
 * Warning class to use in WarningIcon component
 */
export class WarningIcon {

  /**
   * Property to check
   */
  private _condition: Condition;

  /**
   * Message to display
   */
  private _message: string;

  constructor(message: string, condition: Condition) {
    this._message = message;
    this._condition = condition;
  }

  get message(): string {
    return this._message;
  }

  get condition(): Condition {
    return this._condition;
  }

  set setMessage(message: string) {
    this._message = message;
  }

  set setCondition(condition: Condition) {
      this._condition = condition;
  }
}

