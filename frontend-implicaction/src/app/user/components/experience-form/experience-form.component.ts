import {Component, OnInit} from '@angular/core';
import {SidebarContentComponent} from '../../../shared/models/sidebar-props';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {WorkExperience} from '../../../shared/models/work-experience';
import {UserService} from '../../services/user.service';
import {AuthService} from '../../../shared/services/auth.service';
import {ToasterService} from '../../../core/services/toaster.service';
import {SidebarService} from '../../../shared/services/sidebar.service';
import {Observable} from 'rxjs';
import {ExperiencesContexteService} from '../../../shared/services/experiences-contexte.service';

@Component({
  selector: 'app-experience-form',
  templateUrl: './experience-form.component.html',
  styleUrls: ['./experience-form.component.scss']
})
export class ExperienceFormComponent extends SidebarContentComponent implements OnInit {

  readonly YEAR_RANGE = `1900:${new Date().getFullYear() + 1}`;

  formExperience: FormGroup;
  currentUserId: string;
  experience: WorkExperience;
  isUpdate: boolean;

  constructor(
    private formBuilder: FormBuilder,
    private userService: UserService,
    private authService: AuthService,
    private toasterService: ToasterService,
    private sidebarService: SidebarService,
    private ecs: ExperiencesContexteService
  ) {
    super();
  }

  ngOnInit(): void {
    this.experience = this.sidebarInput ? {...this.sidebarInput.experience} : {};
    this.isUpdate = !!this.experience.id;
    this.initForm();
    this.currentUserId = this.authService.getUserId();
  }

  onSubmit(): void {
    if (this.formExperience.valid) {
      const experience: WorkExperience = {...this.formExperience.value};
      let experience$: Observable<WorkExperience>;
      let operation: string;
      if (experience.id) {
        experience$ = this.userService.updateExperience(this.currentUserId, experience);
        operation = 'la mise à jour';
      } else {
        experience$ = this.userService.createExperience(this.currentUserId, experience);
        operation = `l'ajout`;
      }
      experience$.subscribe(
        experienceFromDb => {
          if (this.isUpdate) {
            this.ecs.updateExperience(experienceFromDb);
          } else {
            this.ecs.addExperience(experienceFromDb);
          }
        },
        () => this.toasterService.error('Oops', `Une erreur est survenue lors de ${operation} de votre expérience`),
        () => this.sidebarService.close()
      );
    }
  }

  private initForm(): void {
    this.formExperience = this.formBuilder
      .group({
        id: [this.experience.id ?? null],
        companyName: [this.experience.companyName, Validators.required],
        label: [this.experience.label, Validators.required],
        description: [this.experience.description],
        startedAt: [this.experience.startedAt ? new Date(this.experience.startedAt) : '', Validators.required],
        finishedAt: [this.experience.finishedAt ? new Date(this.experience.finishedAt) : '', Validators.required]
      });
  }
}
