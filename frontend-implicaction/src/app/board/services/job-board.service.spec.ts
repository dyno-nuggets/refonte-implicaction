import {TestBed} from '@angular/core/testing';

import {JobBoardService} from './job-board.service';

describe('JobBoardService', () => {
  let service: JobBoardService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(JobBoardService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
