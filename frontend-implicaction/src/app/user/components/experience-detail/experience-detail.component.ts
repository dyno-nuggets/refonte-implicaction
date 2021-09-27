import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {WorkExperience} from '../../../shared/models/work-experience';
import {SidebarService} from '../../../shared/services/sidebar.service';
import {ExperienceFormComponent} from '../experience-form/experience-form.component';
import {AuthService} from '../../../shared/services/auth.service';
import {ActivatedRoute} from '@angular/router';

@Component({
  selector: 'app-experience-detail',
  templateUrl: './experience-detail.component.html',
  styleUrls: ['./experience-detail.component.scss']
})
export class ExperienceDetailComponent implements OnInit {

  @Input()
  experience: WorkExperience;
  @Output()
  deleteEmitter = new EventEmitter<WorkExperience>();
  yearRange = `1900:${new Date().getFullYear() + 1}`;
  canEdit = false;

  private currentUserId: string;


  constructor(
    private sidebarService: SidebarService,
    private authService: AuthService,
    private route: ActivatedRoute
  ) {
  }

  ngOnInit(): void {
    this.currentUserId = this.authService.getUserId();
    this.route
      .paramMap
      .subscribe(paramMap => {
        const userId = paramMap.get('userId');
        this.canEdit = userId === this.currentUserId;
      });
  }

  deleteExperience(experience: WorkExperience): void {
    this.deleteEmitter.next(experience);
  }

  editExperience(experience: WorkExperience): void {
    this.sidebarService
      .open({
        title: 'Editer une formation',
        input: {experience},
        component: ExperienceFormComponent,
        width: 650
      });
  }


}
