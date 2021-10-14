import {TestBed} from '@angular/core/testing';

import {FilterContextService} from './filter-context.service';

describe('FilterContextService', () => {
  let service: FilterContextService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(FilterContextService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
