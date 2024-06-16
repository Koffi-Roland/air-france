import {ErrorStateMatcher} from '@angular/material/core';
import { FormGroupDirective, UntypedFormControl, NgForm } from '@angular/forms';

/** Error when invalid control is dirty, touched, or submitted. */
export class MyErrorStateMatcher implements ErrorStateMatcher {
    isErrorState(control: UntypedFormControl | null, form: FormGroupDirective | NgForm | null): boolean {
      return !!(control && control.invalid && (control.dirty || control.touched));
    }
  }
