import { Validator } from './validator';
import { RadioButtonOption } from '../contents/radio-button-option';

export interface FieldConfig {

    type: string;
    autofocus?: boolean;
    label?: string;
    placeholder?: string;
    name?: string;
    inputType?: string;
    options?: any[];
    radioBtnOptions?: RadioButtonOption[];
    value?: any;
    filterFct?: any;
    width?: string;
    validations?: Validator[];
    details?: any;

}
