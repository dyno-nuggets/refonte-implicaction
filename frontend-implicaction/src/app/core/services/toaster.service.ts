import {Injectable} from '@angular/core';
import {MessageService} from 'primeng/api';

@Injectable({
  providedIn: 'root'
})
export class ToasterService {

  constructor(private messageService: MessageService) {
  }

  success(title: string, message: string): void {
    this.messageService.add({severity: 'success', sticky: true, summary: title, detail: message});
  }

  error(title: string, message: string): void {
    this.messageService.add({severity: 'error', sticky: true, summary: title, detail: message});
  }
}
