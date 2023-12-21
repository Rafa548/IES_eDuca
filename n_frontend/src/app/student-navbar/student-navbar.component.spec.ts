import { ComponentFixture, TestBed } from '@angular/core/testing';

import { StudentNavbarComponent } from './student-navbar.component';

describe('StudentNavbarComponent', () => {
  let component: StudentNavbarComponent;
  let fixture: ComponentFixture<StudentNavbarComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [StudentNavbarComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(StudentNavbarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
