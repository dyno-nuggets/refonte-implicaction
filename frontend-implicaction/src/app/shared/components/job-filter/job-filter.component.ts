import {Component, OnDestroy, OnInit} from '@angular/core';
import {JobCriteriaFilter} from '../../../job/models/job-criteria-filter';
import {JobFilterContextService} from '../../../job/services/job-filter-context.service';
import {Subscription} from 'rxjs';
import {ContractEnum, ContractEnumCode} from '../../enums/contract.enum';

@Component({
  selector: 'app-job-filter',
  templateUrl: './job-filter.component.html',
  styleUrls: ['./job-filter.component.scss']
})
export class JobFilterComponent implements OnInit, OnDestroy {

  contractTypes = ContractEnum.all();
  criteria: JobCriteriaFilter = {};
  subscription: Subscription;

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

  ngOnDestroy(): void {
    this.subscription?.unsubscribe();
  }
}
