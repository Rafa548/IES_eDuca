import { ComponentFixture, TestBed } from '@angular/core/testing';

import { StudentHomeComponent } from './student-home.component';

describe('StudentHomeComponent', () => {
  let component: StudentHomeComponent;
  let fixture: ComponentFixture<StudentHomeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [StudentHomeComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(StudentHomeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
