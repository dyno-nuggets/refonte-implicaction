import {TestBed} from '@angular/core/testing';
import {BaseContextServiceService} from './base-context-service.service';


describe('BaseEntityContextServiceService', () => {
  let service: BaseContextServiceService<any>;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BaseContextServiceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
