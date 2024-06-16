import { Component} from '@angular/core';
import { Router} from '@angular/router';
@Component({
  selector: 'app-individual-merge-result',
  templateUrl: './merge-result.component.html',
  styleUrls: ['./merge-result.component.scss']
})
export class MergeResultComponent {

  params: any;

  constructor(private router: Router) {
    this.params = this.router.getCurrentNavigation().extras.state.paramsForResult;
  }

}

