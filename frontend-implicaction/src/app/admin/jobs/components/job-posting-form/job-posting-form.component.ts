import {Component, OnInit} from '@angular/core';
import {JobPosting} from '../../../../shared/models/job-posting';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {JobService} from '../../../../job/services/job.service';
import {ToasterService} from '../../../../core/services/toaster.service';
import {SidebarService} from '../../../../shared/services/sidebar.service';
import {SidebarContentComponent} from '../../../../shared/models/sidebar-props';
import {Observable} from 'rxjs';
import {ContractType} from '../../../../shared/models/contractType';

@Component({
  selector: 'app-job-posting-form',
  templateUrl: './job-posting-form.component.html',
  styleUrls: ['./job-posting-form.component.scss']
})
export class JobPostingFormComponent extends SidebarContentComponent implements OnInit {

  readonly YEAR_RANGE = `1900:${new Date().getFullYear() + 1}`;

  formJob: FormGroup;
  currentUserId: string;
  jobPosting: JobPosting;
  isUpdate: boolean;
  isSubmitted = false;
  contractTypes: ContractType[];

  constructor(
    private formBuilder: FormBuilder,
    private jobService: JobService,
    private toasterService: ToasterService,
    private sidebarService: SidebarService,
  ) {
    super();
  }

  ngOnInit(): void {
    this.jobPosting = this.sidebarInput ? {...this.sidebarInput.job} : undefined;
    this.isUpdate = !this.sidebarInput?.job?.id;
    this.initForm(this.jobPosting);
  }

  private initForm(jobPosting: JobPosting): void {
    this.formJob = this.formBuilder
      .group({
        keywords: [jobPosting?.keywords ?? '', Validators.required],
        location: [jobPosting?.location ?? '', Validators.required],
        salary: [jobPosting?.salary ?? '', Validators.required],
        title: [jobPosting?.title ?? '', Validators.required],
        description: [jobPosting?.description ?? '', Validators.required],
        contractType: [jobPosting?.contractType ?? ''],
        company: [jobPosting?.company ?? ''],
        createdAt: [
          jobPosting?.createdAt ? new Date(this.jobPosting.createdAt) : '', Validators.required
        ],
      });

  }

  onSubmit(): void {
    this.isSubmitted = true;

    if (this.formJob.invalid) {
      return;
    }
    const job: JobPosting = {...this.formJob.value};
    let job$: Observable<JobPosting>;
    if (this.isUpdate) {
      job.id = this.sidebarInput.job.id;
      job$ = this.jobService.updateJobPosting(job);
    } else {
      job$ = this.jobService.createJobPosting(job);
    }
    job$.subscribe(
      () => {

      },
      () => this.toasterService.error('Oops', `Une erreur est survenue lors de ${this.isUpdate ? 'la mise à jour' : `l'ajout`} de votre expérience`),
      () => this.sidebarService.close()
    );
  }
}
