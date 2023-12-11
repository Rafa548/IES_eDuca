import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TeachersAdminInfoComponent } from './teachers-admin-info.component';

describe('TeachersAdminInfoComponent', () => {
  let component: TeachersAdminInfoComponent;
  let fixture: ComponentFixture<TeachersAdminInfoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TeachersAdminInfoComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(TeachersAdminInfoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
