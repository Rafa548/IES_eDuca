import {Component, inject, OnDestroy, OnInit} from '@angular/core';
import {JwtHelperService} from "@auth0/angular-jwt";
import {TeacherService} from "../teacher.service";
import {FormsModule} from "@angular/forms";
import {CommonModule} from "@angular/common";
import {ApiDataService} from "../api-data.service";
import { TeacherNavbarComponent } from '../teacher-navbar/teacher-navbar.component';

@Component({
  selector: 'app-teacher-profile',
  standalone: true,
  imports: [
    FormsModule,
    CommonModule,
    TeacherNavbarComponent
  ],
  templateUrl: './teacher-profile.component.html',
  styleUrl: './teacher-profile.component.css'
})
export class TeacherProfileComponent implements OnInit, OnDestroy{
  private TeacherService = inject(TeacherService);
  teacherData: any = {};
  private alive: boolean = true;
  private ApiDataService = inject(ApiDataService);

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
    const name = this.teacherData.name;
    const email = document.getElementById('email') as HTMLInputElement;
    const nmec = document.getElementById('nmec') as HTMLInputElement;
   

    const json = { name : name.value, email : email.value, password : password.value, nmec : nmec.value};


    this.ApiDataService.updateTeacher(localStorage.getItem('token'), json).then((response : any) => {
     
    });
    
  }

}
