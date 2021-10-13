import {Component, OnInit} from '@angular/core';
import {ContractTypeCode, ContractTypeEnum} from '../../../job/models/contract-type-enum';
import {JobCriteriaFilter} from '../../../job/models/job-criteria-filter';
import {JobFilterContextService} from '../../../job/services/job-filter-context.service';

@Component({
  selector: 'app-job-filter',
  templateUrl: './job-filter.component.html',
  styleUrls: ['./job-filter.component.scss']
})
export class JobFilterComponent implements OnInit {

  contractTypes = ContractTypeEnum.all();
  criteria: JobCriteriaFilter = {};

  constructor(private filterContextService: JobFilterContextService) {
  }

  ngOnInit(): void {
    this.filterContextService
      .observeFilter()
      .subscribe(criteria => this.criteria = criteria);
  }

  onContractTypeChange(code: ContractTypeCode): void {
    this.criteria.contractType = code;
    this.filterContextService.setFilter(this.criteria);
  }
}
