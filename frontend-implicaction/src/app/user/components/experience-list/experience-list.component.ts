import {AfterContentChecked, Component, Input, OnInit} from '@angular/core';
import {WorkExperience} from '../../../shared/models/work-experience';
import {UserService} from '../../services/user.service';
import {AuthService} from '../../../shared/services/auth.service';
import {ToasterService} from '../../../core/services/toaster.service';

@Component({
  selector: 'app-experience-list',
  templateUrl: './experience-list.component.html',
  styleUrls: ['./experience-list.component.scss']
})
export class ExperienceListComponent implements OnInit, AfterContentChecked {
  isEditing: boolean;

  @Input()
  experiences: WorkExperience[];
  experienceCopies: WorkExperience[];
  currentUserId: string;

  constructor(
    private userService: UserService,
    private authService: AuthService,
    private toasterService: ToasterService
  ) {
    this.isEditing = false;
  }

  ngAfterContentChecked(): void {
    this.experienceCopies = this.experiences;
  }

  ngOnInit(): void {
    this.currentUserId = this.authService.getUserId();
  }

  toggleModeEdition(): void {
    this.isEditing = !this.isEditing;
  }

  updateExperiences(): void {
    this.userService
      .updateExperiences(this.currentUserId, this.experienceCopies)
      .subscribe(
        experienceUpdates => {
          console.log(experienceUpdates);
          this.experiences = {...experienceUpdates};
          this.experienceCopies = {...experienceUpdates};
        },
        error => {
          this.toasterService.error('Oops', 'Une erreur est survenu lors de la mise à jour des données');
          console.log(error);
        },
        () => this.toasterService.success('Succes', 'Le changement des données a bien été effectué')
      );
    this.toggleModeEdition();
  }

  rollbackExperiences(): void {
    this.experienceCopies = {...this.experiences};
    this.toggleModeEdition();
  }

}
