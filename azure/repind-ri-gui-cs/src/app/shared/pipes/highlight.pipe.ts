import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'highlight'
})
export class HighlightPipe implements PipeTransform {

  transform(text: string, search: any): string {
    const pattern = search
      .replace(/[\-\[\]\/\{\}\(\)\*\+\?\.\\\^\$\|]/g, '\\$&')
      .split(' ')
      .filter((t: string) => t.length > 0)
      .join('|');
    const regex = new RegExp(pattern, 'gi');
    return search ? text.replace(regex, match => match) : text;
  }

}
