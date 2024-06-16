import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Router, NavigationExtras } from '@angular/router';
import { MergeGroupData } from '../../dataFormat/mergeGroupData';
import { TelecomConfig } from '../../config/telecom.config';
import { EmailConfig } from '../../config/email.config';
import { ContractConfig } from '../../config/contract.config';
import { AddressConfig } from '../../config/address.config';
import { AccountDataConfig } from '../../config/accountData.config';
import { Template } from '../../dataFormat/template';

@Component({
  selector: 'app-individual-merge-resume',
  templateUrl: './merge-resume.component.html',
  styleUrls: ['./merge-resume.component.scss']
})
export class MergeResumeComponent implements OnInit {

  result: any;
  individualMerge: any;
  params: any;
  paramsForResult: any;

  constructor(
    public dialogRef: MatDialogRef<MergeResumeComponent>, @Inject(MAT_DIALOG_DATA) public data: any, private router: Router) {

    this.result = this.data;
    if (this.result !== undefined) {
      this.individualMerge = this.result.individualTarget;
    }
    this.params = {};
    this.params.blocsTelecom = MergeGroupData.copyMergeGroupData(TelecomConfig.config);
    this.params.blocsEmail = MergeGroupData.copyMergeGroupData(EmailConfig.config);
    this.params.blocsContract = MergeGroupData.copyMergeGroupData(ContractConfig.config);
    this.params.blocsAddress = MergeGroupData.copyMergeGroupData(AddressConfig.config);
    this.params.blocsAccountData = MergeGroupData.copyMergeGroupData(AccountDataConfig.config);

    this.addMonoblocAndUnselect();


    const sourceIdentity = this.result.individualSource.identification.individual.civility
      + ' ' + this.result.individualSource.identification.individual.firstName + ' '
      + this.result.individualSource.identification.individual.lastName;

    const targetIdentity = this.result.individualTarget.identification.individual.civility + ' ' +
      this.result.individualTarget.identification.individual.firstName + ' ' +
      this.result.individualTarget.identification.individual.lastName;

    this.paramsForResult = {
      'sourceGin': this.result.individualSource.identification.individual.gin,
      'sourceIdentity': sourceIdentity.trim(),
      'targetGin': this.result.individualTarget.identification.individual.gin,
      'targetIdentity': targetIdentity.trim()
    };

  }

  ngOnInit() {
  }

  /**
   * This method add Template 'monobloc' to display only one block of data and not permit to select them
   */
  private addMonoblocAndUnselect() {
    for (const bloc in this.params) {
      if (this.params.hasOwnProperty(bloc)) {
        if (this.params[bloc].options.template == null) {
          this.params[bloc].options.template = new Array<Template>();
        }
        this.params[bloc].options.template.push(new Template('monobloc', null));
        this.params[bloc].options.type = null;
      }
    }
  }

  public onSubmitMerge() {
    const navigationExtras: NavigationExtras = { state: { paramsForResult: this.paramsForResult } };
    this.dialogRef.close();
    this.router.navigate(['individuals/merge/result'], navigationExtras);
  }

}
