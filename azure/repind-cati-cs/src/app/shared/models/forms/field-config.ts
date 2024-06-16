import { AutoFill } from '../contents/autofill';
import { Validator } from './validator';

export interface FieldConfig {

    type: string;
    autofocus?: boolean;
    label?: string;
    placeholder?: string;
    name?: string;
    inputType?: string;
    options?: any[];
    value?: any;
    filterFct?: any;
    width?: string;
    validations?: Validator[];
    disableForUpdate?: boolean;
    id?: boolean;
    uppercase?: boolean;
    autoFill?: AutoFill;
    tableMultiselectIdName?: string;
    isHidden?: boolean;
}
