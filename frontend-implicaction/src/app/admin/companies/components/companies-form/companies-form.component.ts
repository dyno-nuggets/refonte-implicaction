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
import {CompanyContextServiceService} from '../../../../shared/services/company-context-service.service';

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
    private companyContextService: CompanyContextServiceService
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
      companySave => {
        if (this.isUpdate) {
          this.updateFields(companySave);
        } else {
          this.companyContextService.notify(companySave);
        }
      },
      () => {
        const actionType = this.isUpdate ? 'la mise à jour' : `l'ajout`;
        this.toasterService.error('Oops', `Une erreur est survenue lors de ${actionType} de votre entreprise.`);
      },
      () => {
        const actionType = this.isUpdate ? 'mise à jour' : `ajoutée`;
        this.toasterService.success('Succès', `L'entreprise ${company.name} a été ${actionType} avec succès.`);
        this.sidebarService.close();
      }
    );
  }

  private updateFields(companyUpdate: Company): void {
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
