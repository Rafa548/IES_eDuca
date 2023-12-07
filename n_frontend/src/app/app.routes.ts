import {Routes} from '@angular/router';
import { LoginComponent } from './login/login.component';
import { StudentHomeComponent } from './student-home/student-home.component';



export const routes: Routes = [
  { path: '', component: LoginComponent },
  { path: 'student_home', component: StudentHomeComponent },

];

