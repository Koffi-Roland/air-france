import { Component, OnInit } from '@angular/core';
import { FieldConfig } from '../../../models/forms/field-config';
import { UntypedFormGroup } from '@angular/forms';

@Component({
  selector: 'app-select',
  templateUrl: './select.component.html',
  styleUrls: ['./select.component.scss']
})
export class SelectComponent implements OnInit {

  public field: FieldConfig;
  public group: UntypedFormGroup;

  constructor() { }

  ngOnInit() {
  }

}
