import { ComponentFixture, TestBed } from '@angular/core/testing';

import { StudentAdminInfoComponent } from './student-admin-info.component';

describe('StudentAdminInfoComponent', () => {
  let component: StudentAdminInfoComponent;
  let fixture: ComponentFixture<StudentAdminInfoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [StudentAdminInfoComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(StudentAdminInfoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
