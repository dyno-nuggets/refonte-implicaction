import {TestBed} from '@angular/core/testing';

import {JobFilterContextService} from './job-filter-context.service';

describe('JobFilterContextService', () => {
  let service: JobFilterContextService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(JobFilterContextService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
