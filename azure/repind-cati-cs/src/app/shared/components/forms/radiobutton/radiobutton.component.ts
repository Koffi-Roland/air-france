import { Component, OnInit } from '@angular/core';
import { FieldConfig } from '../../../models/forms/field-config';
import { UntypedFormGroup } from '@angular/forms';

@Component({
  selector: 'app-radiobutton',
  templateUrl: './radiobutton.component.html',
  styleUrls: ['./radiobutton.component.scss']
})
export class RadiobuttonComponent implements OnInit {

  public field: FieldConfig;
  public group: UntypedFormGroup;

  constructor() { }

  ngOnInit() {
  }

}
