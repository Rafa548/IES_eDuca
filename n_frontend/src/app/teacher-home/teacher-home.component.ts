import {Component, inject, OnInit, OnDestroy} from '@angular/core';
import {TeacherService} from "../teacher.service";
import {JwtHelperService} from "@auth0/angular-jwt";
import { CommonModule } from '@angular/common';
import {Router} from '@angular/router';
@Component({
  selector: 'app-teacher-home',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './teacher-home.component.html',
  styleUrl: './teacher-home.component.css'
})
export class TeacherHomeComponent implements OnInit, OnDestroy{
  teacherData: any = {};
  TeacherService = inject(TeacherService);
  private alive = true;
  constructor(private router: Router) {this.fetchData()}

  ngOnInit() {
    this.updateContentPeriodically();
  }

  ngOnDestroy() {
    this.alive = false;
  }

  updateContentPeriodically() {
    setInterval(() => {
      if (this.alive) {
        this.fetchData();
      }
    }, 5000);
  }

  fetchData() {
    const token = localStorage.getItem('token');
    console.log("token1111",token)
    if (token) {
      const helper = new JwtHelperService();
      const decodedToken = helper.decodeToken(token);
      const email = decodedToken.sub;
      console.log("email: ", email);
      this.TeacherService.getTeacherByEmail(email).then(teacher => {
        this.teacherData = teacher;
        console.log("teacher: ", this.teacherData);
        const nmec= teacher.nmec;
        this.TeacherService.getTeacherClasses(nmec).then(classes => {
          this.teacherData.classes = classes;
          console.log(classes);
        });
      });
    }

  }

  goToClassStudents(classname: string|undefined) {
    console.log('classId: ', classname)
    if (classname !== undefined && classname !== null) {
      this.router.navigate(['teacher/class', classname, 'students']);
    } else {
      console.error('Invalid classname:', classname);
    }
  }
}
