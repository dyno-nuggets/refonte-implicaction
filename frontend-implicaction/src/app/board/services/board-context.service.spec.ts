import {TestBed} from '@angular/core/testing';

import {BoardContextService} from './board-context.service';

describe('BoardContextServiceService', () => {
  let service: BoardContextService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BoardContextService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
