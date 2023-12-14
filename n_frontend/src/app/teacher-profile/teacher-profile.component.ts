import {Component, inject, OnDestroy, OnInit} from '@angular/core';
import {JwtHelperService} from "@auth0/angular-jwt";
import {TeacherService} from "../teacher.service";
import {FormsModule} from "@angular/forms";
import {CommonModule} from "@angular/common";

@Component({
  selector: 'app-teacher-profile',
  standalone: true,
  imports: [
    FormsModule,
    CommonModule
  ],
  templateUrl: './teacher-profile.component.html',
  styleUrl: './teacher-profile.component.css'
})
export class TeacherProfileComponent implements OnInit, OnDestroy{
  private TeacherService = inject(TeacherService);
  teacherData: any = {};
  private alive: boolean = true;
  constructor() {}

  ngOnInit() {
    this.fetchData();
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
    const token=localStorage.getItem('token');
    if (token) {
      const helper = new JwtHelperService();
      const decodedToken = helper.decodeToken(token);
      const email = decodedToken.sub;
      console.log("email: ", email);
      this.TeacherService.getTeacherByEmail(email).then(teacher => {
        this.teacherData = teacher;
        console.log("teacher_obj",teacher);
      });
    } else {
      console.error('Token not found in localStorage');
    }
  }

  updateTeacher() {
    const password = document.getElementById('password') as HTMLInputElement;
    console.log("aaaaaaaaaaaaaaaa",password.value);
  }

}
