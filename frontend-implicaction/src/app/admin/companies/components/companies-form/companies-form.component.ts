import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {ToasterService} from '../../../../core/services/toaster.service';
import {SidebarService} from '../../../../shared/services/sidebar.service';
import {SidebarContentComponent} from '../../../../shared/models/sidebar-props';
import {Observable} from 'rxjs';
import {Pageable} from '../../../../shared/models/pageable';
import {Constants} from '../../../../config/constants';
import {Company} from '../../../../shared/models/company';
import {CompanyService} from '../../../../company/services/company.service';

@Component({
  selector: 'app-companies-form',
  templateUrl: './companies-form.component.html',
  styleUrls: ['./companies-form.component.scss']
})
export class CompaniesFormComponent extends SidebarContentComponent implements OnInit {

  formCompany: FormGroup;
  currentUserId: string;
  company: Company;
  isUpdate: boolean;
  isSubmitted = false;
  pageable: Pageable = Constants.PAGEABLE_DEFAULT;

  constructor(
    private formBuilder: FormBuilder,
    private companyService: CompanyService,
    private toasterService: ToasterService,
    private sidebarService: SidebarService,
  ) {
    super();
  }

  ngOnInit(): void {
    this.company = this.sidebarInput ? {...this.sidebarInput.company} : undefined;
    this.isUpdate = !!this.sidebarInput?.company?.id;
    this.initForm(this.company);
  }

  onSubmit(): void {
    this.isSubmitted = true;

    if (this.formCompany.invalid) {
      return;
    }
    const company: Company = {...this.formCompany.value};
    let company$: Observable<Company>;
    if (this.isUpdate) {
      company.id = this.sidebarInput.company.id;
      company$ = this.companyService.updateCompany(company);
    } else {
      company$ = this.companyService.createCompany(company);
    }
    company$.subscribe(
      (companyUpdate) => {
        if (this.isUpdate) {
          this.updateFields(companyUpdate);
        }
      },
      () => this.toasterService.error('Oops', `Une erreur est survenue lors de ${this.isUpdate ? 'la mise à jour' : `l'ajout`} de votre entreprise.`),
      () => this.sidebarService.close()
    );
  }

  private updateFields = (companyUpdate: Company) => {
    this.sidebarInput.company.name = companyUpdate.name;
    this.sidebarInput.company.url = companyUpdate.url;
    this.sidebarInput.company.logo = companyUpdate.logo;
    this.sidebarInput.company.description = companyUpdate.description;
  }

  private initForm(company: Company): void {
    this.formCompany = this.formBuilder
      .group({
        description: [company?.description ?? '', Validators.required],
        logo: [company?.logo ?? '', Validators.required],
        name: [company?.name ?? '', Validators.required],
        url: [company?.url ?? '']
      });
  }
}
