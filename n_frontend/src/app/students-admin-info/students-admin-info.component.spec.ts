import { ComponentFixture, TestBed } from '@angular/core/testing';

import { StudentsAdminInfoComponent } from './students-admin-info.component';

describe('StudentsAdminInfoComponent', () => {
  let component: StudentsAdminInfoComponent;
  let fixture: ComponentFixture<StudentsAdminInfoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [StudentsAdminInfoComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(StudentsAdminInfoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
