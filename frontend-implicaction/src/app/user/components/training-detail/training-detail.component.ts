import {Component, Input} from '@angular/core';
import {Training} from '../../../shared/models/training';
import {SidebarService} from '../../../shared/services/sidebar.service';
import {TrainingFormComponent} from '../training-form/training-form.component';
import {TrainingService} from '../../services/training.service';
import {ToasterService} from '../../../core/services/toaster.service';
import {UserContextService} from '../../../shared/services/user-context.service';

@Component({
  selector: 'app-training-detail',
  templateUrl: './training-detail.component.html',
  styleUrls: ['./training-detail.component.scss']
})
export class TrainingDetailComponent {

  @Input()
  training: Training;
  @Input()
  readOnly = true;
  readonly yearRange = `1900:${new Date().getFullYear() + 1}`;

  constructor(
    private sidebarService: SidebarService,
    private trainingService: TrainingService,
    private toasterService: ToasterService,
    private userContextService: UserContextService
  ) {
  }

  deleteTraining(training: Training): void {
    if (this.readOnly) {
      return;
    }

    this.trainingService
      .deleteTraining(training.id)
      .subscribe(
        () => {
        },
        () => this.toasterService.error('Oops', 'Une erreur est survenue lors de la suppression de la formation'),
        () => this.userContextService.removeTraining(training)
      );
  }

  editTraining(training: Training): void {
    if (this.readOnly) {
      return;
    }

    this.sidebarService
      .open({
        title: 'Editer une formation',
        input: {training},
        component: TrainingFormComponent,
        width: 650
      });
  }
}
