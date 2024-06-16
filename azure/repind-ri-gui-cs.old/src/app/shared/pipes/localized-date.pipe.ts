import { Pipe, PipeTransform } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { DatePipe } from '@angular/common';
import * as moment from 'moment';

@Pipe({
  name: 'localizedDate',
  pure: false
})
export class LocalizedDatePipe implements PipeTransform {

  constructor(private translate: TranslateService) { }

  transform(date: any, pattern: string = 'medium'): any {

    if (!date) { return ''; }
    const currentLang = this.translate.currentLang;
    return new DatePipe(currentLang).transform(moment(date).toString(), pattern);
  }

}
