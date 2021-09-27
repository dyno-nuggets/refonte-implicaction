import {Pipe, PipeTransform} from '@angular/core';

@Pipe({
  name: 'br'
})
export class BrPipe implements PipeTransform {

  private static readonly NEW_LINE_PATTERN = /\r\n|\r|\n/g;
  private static readonly BR_MARKUP = '<br>';

  transform(text: string): string {
    return text ? text.replace(BrPipe.NEW_LINE_PATTERN, BrPipe.BR_MARKUP) : '';
  }

}
