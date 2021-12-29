import {Component, Input, OnInit} from '@angular/core';
import {Training} from '../../../shared/models/training';
import {UserService} from '../../services/user.service';
import {AuthService} from '../../../shared/services/auth.service';
import {ToasterService} from '../../../core/services/toaster.service';
import {Subscription} from 'rxjs';
import {SidebarService} from '../../../shared/services/sidebar.service';
import {UserContextService} from '../../../shared/services/user-context.service';
import {TrainingFormComponent} from '../training-form/training-form.component';
import {TrainingService} from '../../services/training.service';

@Component({
  selector: 'app-training-list',
  templateUrl: './training-list.component.html',
  styleUrls: ['./training-list.component.scss']
})
export class TrainingListComponent implements OnInit {

  @Input()
  readOnly = true;
  trainings: Training[];

  private subscription: Subscription;

  constructor(
    private userService: UserService,
    private authService: AuthService,
    private toasterService: ToasterService,
    private sidebarService: SidebarService,
    private userContextService: UserContextService,
    private trainingService: TrainingService
  ) {
  }

  trackByTrainingId = (index: number, training: Training) => training.id;

  ngOnInit(): void {
    this.subscription = this.userContextService
      .observeUser()
      .subscribe(user => this.trainings = user.trainings);
  }

  onAddTraining(): void {
    this.sidebarService
      .open({
        title: 'Ajouter une formation',
        component: TrainingFormComponent,
        width: 650
      });
  }

  deleteTraining(training: Training): void {
    if (this.readOnly) {
      return;
    }

    this.trainingService
      .deleteTraining(training.id)
      .subscribe(
        () => this.userContextService.removeTraining(training),
        () => this.toasterService.error('Oops', 'Une erreur est survenue lors de la suppression de la formation'),
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
