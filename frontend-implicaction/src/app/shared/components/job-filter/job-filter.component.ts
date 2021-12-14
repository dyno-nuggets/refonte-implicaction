import {Component, OnDestroy, OnInit} from '@angular/core';
import {JobCriteriaFilter} from '../../../job/models/job-criteria-filter';
import {JobFilterContextService} from '../../../job/services/job-filter-context.service';
import {Subscription} from 'rxjs';
import {ContractEnum, ContractEnumCode} from '../../enums/contract.enum';
import {BusinessSectorEnum} from '../../enums/sector.enum';

@Component({
  selector: 'app-job-filter',
  templateUrl: './job-filter.component.html',
  styleUrls: ['./job-filter.component.scss']
})
export class JobFilterComponent implements OnInit, OnDestroy {

  readonly defaultSector = {label: `Tous les secteurs d'activités`, code: null};

  contractTypes = ContractEnum.all();
  criteria: JobCriteriaFilter = {};
  subscription: Subscription;
  businessSectors = [this.defaultSector, ...BusinessSectorEnum.all()];

  constructor(private filterContextService: JobFilterContextService) {
  }

  ngOnInit(): void {
    this.subscription = this.filterContextService
      .observe()
      .subscribe(criteria => this.criteria = criteria);
  }

  onContractTypeChange(code: ContractEnumCode): void {
    this.criteria.contractType = code;
    this.filterContextService.criteria = this.criteria;
  }

  onBusinessSectorChange({value}): void {
    this.criteria.businessSector = value;
    this.filterContextService.criteria = this.criteria;
  }

  ngOnDestroy(): void {
    this.subscription?.unsubscribe();
  }
}
