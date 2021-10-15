import {Component, OnInit} from '@angular/core';
import {JobPosting} from '../../../../shared/models/job-posting';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {JobService} from '../../../../job/services/job.service';
import {ToasterService} from '../../../../core/services/toaster.service';
import {SidebarService} from '../../../../shared/services/sidebar.service';
import {SidebarContentComponent} from '../../../../shared/models/sidebar-props';
import {Observable} from 'rxjs';
import {CompanyService} from '../../../../job/services/company.service';
import {Pageable} from '../../../../shared/models/pageable';
import {Constants} from '../../../../config/constants';
import {Company} from '../../../../shared/models/company';
import {ContractEnum} from '../../../../shared/enums/contract.enum';
import {StatusEnum} from '../../../../shared/enums/status.enum';

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
  contracts = ContractEnum.all();
  companies: Company[] = [];
  pageable: Pageable = Constants.PAGEABLE_DEFAULT;

  constructor(
    private formBuilder: FormBuilder,
    private jobService: JobService,
    private companyService: CompanyService,
    private toasterService: ToasterService,
    private sidebarService: SidebarService,
  ) {
    super();
  }

  ngOnInit(): void {
    this.companyService.getAll(this.pageable)
      .subscribe(data => {
        this.companies = data.content;
      });
    this.jobPosting = this.sidebarInput ? {...this.sidebarInput.job} : undefined;
    this.isUpdate = !!this.sidebarInput?.job?.id;
    this.initForm(this.jobPosting);
  }

  private initForm(jobPosting: JobPosting): void {
    this.formJob = this.formBuilder
      .group({
        keywords: [jobPosting?.keywords ?? ''],
        location: [jobPosting?.location ?? '', Validators.required],
        salary: [jobPosting?.salary ?? '', Validators.required],
        title: [jobPosting?.title ?? '', Validators.required],
        shortDescription: [jobPosting?.shortDescription ?? '', Validators.required],
        description: [jobPosting?.description ?? '', Validators.required],
        contractType: [jobPosting?.contractType ?? '', Validators.required],
        company: [jobPosting?.company ?? '']
      });

  }

  onSubmit(): void {
    this.isSubmitted = true;

    if (this.formJob.invalid) {
      return;
    }
    const job: JobPosting = {...this.formJob.value};
    job.contractType = ContractEnum.from(this.formJob.controls.contractType.value);
    let job$: Observable<JobPosting>;
    if (this.isUpdate) {
      job.status = this.jobPosting.status;
      job.id = this.sidebarInput.job.id;
      job$ = this.jobService.updateJob(job);
    } else {
      job.status = StatusEnum.JOB_AVAILABLE;
      job$ = this.jobService.createJob(job);
    }
    job$.subscribe(
      () => {
        // TODO mettre en place le context service
      },
      () => this.toasterService.error('Oops', `Une erreur est survenue lors de ${this.isUpdate ? 'la mise à jour' : `l'ajout`} de votre expérience`),
      () => this.sidebarService.close()
    );
  }

  onContractChange($event: Event): void {

  }

  onCompanyChange($event: Event): void {

  }
}
