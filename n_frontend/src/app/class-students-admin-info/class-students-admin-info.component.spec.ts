import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ClassStudentsAdminInfoComponent } from './class-students-admin-info.component';

describe('ClassStudentsAdminInfoComponent', () => {
  let component: ClassStudentsAdminInfoComponent;
  let fixture: ComponentFixture<ClassStudentsAdminInfoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ClassStudentsAdminInfoComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ClassStudentsAdminInfoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
