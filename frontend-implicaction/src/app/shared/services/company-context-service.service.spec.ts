import {TestBed} from '@angular/core/testing';

import {CompanyContextServiceService} from './company-context-service.service';

describe('CompanyContextServiceService', () => {
  let service: CompanyContextServiceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CompanyContextServiceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
