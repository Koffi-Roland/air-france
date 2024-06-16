import { Pipe } from '@angular/core';
import { PipeTransform } from '@angular/core';

@Pipe({name: 'capitalize'})
export class CapitalizePipe implements PipeTransform {

  // Capitalize the first letter of the value
  transform(value: any) {
    if (value) {
      value = value.toLowerCase().split(' ');
      for (let i = 0; i < value.length; i++) {
          value[i] = value[i].charAt(0).toUpperCase() + value[i].substring(1);
      }
      value = value.join(' ');
    }
    return value;
  }
}
