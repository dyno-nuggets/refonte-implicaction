import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import {WorkExperience} from '../../../shared/models/work-experience';
import {UserService} from '../../services/user.service';
import {AuthService} from '../../../shared/services/auth.service';
import {ToasterService} from '../../../core/services/toaster.service';
import {SidebarService} from '../../../shared/services/sidebar.service';
import {ExperienceFormComponent} from '../experience-form/experience-form.component';
import {ExperiencesContexteService} from '../../../shared/services/experiences-contexte.service';
import {ActivatedRoute} from '@angular/router';
import {Subscription} from 'rxjs';

@Component({
  selector: 'app-experience-list',
  templateUrl: './experience-list.component.html',
  styleUrls: ['./experience-list.component.scss']
})
export class ExperienceListComponent implements OnInit, OnDestroy {

  @Input()
  experiences: WorkExperience[];
  isEditing = false;
  canEdit = false;

  private currentUserId: string;
  private subscription: Subscription;

  constructor(
    private userService: UserService,
    private authService: AuthService,
    private toasterService: ToasterService,
    private sidebarService: SidebarService,
    private ecs: ExperiencesContexteService,
    private route: ActivatedRoute
  ) {
  }

  ngOnDestroy(): void {
    this.subscription?.unsubscribe();
  }

  trackByExperienceId = (index: number, experience: WorkExperience) => experience.id;

  ngOnInit(): void {
    this.currentUserId = this.authService.getUserId();
    this.route
      .paramMap
      .subscribe(paramMap => {
        const userId = paramMap.get('userId');
        this.canEdit = userId === this.currentUserId;
      });
    this.subscription = this.ecs
      .observeExperiences()
      .subscribe(experiences => this.experiences = experiences);
  }

  deleteExperience(experience: WorkExperience): void {
    // TODO: utiliser l'ecs pour supprimer l'expérience côté font après avoir envoyé la requête de suppression au back
    const index = this.experiences.indexOf(experience);

    if (index >= 0) {
      this.experiences.splice(index, 1);
    }
  }

  addElement(): void {
    this.sidebarService
      .open({
        title: 'Ajouter une expérience professionelle',
        component: ExperienceFormComponent,
        width: 650
      });
  }
}
