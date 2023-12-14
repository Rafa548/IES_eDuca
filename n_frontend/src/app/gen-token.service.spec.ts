import { TestBed } from '@angular/core/testing';

import { GenTokenService } from './gen-token.service';

describe('GenTokenService', () => {
  let service: GenTokenService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(GenTokenService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
