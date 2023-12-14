import { TestBed } from '@angular/core/testing';

import { GradesService } from './grades.service';

describe('GradesService', () => {
  let service: GradesService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(GradesService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
