import {Component, OnInit} from '@angular/core';
import {CompanyCriteriaFilter} from '../../../job/models/company-criteria-filter';
import {CompanyFilterContextService} from '../../../company/services/company-filter-context.service';

@Component({
  selector: 'app-company-filter',
  templateUrl: './company-filter.component.html',
  styleUrls: ['./company-filter.component.scss']
})
export class CompanyFilterComponent implements OnInit {

  criteria: CompanyCriteriaFilter = {};

  constructor(private filterContextService: CompanyFilterContextService) {
  }

  ngOnInit(): void {
    this.filterContextService
      .observeFilter()
      .subscribe(criteria => this.criteria = criteria);
  }

  onKeywordChange(keyword: string): void {
    this.criteria.keyword = keyword;
    this.filterContextService.setFilter(this.criteria);
  }
}
