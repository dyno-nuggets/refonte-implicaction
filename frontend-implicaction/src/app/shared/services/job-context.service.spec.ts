import { TestBed } from '@angular/core/testing';

import { JobContextService } from './job-context.service';

describe('JobContextService', () => {
  let service: JobContextService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(JobContextService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
