import {TestBed} from '@angular/core/testing';

import {ExperiencesContexteService} from './experiences-contexte.service';

describe('ExperiencesContexteService', () => {
  let service: ExperiencesContexteService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ExperiencesContexteService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
