import {Component, OnInit} from '@angular/core';
import {SidebarContentComponent} from '../../../shared/models/sidebar-props';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {WorkExperience} from '../../../shared/models/work-experience';
import {AuthService} from '../../../shared/services/auth.service';
import {ToasterService} from '../../../core/services/toaster.service';
import {SidebarService} from '../../../shared/services/sidebar.service';
import {Observable} from 'rxjs';
import {UserContextService} from '../../../shared/services/user-context.service';
import {ExperienceService} from '../../services/experience.service';
import {Constants} from '../../../config/constants';

@Component({
  selector: 'app-experience-form',
  templateUrl: './experience-form.component.html',
  styleUrls: ['./experience-form.component.scss']
})
export class ExperienceFormComponent extends SidebarContentComponent implements OnInit {

  formExperience: FormGroup;
  currentUserId: string;
  experience: WorkExperience;
  isUpdate: boolean;
  isSubmitted = false;
  constant = Constants;

  constructor(
    private formBuilder: FormBuilder,
    private experienceService: ExperienceService,
    private authService: AuthService,
    private toasterService: ToasterService,
    private sidebarService: SidebarService,
    private userContextService: UserContextService
  ) {
    super();
  }

  ngOnInit(): void {
    this.experience = this.sidebarInput ? {...this.sidebarInput.experience} : undefined;
    this.isUpdate = !!this.sidebarInput?.experience?.id;
    this.initForm(this.experience);
  }

  onSubmit(): void {
    this.isSubmitted = true;

    if (this.formExperience.invalid) {
      return;
    }

    const experience: WorkExperience = {...this.formExperience.value};
    let experience$: Observable<WorkExperience>;

    if (this.isUpdate) {
      // on set manuellement l'id de l'expérience car cette information n'est pas stockée dans le formulaire
      experience.id = this.sidebarInput.experience.id;
      experience$ = this.experienceService.updateExperience(this.currentUserId, experience);
    } else {
      experience$ = this.experienceService.createExperience(this.currentUserId, experience);
    }

    experience$.subscribe(
      experienceFromDb => {
        if (this.isUpdate) {
          this.userContextService.updateExperience(experienceFromDb);
        } else {
          this.userContextService.addExperience(experienceFromDb);
        }
      },
      () => {
        const action = this.isUpdate ? 'la mise à jour' : `l'ajout`;
        this.toasterService.error('Oops', `Une erreur est survenue lors de ${action} de votre expérience`);
      },
      () => this.sidebarService.close()
    );
  }

  private initForm(experience: WorkExperience): void {
    this.formExperience = this.formBuilder
      .group({
        companyName: [experience?.companyName ?? '', Validators.required],
        label: [experience?.label ?? '', Validators.required],
        description: [experience?.description ?? ''],
        startedAt: [
          experience?.startedAt ? new Date(this.experience.startedAt) : '', Validators.required
        ],
        finishedAt: experience?.finishedAt ? new Date(this.experience.finishedAt) : ''
      });
  }
}
