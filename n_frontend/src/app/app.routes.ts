import {Routes} from '@angular/router';
import { LoginComponent } from './login/login.component';
import { StudentHomeComponent } from './student-home/student-home.component';
import { AdminDashboardComponent } from './admin-dashboard/admin-dashboard.component';
import { ClassStudentsAdminInfoComponent } from './class-students-admin-info/class-students-admin-info.component';
import { ClassesAdminInfoComponent } from './classes-admin-info/classes-admin-info.component';
import { StudentsAdminInfoComponent } from './students-admin-info/students-admin-info.component';
import { StudentAdminInfoComponent } from './student-admin-info/student-admin-info.component';
import {TeachersAdminInfoComponent} from "./teachers-admin-info/teachers-admin-info.component";
import {TeacherAdminInfoComponent} from "./teacher-admin-info/teacher-admin-info.component";
import {StudentGradesComponent} from "./student-grades/student-grades.component";
import {StudentProfileComponent} from "./student-profile/student-profile.component";
import {TeacherHomeComponent} from "./teacher-home/teacher-home.component";
import {TeacherClassStudentsComponent} from "./teacher-class-students/teacher-class-students.component";
import { TeacherProfileComponent } from './teacher-profile/teacher-profile.component';


export const routes: Routes = [
  { path: '', component: LoginComponent },
  { path: 'student_home', component: StudentHomeComponent },
  { path: 'admin_dashboard', component: AdminDashboardComponent },
  { path: 'admin/class_students', component: ClassStudentsAdminInfoComponent },
  { path: 'admin/class/:classId', component: ClassesAdminInfoComponent },
  { path: 'admin/students', component: StudentsAdminInfoComponent },
  { path:'admin/student/:nmec', component: StudentAdminInfoComponent},
  { path : 'admin/teachers', component: TeachersAdminInfoComponent},
  { path: 'admin/teacher/:nmec', component: TeacherAdminInfoComponent},
  { path: 'admin_dashboard', component: AdminDashboardComponent },
  { path: 'admin/class_students', component: ClassStudentsAdminInfoComponent },
  { path: 'admin/class/:classId', component: ClassesAdminInfoComponent },
  { path: 'admin/students', component: StudentsAdminInfoComponent },
  {path: 'student_grades', component: StudentGradesComponent},
  {path: 'student_profile', component: StudentProfileComponent},
  {path: 'teacher_home', component: TeacherHomeComponent},
  {path: 'teacher/class/:classname/students', component: TeacherClassStudentsComponent},
  {path: 'teacher/profile', component: TeacherProfileComponent}
];

