import {Component, Input, OnInit} from '@angular/core';
import {Training} from '../../../shared/models/training';
import {UserService} from '../../services/user.service';
import {AuthService} from '../../../shared/services/auth.service';
import {ToasterService} from '../../../core/services/toaster.service';
import {finalize} from 'rxjs/operators';

@Component({
  selector: 'app-training-list',
  templateUrl: './training-list.component.html',
  styleUrls: ['./training-list.component.scss']
})
export class TrainingListComponent implements OnInit {

  @Input()
  trainings: Training[];
  isEditing = false;
  currentUserId: string;
  trainingCopies: Training[] = [];

  constructor(
    private userService: UserService,
    private authService: AuthService,
    private toastService: ToasterService,
  ) {
  }

  trackByTrainingId = (index: number, training: Training) => training.id;

  ngOnInit(): void {
    this.currentUserId = this.authService.getUserId();
  }

  toggleModeEdition(): void {
    this.isEditing = !this.isEditing;
    this.trainingCopies = this.trainings.map(x => Object.assign({}, x));
  }

  updateTrainings(): any {
    this.userService
      .updateTraining(this.currentUserId, this.trainingCopies)
      .pipe(finalize(() => this.isEditing = false))
      .subscribe(
        trainingUpdates => this.trainings = [...trainingUpdates],
        () => this.toastService.error('Oops', 'Une erreur est survenue lors de la mise à jour des données'),
        () => this.toastService.success('Succès', 'Le changement des données a bien été effectué'),
      );
  }

  deleteEvent(training: Training): void {
    const trainingIndex = this.trainingCopies.indexOf(training);
    if (trainingIndex > -1) {
      this.trainingCopies.splice(trainingIndex, 1);
    }
  }
}
