import { Component, OnInit, Input, ViewChild } from '@angular/core';
import { DynamicFormComponent } from '../../../../../shared/components/forms/dynamic-form/dynamic-form.component';
import { FieldConfig } from '../../../../../shared/models/forms/field-config';
import { Validators } from '@angular/forms';
import { Status } from '../../../../../shared/models/common/status';
import { Type } from '../../../../../shared/models/common/type';
import { TranslateService } from '@ngx-translate/core';
import { OptionItem } from '../../../../../shared/models/contents/option-item';

@Component({
  selector: 'app-resource-general-info-form',
  templateUrl: './resource-general-info-form.component.html',
  styleUrls: ['./resource-general-info-form.component.scss']
})
export class ResourceGeneralInfoFormComponent implements OnInit {

  @ViewChild(DynamicFormComponent, { static: true }) generalInfoForm: DynamicFormComponent;

  @Input() resource: any;

  public config: FieldConfig[];

  public allStatus: Array<OptionItem> = [];
  public allTypes: Array<OptionItem> = [];

  constructor(private translateService: TranslateService) { }

  ngOnInit() {
    this.initializeOptions();
    this.initializeFormConfiguration();
  }

  get valid(): boolean {
    return this.generalInfoForm.valid;
  }

  get value(): any {
    return this.generalInfoForm.value;
  }

  private initializeOptions(): void {
    Object.keys(Status).map(key => {
      // Do not put H status (historic) in the possibilities
      if (Status[key] !== 'H' && Status[key] !== 'X') {
        const status = Status[key];
        const opt = this.translateService.instant(status + '-STATUS');
        const optItem = new OptionItem(status, opt, status);
        this.allStatus.push(optItem);
      }
    });
    Object.keys(Type).map(key => {
      const type = Type[key];
      const opt = this.translateService.instant(type + '-TYPE');
      const optItem = new OptionItem(type, opt, type);
      this.allTypes.push(optItem);
    });
  }

  private initializeFormConfiguration(): void {

    const defaultStatus = 'V';
    const defaultType = 'D';

    this.config = [
      {
        type: 'select',
        name: 'status',
        label: 'STATUS',
        value: (this.resource) ? this.resource.status : defaultStatus,
        options: this.allStatus,
        validations: [
          {
            name: 'required',
            validator: Validators.required,
            message: 'FIELD-REQUIRED'
          }
        ]
      },
      {
        type: 'select',
        name: 'type',
        label: 'TYPE',
        value: (this.resource) ? this.resource.type : defaultType,
        options: this.allTypes,
        validations: [
          {
            name: 'required',
            validator: Validators.required,
            message: 'FIELD-REQUIRED'
          }
        ]
      }
    ];

  }

}
