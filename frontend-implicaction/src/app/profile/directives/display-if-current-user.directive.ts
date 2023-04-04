import {Directive, Input, OnInit, TemplateRef, ViewContainerRef} from '@angular/core';
import {AuthService} from "../../shared/services/auth.service";

@Directive({
  selector: '[appDisplayIfCurrentUser]'
})
export class DisplayIfCurrentUserDirective implements OnInit {

  username: string;

  @Input('appDisplayIfCurrentUser')
  set isCurrentUser(username: string) {
    this.username = username;
    this.updateView();
  }

  private currentUsername: string;

  constructor(
    private viewContainerRef: ViewContainerRef,
    private templateRef: TemplateRef<any>,
    private authService: AuthService
  ) {
  }

  ngOnInit(): void {
    this.currentUsername = this.authService.getCurrentUser()?.username;
    this.authService.currentUser$.subscribe(user => {
        this.currentUsername = user.username;
        this.updateView();
      }
    );
    this.updateView();
  }

  private updateView(): void {
    if (this.username && this.username === this.currentUsername) {
      this.viewContainerRef.createEmbeddedView(this.templateRef);
    } else {
      this.viewContainerRef.clear()
    }
  }

}
