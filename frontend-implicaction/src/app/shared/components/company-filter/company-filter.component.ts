import {Component, OnInit} from '@angular/core';
import {CompanyFilterContextService} from '../../services/company-filter-context.service';
import {Criteria} from '../../models/Criteria';

@Component({
  selector: 'app-company-filter',
  templateUrl: './company-filter.component.html',
})
export class CompanyFilterComponent implements OnInit {

  criteria: Criteria = {};

  constructor(private filterContextService: CompanyFilterContextService) {
  }

  ngOnInit(): void {
    this.filterContextService
      .observe()
      .subscribe(criteria => this.criteria = criteria);
  }

  onKeywordChange(keyword: string): void {
    this.criteria.keyword = keyword;
    this.filterContextService.criteria = this.criteria;
  }
}
