import {Component, Input, OnInit} from '@angular/core';
import {JobPosting} from '../../../shared/models/job-posting';
import {Univers} from '../../../shared/enums/univers';
import {ApplyStatusCode} from '../../../board/enums/apply-status-enum';
import {JobApplicationService} from '../../../board/services/job-application.service';
import {ToasterService} from '../../../core/services/toaster.service';
import {finalize} from 'rxjs/operators';
import {AuthService} from '../../../shared/services/auth.service';
import {RoleEnumCode} from '../../../shared/enums/role.enum';

@Component({
  selector: 'app-job-card',
  templateUrl: './job-card.component.html',
  styleUrls: ['./job-card.component.scss']
})
export class JobCardComponent implements OnInit {

  @Input()
  job: JobPosting;

  univers = Univers;
  isLoading = false;
  isPremium = false;

  constructor(
    private jobBoardService: JobApplicationService,
    private toasterService: ToasterService,
    private authService: AuthService
  ) {
  }

  ngOnInit(): void {
    this.isPremium = this.authService
      .getCurrentUser()
      ?.roles
      .includes(RoleEnumCode.PREMIUM);
  }

  addToJobBoard(): void {
    this.isLoading = true;
    this.jobBoardService
      .createApplication({jobId: this.job.id, status: ApplyStatusCode.PENDING})
      .pipe(finalize(() => this.isLoading = false))
      .subscribe(
        () => this.job.apply = true,
        () => {
          this.job.apply = false;
          this.toasterService.error('Oops', `Une erreur est survenue lors de l'ajout de l'offre à votre board`);
        }
      );
  }
}
