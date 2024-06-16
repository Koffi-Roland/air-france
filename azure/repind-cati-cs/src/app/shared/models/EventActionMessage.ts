import { throwError } from 'rxjs';

/**
 * Message class used to communicate between component when using component.
 * This kind of message contains datas and a type of message to know which action to do.
 */
export class EventActionMessage {

  /**
   * Type of communication Between component
   */
  private _message: string;

  /**
   * Data needed with the message.
   * Here 'any' is tolerated because we didn't know datas we can received
   */
  private _data: any;

  constructor(message: string, data: any = null) {
    if (EventActionMessageEnum.isMessageEnum(message)) {
      this._message = message;
    } else {
      throwError('NOT-A-VALID-MESSAGE');
    }
    this._data = data;
  }

  get message(): string {
    return this._message;
  }

  get data(): any {
    return this._data;
  }

  set setMessage(message: string) {
    if (EventActionMessageEnum.isMessageEnum(message)) {
      this._message = message;
    } else {
      throwError('NOT-A-VALID-MESSAGE');
    }
  }

  set setData(data: any) {
    this._data = data;
  }
}

/**
 * Enum to define authorized type of message
 */
export class EventActionMessageEnum {
  public static DELETE = 'DELETE';
  public static REFRESH = 'REFRESH';
  public static UPDATE = 'UPDATE';
  public static CREATE = 'CREATE';

  public static messageEnum: ReadonlyArray<string> = [
    EventActionMessageEnum.DELETE, EventActionMessageEnum.REFRESH, EventActionMessageEnum.UPDATE, EventActionMessageEnum.CREATE
  ];

  public static isMessageEnum(message: string) {
    return EventActionMessageEnum.messageEnum.indexOf(message) > -1;
  }
}

