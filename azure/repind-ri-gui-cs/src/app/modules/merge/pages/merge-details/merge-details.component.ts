import { Component, OnInit, Output, EventEmitter, ViewChildren, QueryList } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Router, ActivatedRoute } from '@angular/router';
import {
  WarningModalData,
  WarningModalBody,
  WarningModalTitle
} from '../../../../shared/components/warning-modal/_models/warningModalData';
import { WarningModalComponent } from '../../../../shared/components/warning-modal/warning-modal.component';
import { MergeResumeComponent } from '../../components/merge-resume/merge-resume.component';
import { MergeGroupDataComponent } from '../../components/merge-group-data/merge-group-data.component';
import { TelecomConfig } from '../../config/telecom.config';
import { EmailConfig } from '../../config/email.config';
import { ContractConfig } from '../../config/contract.config';
import { AddressConfig } from '../../config/address.config';
import { NotMergeableAddressesConfig } from '../../config/notMergeableAddresses.config';
import { AccountDataConfig } from '../../config/accountData.config';
import { MergeService } from '../../../../core/services/merge/merge.service';

@Component({
  selector: 'app-individual-merge-details',
  templateUrl: './merge-details.component.html',
  styleUrls: ['./merge-details.component.scss']
})
export class MergeDetailsComponent implements OnInit {

  result: any;
  individualSource: any;
  individualTarget: any;
  params: any;
  @Output() selectedData = new EventEmitter();

  @ViewChildren(MergeGroupDataComponent) child: QueryList<MergeGroupDataComponent>;

  constructor(private mergeService: MergeService, private router: Router, private route: ActivatedRoute, public dialog: MatDialog) {

    if (this.route.snapshot.data['mergeData']) {
      if (this.route.snapshot.data['mergeData'].isExpertRequired) {
      const modalInfo = new WarningModalData(new WarningModalTitle('MERGE-EXPERT-REQUIRED-TITLE', 'warning'),
      new WarningModalBody('MERGE-EXPERT-REQUIRED-BODY', 'MERGE-EXPERT-VALIDATION-CHECKBOX', '/individuals/merge'));
      this.dialog.open(WarningModalComponent, {
        data: modalInfo,
        disableClose: true
      });
    }
      if (!this.route.snapshot.data['mergeData'].isSamePerson) {
        const modalInfo = new WarningModalData(new WarningModalTitle('NOT-SAME-PERSON-TITLE', 'warning'),
        new WarningModalBody('NOT-SAME-PERSON-BODY', 'AGREE-IT-OK-CHECKBOX', '/individuals/merge'));
        this.dialog.open(WarningModalComponent, {
          data: modalInfo,
          disableClose: true
        });
      }
    }
   }

  ngOnInit() {

    this.result = this.route.snapshot.data['mergeData'];
    this.params = {};

    if (this.result !== undefined) {
      this.individualSource = this.result.individualSource;
      this.individualTarget = this.result.individualTarget;
    }


    this.params.blocsTelecom = TelecomConfig.config;
    this.params.blocsEmail = EmailConfig.config;
    this.params.blocsContract = ContractConfig.config;
    this.params.blocsAddress = AddressConfig.config;
    this.params.blocsNotMergeableAddress = NotMergeableAddressesConfig.config;
    this.params.blocsAccountData = AccountDataConfig.config;
  }

  onSubmitMerge(): void {

    this.mergeService.setFirstGin(this.individualSource.identification.individual.gin);
    this.mergeService.setSecondGin(this.individualTarget.identification.individual.gin);

    const response = [];
    this.child.forEach((child) => {
      const value = child.getValue();
      if (value) {
        response.push(child.getValue());
      }
    });
    this.mergeService.setBlocsToKeep(response);

    this.mergeService.getMergeResume(this.individualSource.identification.individual.gin,
      this.individualTarget.identification.individual.gin, response).then(res => {
      const dialogRef = this.dialog.open(MergeResumeComponent, {
        width: '90vw',
        autoFocus: false,
        data: res
      });
    });
    }

  onSwitchIndividuals(): void {

    this.mergeService.setFirstGin(this.individualTarget.identification.individual.gin);
    this.mergeService.setSecondGin(this.individualSource.identification.individual.gin);
    this.mergeService.setForce(true);

    this.router.navigate(['individuals/merge']).then(() => this.router.navigate(['individuals/merge/details']));
  }

}
