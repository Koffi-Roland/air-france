import { Component, OnInit } from '@angular/core';
import { FieldConfig } from '../../../models/forms/field-config';
import { UntypedFormGroup } from '@angular/forms';

@Component({
  selector: 'app-checkbox',
  templateUrl: './checkbox.component.html',
  styleUrls: ['./checkbox.component.scss']
})
export class CheckboxComponent implements OnInit {

  public field: FieldConfig;
  public group: UntypedFormGroup;

  constructor() { }

  ngOnInit() {
  }

}
