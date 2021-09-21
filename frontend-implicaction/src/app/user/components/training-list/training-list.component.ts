import {AfterContentChecked, Component, Input, OnInit} from '@angular/core';
import {Training} from '../../../shared/models/training';
import {UserService} from '../../services/user.service';
import {AuthService} from '../../../shared/services/auth.service';
import {ToasterService} from '../../../core/services/toaster.service';

@Component({
  selector: 'app-training-list',
  templateUrl: './training-list.component.html',
  styleUrls: ['./training-list.component.scss']
})
export class TrainingListComponent implements OnInit, AfterContentChecked {
  isEditing = true;
  currentUserId: string;
  trainingCopies: Training[] = [];

  @Input()
  trainings: Training[];

  constructor(
    private userService: UserService,
    private authService: AuthService,
    private toastService: ToasterService,
  ) {
    this.isEditing = false;
  }

  ngOnInit(): void {
    this.currentUserId = this.authService.getUserId();
  }

  ngAfterContentChecked(): void {
    this.trainingCopies = this.trainings;
  }

  toggleModeEdition(): void {
    this.isEditing = !this.isEditing;
  }

  updateTrainings(): any {
    this.userService
      .updateTraining(this.currentUserId, this.trainingCopies)
      .subscribe(
        trainingUpdates => {
          this.trainings = trainingUpdates;
          this.trainingCopies = trainingUpdates;
        },
        () => this.toastService.error('Oops', 'Une erreur est survenue lors de la mise à jour des données'),
        () => this.toastService.success('Changement effectué', 'Votre changement a bien été pris en compte'),
      );
  }

  rollbackTrainings(): void {
    this.trainingCopies = this.trainings;
    this.toggleModeEdition();
  }

}
