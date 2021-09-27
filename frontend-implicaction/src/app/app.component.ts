import {Component, ComponentFactoryResolver, HostBinding, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {SidebarService} from './shared/services/sidebar.service';
import {SidebarContentComponent, SidebarProps} from './shared/models/sidebar-props';
import {SidebarContentDirective} from './shared/directives/sidebar-content.directive';
import {Subscription} from 'rxjs';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit, OnDestroy {
  sidebarProps: SidebarProps;
  @ViewChild(SidebarContentDirective, {static: true})
  sidebarContent: SidebarContentDirective;
  private sidebarSubject: Subscription;
  @HostBinding('style.--sidebar-content-width')
  private sidebarContentWidth: string;

  constructor(
    private componentFactoryResolver: ComponentFactoryResolver,
    public sidebarService: SidebarService
  ) {
  }

  ngOnInit(): void {
    this.sidebarSubject = this.sidebarService
      .getContent()
      .subscribe(content => this.loadComponent(content))
    ;
  }

  ngOnDestroy(): void {
    this.sidebarSubject?.unsubscribe();
  }

  private loadComponent(content: SidebarProps): void {
    if (!content) {
      return;
    }

    this.sidebarProps = content;
    this.sidebarContentWidth = `${this.sidebarProps.width}px`;
    const componentFactory = this.componentFactoryResolver.resolveComponentFactory(content.component);
    const viewContainerRef = this.sidebarContent.viewContainerRef;
    viewContainerRef.clear();

    // instanciation dynamique du contenu de la sidebar
    const componentRef = viewContainerRef.createComponent<SidebarContentComponent>(componentFactory);
    componentRef.instance.sidebarInput = content.input;
  }
}
