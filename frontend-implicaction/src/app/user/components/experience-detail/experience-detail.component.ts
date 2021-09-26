import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {WorkExperience} from '../../../shared/models/work-experience';
import {SidebarService} from '../../../shared/services/sidebar.service';
import {ExperienceFormComponent} from '../experience-form/experience-form.component';
import {Utils} from '../../../shared/classes/utils';
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
  descriptionWithBr: string;
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
    // les sauts de ligne sont enregistrés en '\n', pour afficher un saut de ligne dans le html il faut remplacer
    //  ce caractère par une balise <br>
    this.descriptionWithBr = Utils.replaceNewLineByBrMarkup(this.experience.description);
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
