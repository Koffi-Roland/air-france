import { Component, OnInit, ViewChild, Input } from '@angular/core';
import { DynamicFormComponent } from '../../../../../../shared/components/forms/dynamic-form/dynamic-form.component';
import { Email } from '../../../../../../shared/models/resources/email';
import { FieldConfig } from '../../../../../../shared/models/forms/field-config';
import { Validators } from '@angular/forms';
import { TranslateService } from '@ngx-translate/core';
import { OptionItem } from '../../../../../../shared/models/contents/option-item';
import { AuthorizationMailing } from '../../../../../../shared/models/common/authorization-mailing';
import { CommonService } from '../../../../../../core/services/common.service';

@Component({
  selector: 'app-email-details-form',
  templateUrl: './email-details-form.component.html',
  styleUrls: ['./email-details-form.component.scss']
})
export class EmailDetailsFormComponent implements OnInit {

  @ViewChild(DynamicFormComponent, { static: true }) private _form: DynamicFormComponent;

  @Input() email: Email;

  public config: FieldConfig[];

  private allNAT: Array<OptionItem> = [];
  private defaultNAT = 'A';

  constructor(private translateService: TranslateService, public common: CommonService) { }

  ngOnInit() {
    this.initializeOptions();
    this.initializeFormConfiguration();
  }

  get form(): DynamicFormComponent {
    return this._form;
  }

  get valid(): boolean {
    return this.form.valid;
  }

  get value(): any {
    return this.form.value;
  }

  private initializeOptions(): void {
    Object.keys(AuthorizationMailing).map(key => {
      const nat = AuthorizationMailing[key];
      const opt = this.translateService.instant(this.common.getTransformEnumType(this.common.referenceDataType.NAT) + nat);
      const optItem = new OptionItem(nat, opt, nat);
      this.allNAT.push(optItem);
    });
  }

  private initializeFormConfiguration(): void {

    this.config = [
      {
        type: 'input',
        name: 'email',
        label: 'EMAIL',
        autofocus: true,
        value: (this.email) ? this.email.emailAddress : '',
        validations: [
          {
            name: 'required',
            validator: Validators.required,
            message: 'FIELD-REQUIRED'
          },
          {
            name: 'maxlength',
            validator: Validators.maxLength(60),
            message: 'MAX-LENGTH-MSG',
            maxLength: 60
          }
        ]
      },
      {
        type: 'select',
        name: 'nat',
        label: 'AUTHORIZATIONMAILING',
        value: (this.email) ? this.email.mailingAuthorization : this.defaultNAT,
        options: this.allNAT,
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
