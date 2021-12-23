import {Pipe, PipeTransform} from '@angular/core';

@Pipe({
  name: 'textEllipsis'
})
export class TextEllipsisPipe implements PipeTransform {

  transform(value: string, maxLength: number): string {
    return value.length > maxLength ? `${value.slice(0, maxLength - 1)}\u2026` : value;
  }

}
