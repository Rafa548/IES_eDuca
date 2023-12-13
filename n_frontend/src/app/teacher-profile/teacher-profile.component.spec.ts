import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TeacherProfileComponent } from './teacher-profile.component';

describe('TeacherProfileComponent', () => {
  let component: TeacherProfileComponent;
  let fixture: ComponentFixture<TeacherProfileComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TeacherProfileComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(TeacherProfileComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
