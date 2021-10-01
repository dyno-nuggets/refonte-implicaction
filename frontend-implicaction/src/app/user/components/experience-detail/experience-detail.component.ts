import {Component, Input} from '@angular/core';
import {WorkExperience} from '../../../shared/models/work-experience';
import {SidebarService} from '../../../shared/services/sidebar.service';
import {ExperienceFormComponent} from '../experience-form/experience-form.component';
import {ExperienceService} from '../../services/experience.service';
import {UserContexteService} from '../../../shared/services/user-contexte.service';
import {ToasterService} from '../../../core/services/toaster.service';

@Component({
  selector: 'app-experience-detail',
  templateUrl: './experience-detail.component.html',
  styleUrls: ['./experience-detail.component.scss']
})
export class ExperienceDetailComponent {

  readonly yearRange = `1900:${new Date().getFullYear() + 1}`;

  @Input()
  experience: WorkExperience;
  @Input()
  readOnly = true;


  constructor(
    private sidebarService: SidebarService,
    private toasterService: ToasterService,
    private experienceService: ExperienceService,
    private userContexteService: UserContexteService
  ) {
  }

  deleteExperience(experience: WorkExperience): void {
    if (this.readOnly) {
      return;
    }

    this.experienceService
      .deleteExperience(experience.id)
      .subscribe(
        () => {
          // on ne fait rien car la liste est mise à jour dans le complete
        },
        () => this.toasterService.error('Oops', 'Une erreur est survenue lors de la suppression de la formation'),
        () => this.userContexteService.removeExperience(experience)
      );
  }

  editExperience(experience: WorkExperience): void {
    if (this.readOnly) {
      return;
    }

    this.sidebarService
      .open({
        title: 'Editer une formation',
        input: {experience},
        component: ExperienceFormComponent,
        width: 650
      });
  }


}
