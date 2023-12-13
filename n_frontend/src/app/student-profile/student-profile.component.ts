import { Component, inject, OnInit, OnDestroy } from '@angular/core';
import { StudentService } from "../student.service";
import { JwtHelperService } from "@auth0/angular-jwt";
import { FormsModule } from "@angular/forms";
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-student-profile',
  standalone: true,
  imports: [
    FormsModule, CommonModule
  ],
  templateUrl: './student-profile.component.html',
  styleUrl: './student-profile.component.css'
})
export class StudentProfileComponent implements OnInit, OnDestroy {
  studentData: any = {};
  private alive = true;

  constructor(private studentService: StudentService) { }

  ngOnInit() {
    this.loadStudentData();
    this.updateContentPeriodically();
  }

  ngOnDestroy() {
    this.alive = false;
  }

  updateContentPeriodically() {
    setInterval(() => {
      if (this.alive) {
        this.loadStudentData();
      }
    }, 5000);
  }

  loadStudentData() {
    const token = localStorage.getItem('token');
    const helper = new JwtHelperService();
    if (!token) {
      console.error('Token not found in localStorage');
      return;
    }
    const decodedToken = helper.decodeToken(token);
    const email = decodedToken.sub;
    this.studentService.getStudentByEmail(email).then(student => {
      this.studentData = student;
      console.log("Student data: ", student);
    });
  }

  updateStudent() {
    const password = document.getElementById('password') as HTMLInputElement;
    const studentSub = {
      password: password.value,
      nmec: this.studentData.nmec
    };
    const token = localStorage.getItem('token');
    console.log("studentSub: ", studentSub);
    this.studentService.updateStudent(token, studentSub).then(student => {
      // Handle update response if needed
    });
  }
}
