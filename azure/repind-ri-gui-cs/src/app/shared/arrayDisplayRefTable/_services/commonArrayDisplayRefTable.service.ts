import { Injectable } from '@angular/core';
import { ArrayDisplayRefTableActionOptionConfig } from '../_models/ArrayDisplayRefTableActionOption';
import { ArrayDisplayRefTableColumn } from '../_models/ArrayDisplayRefTableColumn';

@Injectable({
  providedIn: 'root'
})
export class CommonArrayDisplayRefTableService {

  constructor() { }

  /**
   * Method to get only not null key for an Object
   * @param data Return a tab of string with key available for Object
   */
  public static getNotNullKeyForObject(data: any, isAction: boolean) {
    const displayedColumns = Array<ArrayDisplayRefTableColumn>();
    for (const key of Object.keys(data)) {
      if ((data[key] && !(data[key] instanceof Array)) || (data[key] instanceof Array && data[key].length > 0)) {
        displayedColumns.push(new ArrayDisplayRefTableColumn(key, key, true));
      }
    }
    if (isAction) {
      displayedColumns.push(new ArrayDisplayRefTableColumn('action', 'Actions', true));
    }
    return displayedColumns;
  }

  /**
   * find value for a given key
   *
   * @param key of ArrayDisplayRefTableActionOptionConfig
   * @param configList ArrayDisplayRefTableActionOptionConfig[]
   * @returns 'value' corresponding to key OR 'undefined'
   */
  public static getValueByKey(key: string, configList: Array<ArrayDisplayRefTableActionOptionConfig>): any {
    return configList.find(e => e.key === key)?.value;
  }

}
