import {Component, OnInit} from '@angular/core';
import {ContractTypeCode, ContractTypeEnum} from '../../../job/models/contract-type-enum';
import {FilterContextService} from '../../services/filter-context.service';
import {CriteriaFilter} from '../../models/criteria-filter';

@Component({
  selector: 'app-job-filter',
  templateUrl: './job-filter.component.html',
  styleUrls: ['./job-filter.component.scss']
})
export class JobFilterComponent implements OnInit {

  contractTypes = ContractTypeEnum.all();
  criteria: CriteriaFilter = {};

  constructor(private filterContextService: FilterContextService) {
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
