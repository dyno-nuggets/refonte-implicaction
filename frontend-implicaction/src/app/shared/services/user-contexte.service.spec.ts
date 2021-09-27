import {TestBed} from '@angular/core/testing';

import {UserContexteService} from './user-contexte.service';

describe('userContexteService', () => {
  let service: UserContexteService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(UserContexteService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
