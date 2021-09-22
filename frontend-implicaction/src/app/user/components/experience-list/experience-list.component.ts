import {Component, Input, OnInit} from '@angular/core';
import {WorkExperience} from '../../../shared/models/work-experience';
import {UserService} from '../../services/user.service';
import {AuthService} from '../../../shared/services/auth.service';
import {ToasterService} from '../../../core/services/toaster.service';
import {finalize} from 'rxjs/operators';

@Component({
  selector: 'app-experience-list',
  templateUrl: './experience-list.component.html',
  styleUrls: ['./experience-list.component.scss']
})
export class ExperienceListComponent implements OnInit {

  @Input()
  experiences: WorkExperience[];
  experienceCopies: WorkExperience[];
  currentUserId: string;
  isEditing = false;

  constructor(
    private userService: UserService,
    private authService: AuthService,
    private toasterService: ToasterService
  ) {
  }

  trackByExperienceId = (index: number, experience: WorkExperience) => experience.id;

  ngOnInit(): void {
    this.currentUserId = this.authService.getUserId();
  }

  toggleModeEdition(): void {
    // on clone individuellement chaque élément de la liste pour ne pas impacter la liste experiences et pouvoir rollback
    this.experienceCopies = this.experiences.map(x => Object.assign({}, x));
    this.isEditing = !this.isEditing;
  }

  updateExperiences(): void {
    this.userService
      .updateExperiences(this.currentUserId, this.experienceCopies)
      .pipe(finalize(() => this.isEditing = false))
      .subscribe(
        experienceUpdates => this.experiences = [...experienceUpdates],
        () => this.toasterService.error('Oops', 'Une erreur est survenu lors de la mise à jour des données'),
        () => this.toasterService.success('Ok', 'Le changement des données a bien été effectué')
      );
  }
}
