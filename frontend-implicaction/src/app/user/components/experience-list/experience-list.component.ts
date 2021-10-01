import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import {WorkExperience} from '../../../shared/models/work-experience';
import {UserService} from '../../services/user.service';
import {AuthService} from '../../../shared/services/auth.service';
import {ToasterService} from '../../../core/services/toaster.service';
import {SidebarService} from '../../../shared/services/sidebar.service';
import {ExperienceFormComponent} from '../experience-form/experience-form.component';
import {UserContexteService} from '../../../shared/services/user-contexte.service';
import {Subscription} from 'rxjs';

@Component({
  selector: 'app-experience-list',
  templateUrl: './experience-list.component.html',
  styleUrls: ['./experience-list.component.scss']
})
export class ExperienceListComponent implements OnInit, OnDestroy {

  @Input()
  readOnly = true;
  experiences: WorkExperience[];

  private subscription: Subscription;

  constructor(
    private userService: UserService,
    private authService: AuthService,
    private toasterService: ToasterService,
    private sidebarService: SidebarService,
    private userContexteService: UserContexteService,
  ) {
  }

  trackByExperienceId = (index: number, experience: WorkExperience) => experience.id;

  ngOnInit(): void {
    this.subscription = this.userContexteService
      .observeUser()
      .subscribe(user => this.experiences = user.experiences);
  }

  onAddExperience(): void {
    this.sidebarService
      .open({
        title: 'Ajouter une expérience professionnelle',
        component: ExperienceFormComponent,
        width: 650
      });
  }

  ngOnDestroy(): void {
    this.subscription?.unsubscribe();
  }
}
