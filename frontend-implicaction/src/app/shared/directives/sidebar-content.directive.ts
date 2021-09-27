import {Directive, ViewContainerRef} from '@angular/core';

@Directive({
  selector: '[appSidebarContent]'
})
export class SidebarContentDirective {

  constructor(public viewContainerRef: ViewContainerRef) {
  }

}
