import {Component, ComponentFactoryResolver, HostBinding, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {SidebarService} from './shared/services/sidebar.service';
import {SidebarContentComponent, SidebarProps} from './shared/models/sidebar-props';
import {SidebarContentDirective} from './shared/directives/sidebar-content.directive';
import {Subscription} from 'rxjs';
import {AuthService} from "./shared/services/auth.service";
import {User} from "./shared/models/user";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit, OnDestroy {

  @ViewChild(SidebarContentDirective, {static: true})
  sidebarContent: SidebarContentDirective;

  sidebarProps: SidebarProps<unknown>;
  currentUser: User;

  @HostBinding('style.--sidebar-content-width')
  private sidebarContentWidth: string;
  private subscription: Subscription;

  constructor(
    private componentFactoryResolver: ComponentFactoryResolver,
    private authService: AuthService,
    public sidebarService: SidebarService
  ) {
  }

  ngOnInit(): void {
    this.subscription = this.sidebarService
      .getContent()
      .subscribe(content => this.loadComponent(content))
      .add(
        this.authService
          .currentUser$
          .subscribe(currentUser => this.currentUser = currentUser)
      );
    this.currentUser = this.authService.getCurrentUser()
  }

  private loadComponent(content: SidebarProps<unknown>): void {
    if (!content) {
      return;
    }

    this.sidebarProps = content;
    this.sidebarContentWidth = `${this.sidebarProps.width}px`;
    const componentFactory = this.componentFactoryResolver.resolveComponentFactory(content.component);
    const viewContainerRef = this.sidebarContent.viewContainerRef;
    viewContainerRef.clear();

    // instanciation dynamique du contenu de la sidebar
    const componentRef = viewContainerRef.createComponent<SidebarContentComponent<unknown>>(componentFactory);
    componentRef.instance.sidebarInput = content.input;
  }

  ngOnDestroy(): void {
    this.subscription?.unsubscribe();
  }
}
