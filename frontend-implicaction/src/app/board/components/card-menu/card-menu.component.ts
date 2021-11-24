import {Component, Input} from '@angular/core';
import {MenuItem} from 'primeng/api';
import {JobApplication} from '../../models/job-application';

@Component({
  selector: 'app-card-menu',
  templateUrl: './card-menu.component.html',
  styleUrls: ['./card-menu.component.scss']
})
export class CardMenuComponent {

  @Input()
  apply: JobApplication;

  actions: MenuItem[] = [{
    label: 'Options',
    items: [
      {
        label: 'Candidature acceptée',
        icon: 'far fa-check-circle',
        command: () => this.acceptApply()
      },
      {
        label: 'Candidature refusée',
        icon: 'far fa-times-circle',
        command: () => this.declineApply()
      },
      {
        label: 'Annuler la candidature',
        icon: 'far fa-trash-alt pe-1',
        command: () => this.cancelApply()
      },
      {
        label: 'Archiver la candidature',
        icon: 'far fa-folder-open',
        command: () => this.archiveApply()
      }]
  }];

  acceptApply(): void {
    console.log('accept');
  }

  declineApply(): void {
    console.log('decline');
  }

  archiveApply(): void {
    console.log('archive');
  }

  cancelApply(): void {
    console.log('cancel');
  }
}
