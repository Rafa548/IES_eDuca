import { Component, inject, OnInit, OnDestroy } from '@angular/core';
import { StudentService } from "../student.service";
import { JwtHelperService } from "@auth0/angular-jwt";
import { FormsModule } from "@angular/forms";
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { StudentNavbarComponent } from '../student-navbar/student-navbar.component';

@Component({
  selector: 'app-student-profile',
  standalone: true,
  imports: [
    FormsModule, CommonModule, StudentNavbarComponent
  ],
  templateUrl: './student-profile.component.html',
  styleUrl: './student-profile.component.css'
})
export class StudentProfileComponent implements OnInit, OnDestroy {
  studentData: any = {};
  private alive = true;
  isLoggedIn: boolean = localStorage.getItem('token') !== null;
  showDropdown: boolean = false;
  

  constructor(private studentService: StudentService, private router: Router) { }

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

  login() {
    // Implement your login logic here
    // For example, navigate to the login page
    this.router.navigate(['/']);
  }

  logout() {
    localStorage.removeItem('token');
    this.isLoggedIn = false;
    this.router.navigate(['/']);
  }

  redirectTo(path: string): void {
    switch (path) {
      case 'grades':
        this.router.navigate(['student_grades']); // Change the route path as needed
        break;
      case 'profile':
        this.router.navigate(['student_profile']); // Change the route path as needed
        break;
      case 'home':
        this.router.navigate(['student_home']); // Change the route path as needed
        break;
      default:
        break;
    }
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
