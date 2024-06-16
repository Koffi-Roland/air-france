import { UntypedFormGroup } from '@angular/forms';
import { FieldConfig } from '../field-config';
import { Validator } from '../validator';

export interface StepConfig {

    label: string;
    stepControl?: UntypedFormGroup;
    errorMessage: string;
    fieldConfig: FieldConfig[];
    globalValidators?: Validator[];

}
