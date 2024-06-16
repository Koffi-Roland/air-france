import {
  Injectable
} from '@angular/core';
import {
  UntypedFormControl
} from '@angular/forms';


@Injectable()
export class ValidatorsCustom {

  static list: Array<any>;

  static listCombination: Array<any>;

  static isUnique(caseSensistive: boolean = false) {
    return (control: UntypedFormControl) => {
      if (ValidatorsCustom.list && control.value) {
        const value = caseSensistive ? control.value : control.value.toUpperCase();
        const isUnique = ValidatorsCustom.list.indexOf(value) === -1;
        return isUnique ? null : {
          isUnique: true
        };
      }
      return null;
    };
  }

  static isCombinationUnique() {
    return (control: UntypedFormControl) => {
      if (this.listCombination && control) {
        const isCombinationUnique = this.listCombination.filter(x => x.type === control.value.type &&
          x.domain === control.value.domain &&
          x.groupType === control.value.groupType)[0];
        return isCombinationUnique ? {
          isCombinationUnique: true
        } : null;
      }
      return null;
    };
  }

  isCombinationWithMarketUnique(list: any) {
    return (control: UntypedFormControl) => {
      if (list) {
        const isCombinationWithMarketUnique = list.filter(x => x.comType.codeType === control.value.comType.codeType &&
          x.domain.codeDomain === control.value.domain.codeDomain &&
          x.comGroupeType.codeGType === control.value.comGroupeType.codeGType)[0];
        return isCombinationWithMarketUnique ? {
          isCombinationWithMarketUnique: true
        } : null;
      }
      return null;
    };
  }

  isDefaultLanguagesValid() {
    return (control: UntypedFormControl) => {
      if (control.value.domain && control.value.domain !== 'P' && control.value.domain !== 'U') {
        if (control.value.domain === 'F') {
          if (control.value.comType === 'FB_ESS') {

            if (control.value.defaultLanguages.length === 0) {
              return {
                isDefaultLanguagesValid: true
              };
            }
          }
        } else if (control.value.defaultLanguages.length === 0) {
          return {
            isDefaultLanguagesValid: true
          };
        }
      }
      return null;
    };

  }

  isMarketValid(list: any, listComPref: any) {
    return (control: UntypedFormControl) => {
      if (list) {
        const market = control.value.market;
        const domain = control.value.domain;
        const type = control.value.comType;
        const groupType = control.value.comGroupeType;
        if (control.value.market) {
          if (market.length === 2 || market.length === 3) {
            let found = false;
            for (let i = 0; i < list.length; i++) {
              if (list[i].mainText === market) {
                found = true;
                break;
              }
            }

            if (!found) {
              return {
                checkIfDoesntExist: true
              };
            }
            const keyAlreadyExist = listComPref.filter(x => x.comType === type &&
              x.domain === domain && x.comGroupeType === groupType && x.market === market)[0];

            if (keyAlreadyExist) {
              return {
                checkKeyAlreadyExist: true
              };
            }

          }
          // * is an allowed value
          if (market.length === 1) {
            // If type equal to FB_ESS => * is not allowed
            if (type === 'FB_ESS') {
              if (market === '*') {
                return {
                  checkTypeFBESS: true
                };
              }
            }
            // If domain is different of F => * is not allowed
            if (domain !== 'F' && domain !== 'P' && domain !== 'U') {
              if (market === '*') {
                return {
                  checkDomain: true
                };
              }
            }


            // Check if combination Domain+Grouptype+Comm_Type (identifying a Comm Preference) is unique if value is an asterisk
            if (market === '*') {
              if (listComPref) {
                const keyAsterixAlreadyExist = listComPref.filter(x => x.comType.codeType === type &&
                  x.domain.codeDomain === domain && x.comGroupeType.codeGType === groupType && x.market === market)[0];
                if (keyAsterixAlreadyExist) {

                  return {
                    checkKeyAlreadyExist: true
                  };
                }
              }
            } else {
              // If value length = 1, so * is the only value allowed
              return {
                minChar: true
              };
            }
          }
        } else {
          // Market is a required field for others domain than P and U
          if (domain && domain !== 'P' && domain !== 'U') {
            return {
              marketRequired: true
            };
          }
        }
      }
      return null;
    };

  }


}
