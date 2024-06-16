import { Component, OnInit } from '@angular/core';
import { UntypedFormGroup, UntypedFormControl, Validators } from '@angular/forms';
import { MergeService } from '../../../../core/services/merge/merge.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-individual-merge',
  templateUrl: './merge.component.html',
  styleUrls: ['./merge.component.scss']
})
export class MergeComponent implements OnInit {

  ginSearchForm: UntypedFormGroup;

  constructor(private mergeService: MergeService, private router: Router) { }

  ngOnInit() {
    this.ginSearchForm = new UntypedFormGroup ({
      firstGin: new UntypedFormControl('',
      [Validators.required, Validators.maxLength(12), Validators.minLength(10)]),
      secondGin: new UntypedFormControl('',
      [Validators.required, Validators.maxLength(12), Validators.minLength(10)])
    });
  }

  onSubmitSearch(): void {
    const values = this.ginSearchForm.value;
    this.mergeService.setFirstGin(((values.firstGin as string).trim().length === 10) ? '00' + values.firstGin : values.firstGin);
    this.mergeService.setSecondGin(((values.secondGin as string).trim().length === 10) ? '00' + values.secondGin : values.secondGin);
    this.router.navigate(['individuals/merge/details']);
  }

}
