import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'firstLetter'
})
export class FirstLetterPipe implements PipeTransform {

  transform(value: string): any {
    if (!value) { return ''; }
    return value.charAt(0);
  }

}
