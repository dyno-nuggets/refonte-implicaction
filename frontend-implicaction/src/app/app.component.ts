import {Component, ComponentFactoryResolver, HostBinding, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {SidebarService} from './shared/services/sidebar.service';
import {SidebarContentComponent, SidebarProps} from './shared/models/sidebar-props';
import {SidebarContentDirective} from './shared/directives/sidebar-content.directive';
import {Observable, Subject} from 'rxjs';
import {AuthService} from "./core/services/auth.service";
import {take, takeUntil} from "rxjs/operators";
import {ProfileContextService} from "./core/services/profile-context.service";
import {Principal} from "./shared/models/principal";
import {Profile} from "./community/models/profile/profile";
import {ProfileService} from "./community/services/profile/profile.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
})
export class AppComponent implements OnInit, OnDestroy {

  @ViewChild(SidebarContentDirective, {static: true})
  sidebarContent: SidebarContentDirective;

  sidebarProps: SidebarProps<unknown>;
  principal: Principal;
  profile$: Observable<Profile>;

  @HostBinding('style.--sidebar-content-width')
  private sidebarContentWidth: string;
  private onDestroySubject = new Subject<void>();

  constructor(
    private componentFactoryResolver: ComponentFactoryResolver,
    private authService: AuthService,
    public sidebarService: SidebarService,
    public profileService: ProfileService,
    private profileContextService: ProfileContextService
  ) {
  }

  ngOnInit(): void {
    this.sidebarService.getContent()
      .pipe(takeUntil(this.onDestroySubject))
      .subscribe(content => this.loadComponent(content));

    this.authService.principal$
      .pipe(takeUntil(this.onDestroySubject))
      .subscribe(principal => this.updateCurrentProfile(principal));

    this.profile$ = this.profileContextService.observeProfile();
  }

  private updateCurrentProfile(principal: Principal) {
    this.principal = principal;
    if (this.principal?.username) {
      this.profileService.getProfileByUsername(this.principal.username)
        .pipe(take(1))
        .subscribe(profile => this.profileContextService.profile = profile);
    } else {
      this.profileContextService.profile = null;
    }
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
    this.onDestroySubject.next()
    this.onDestroySubject.complete();
  }
}
