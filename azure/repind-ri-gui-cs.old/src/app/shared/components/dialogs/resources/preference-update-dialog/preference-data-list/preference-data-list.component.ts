import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { RefProviderService } from '../../../../../../core/services/references/ref-provider.service';
import { ReferenceDataType } from '../../../../../../shared/models/references/ReferenceDataType.enum';
import { PreferenceData } from '../../../../../../shared/models/common/preference-data';
import { CommonService } from '../../../../../../core/services/common.service';
import { Preference } from '../../../../../../shared/models/resources/preference';

@Component({
  selector: 'app-preference-data-list',
  templateUrl: './preference-data-list.component.html',
  styleUrls: ['./preference-data-list.component.scss']
})
export class PreferenceDataListComponent implements OnInit {

  @Input() preference: Preference;
  @Output() preferenceDataSelected = new EventEmitter<PreferenceData>();

  public currentLocal = '';
  public references = [];

  constructor(private _refProviderSerivce: RefProviderService, private common: CommonService) { }

  ngOnInit() {
    this.currentLocal = this.common.getCurrentLocal();
    this.preference.preferenceData.forEach((prefData: PreferenceData) => {
      const ref = this._refProviderSerivce.findReferenceDataByCode(ReferenceDataType.PREFERENCE_KEYS, prefData.key.toUpperCase());
      // prefData.refData = ref;
    });
  }

  public delete(data: PreferenceData): void {
    this.preference.deletePreferenceData(data);
  }

  public update(data: PreferenceData): void {
    this.preferenceDataSelected.emit(data);
  }

}
