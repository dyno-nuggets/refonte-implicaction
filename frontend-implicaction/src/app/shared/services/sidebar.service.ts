import {Injectable} from '@angular/core';
import {BehaviorSubject, Observable} from 'rxjs';
import {SidebarProps} from '../models/sidebar-props';

@Injectable({
  providedIn: 'root'
})
export class SidebarService {

  private static readonly DEFAULT_WIDTH = 1050;

  isOpen = false;

  private sidebarContent = new BehaviorSubject<SidebarProps>(null);

  getContent(): Observable<SidebarProps> {
    return this.sidebarContent.asObservable();
  }

  setContent(content: SidebarProps): SidebarService {
    this.sidebarContent.next(content);
    return this;
  }

  open(content?: SidebarProps): void {
    if (content) {
      // défini la largeur par défaut si non définie
      content.width = content.width ?? SidebarService.DEFAULT_WIDTH;
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
