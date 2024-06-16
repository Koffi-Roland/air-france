import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class MergeTransformService {

  /**
   * Transform data from list 1 and list 2 by:
   * - Regrouping them into block of two when compare is OK
   * - Transform the result into a generic dataset readable by UI component
   * @param firstDataList -> data from list 1 to compare with data from list 2 to be join in block of 2
   * @param secondDataList -> data from list 2 to compare with data from list 1 to be join in block of 2
   * @param pattern -> pattern to apply to transform specific data into generic one
   * @param compare -> method to compare data from list 1 and list 2
   */
  public static transformToGenericDataByBlockOfTwo(firstDataList: any, secondDataList: any, pattern: any, compare: Function): any {
    const result = [];
    if (firstDataList && secondDataList) {
      for (let i = 0; i < firstDataList.length; i++) {
        for (let u = 0; u < secondDataList.length; u++) {
          if (compare(firstDataList[i][pattern.name], secondDataList[u][pattern.name])) {
            result.push({
              'blocSource': pattern.transformDataToMergeAccordionData(firstDataList[i][pattern.name], pattern, firstDataList[i].selected),
              'blocTarget': pattern.transformDataToMergeAccordionData(secondDataList[u][pattern.name], pattern, secondDataList[u].selected)
            });
            secondDataList.splice(u, 1);
            firstDataList.splice(i, 1);
            i--;
            u--;
            break;
          }
        }
      }
      // We add all data from firstDataList with are not matching with one from secondDataList
      if (firstDataList.length > 0) {
        for (let i = 0; i < firstDataList.length; i++) {
          result.push({
            'blocSource': pattern.transformDataToMergeAccordionData(firstDataList[i][pattern.name], pattern, firstDataList[i].selected),
          });
        }
      }
      // Same for secondDataList data
      if (secondDataList.length > 0) {
        for (let i = 0; i < secondDataList.length; i++) {
          result.push({
            'blocTarget': pattern.transformDataToMergeAccordionData(secondDataList[i][pattern.name], pattern, secondDataList[i].selected),
          });
        }
      }
    }
    return result;
  }

    /**
   * Transform data from list to generic bloc:
   * - Transform the result into a generic dataset readable by UI component
   * @param firstDataList -> data from list to transform
   * @param pattern -> pattern to apply to transform specific data into generic one
   */
  public static transformToGenericDataByBlock(firstDataList: any, pattern: any): any {
    const result = [];
    if (firstDataList) {
      for (let i = 0; i < firstDataList.length; i++) {
            result.push(pattern.transformDataToMergeAccordionData(firstDataList[i][pattern.name], pattern, firstDataList[i].selected));
            firstDataList.splice(i, 1);
            i--;
          }
        }
      return result;
    }
}
