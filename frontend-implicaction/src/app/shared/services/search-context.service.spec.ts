import {TestBed} from '@angular/core/testing';

import {SearchContextService} from './search-context.service';

describe('SearchContextService', () => {
  let service: SearchContextService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SearchContextService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
