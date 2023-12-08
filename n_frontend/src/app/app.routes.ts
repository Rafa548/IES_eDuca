import {Routes} from '@angular/router';
import { LoginComponent } from './login/login.component';
import { StudentHomeComponent } from './student-home/student-home.component';
import { AdminDashboardComponent } from './admin-dashboard/admin-dashboard.component';
import { ClassStudentsAdminInfoComponent } from './class-students-admin-info/class-students-admin-info.component';
import { ClassesAdminInfoComponent } from './classes-admin-info/classes-admin-info.component';


export const routes: Routes = [
  { path: '', component: LoginComponent },
  { path: 'student_home', component: StudentHomeComponent },
  { path: 'admin_dashboard', component: AdminDashboardComponent },
  { path: 'admin/class_students', component: ClassStudentsAdminInfoComponent },
  { path: 'admin/class/:classId', component: ClassesAdminInfoComponent },
];

