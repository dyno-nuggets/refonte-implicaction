import {Injectable} from '@angular/core';
import {BehaviorSubject, Observable} from 'rxjs';
import {SidebarProps} from '../models/sidebar-props';

@Injectable({
  providedIn: 'root'
})
export class SidebarService {

  private static readonly DEFAULT_WIDTH = 1050;

  isOpen = false;

  private sidebarContent = new BehaviorSubject<SidebarProps<unknown>>(null);

  getContent(): Observable<SidebarProps<unknown>> {
    return this.sidebarContent.asObservable();
  }

  setContent<T>(content: SidebarProps<T>): SidebarService {
    this.sidebarContent.next(content);
    return this;
  }

  open<T>(content?: SidebarProps<T>): void {
    if (content) {
      // défini la largeur par défaut si non définie
      content.width ??= SidebarService.DEFAULT_WIDTH;
      this.setContent(content);
    }
    this.isOpen = true;
  }

  close(): void {
    this.isOpen = false;
  }

  toggle(): void {
    this.isOpen = !this.isOpen;
  }
}
