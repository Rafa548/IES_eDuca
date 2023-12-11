import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TeacherAdminInfoComponent } from './teacher-admin-info.component';

describe('TeacherAdminInfoComponent', () => {
  let component: TeacherAdminInfoComponent;
  let fixture: ComponentFixture<TeacherAdminInfoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TeacherAdminInfoComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(TeacherAdminInfoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
