import {Component, OnInit} from '@angular/core';
import {SidebarContentComponent} from '../../../shared/models/sidebar-props';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {AuthService} from '../../../shared/services/auth.service';
import {ToasterService} from '../../../core/services/toaster.service';
import {SidebarService} from '../../../shared/services/sidebar.service';
import {UserContexteService} from '../../../shared/services/user-contexte.service';
import {WorkExperience} from '../../../shared/models/work-experience';
import {Training} from '../../../shared/models/training';
import {Observable} from 'rxjs';
import {TrainingService} from '../../services/training.service';

@Component({
  selector: 'app-training-form',
  templateUrl: './training-form.component.html',
  styleUrls: ['./training-form.component.scss']
})
export class TrainingFormComponent extends SidebarContentComponent implements OnInit {

  readonly YEAR_RANGE = `1900:${new Date().getFullYear() + 1}`;

  formTraining: FormGroup;
  currentUserId: string;
  training: Training;
  isUpdate: boolean;
  isSubmitted = false;

  constructor(
    private formBuilder: FormBuilder,
    private trainingService: TrainingService,
    private authService: AuthService,
    private toasterService: ToasterService,
    private sidebarService: SidebarService,
    private userContexteService: UserContexteService
  ) {
    super();
  }

  ngOnInit(): void {
    this.training = this.sidebarInput ? {...this.sidebarInput.training} : undefined;
    this.isUpdate = !!this.sidebarInput?.training?.id;
    this.initForm(this.training);
    this.currentUserId = this.authService.getUserId();
  }

  onSubmit(): void {
    this.isSubmitted = true;

    if (this.formTraining.invalid) {
      return;
    }

    const training: Training = {...this.formTraining.value};
    let training$: Observable<WorkExperience>;
    if (this.isUpdate) {
      // on set manuellement l'id du training car cette information n'est pas stockée dans le formulaire
      training.id = this.sidebarInput.training.id;
      training$ = this.trainingService.updateTraining(training);
    } else {
      training$ = this.trainingService.createTraining(training);
    }
    training$.subscribe(
      trainingFromDb => {
        if (this.isUpdate) {
          this.userContexteService.updateTraining(trainingFromDb);
        } else {
          this.userContexteService.addTraining(trainingFromDb);
        }
      },
      () => this.toasterService.error('Oops', `Une erreur est survenue lors de ${this.isUpdate ? 'la mise à jour' : `l'ajout`} de votre expérience`),
      () => this.sidebarService.close()
    );
  }

  private initForm(training: Training): void {
    this.formTraining = this.formBuilder
      .group({
        label: [training?.label ?? '', Validators.required],
        school: [training?.school ?? ''],
        date: [
          training?.date ? new Date(this.training.date) : '', Validators.required
        ],
      });
  }
}
