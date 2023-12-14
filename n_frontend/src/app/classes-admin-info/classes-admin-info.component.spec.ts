import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ClassesAdminInfoComponent } from './classes-admin-info.component';

describe('ClassesAdminInfoComponent', () => {
  let component: ClassesAdminInfoComponent;
  let fixture: ComponentFixture<ClassesAdminInfoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ClassesAdminInfoComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ClassesAdminInfoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
