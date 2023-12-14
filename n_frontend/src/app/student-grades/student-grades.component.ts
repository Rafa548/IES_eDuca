import {ChangeDetectorRef, Component, inject,OnInit,OnDestroy} from '@angular/core';
import { GradesService } from '../grades.service';
import { JwtHelperService } from '@auth0/angular-jwt';
import { StudentService } from '../student.service';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';

@Component({
  selector: 'app-student-grades',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './student-grades.component.html',
  styleUrls: ['./student-grades.component.css'],
})
export class StudentGradesComponent implements OnInit, OnDestroy {
  studentGrades: any[] = [];
  gradesService = inject(GradesService);
  studentService = inject(StudentService); // corrected from StudentService to studentService
  gradesMap: Map<string, number[]> = new Map();
  gradesArray: { name: string; grades: number[] }[] = [];
  private alive = true;
  isLoggedIn: boolean = localStorage.getItem('token') !== null;
  showDropdown: boolean = false;

  constructor(private cdr: ChangeDetectorRef, private router: Router) {this.fetchData()}

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
    this.gradesArray = [];
    this.gradesMap.clear();
    const token = localStorage.getItem('token');
    if (token) {
      const helper = new JwtHelperService();
      const decodedToken = helper.decodeToken(token);
      const email = decodedToken.sub;
      this.studentService.getIDofStudent(email).then((id) => {
        this.gradesService.getStudentGrades(id).then((grades) => {
          this.studentGrades = grades;
          this.buildGradesMap();
          this.cdr.detectChanges(); // Trigger change detection
        });
      });
    } else {
      console.error('Token not found in localStorage');
    }
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

  private buildGradesMap(): void {
    this.gradesMap.clear();
    for (let i = 0; i < this.studentGrades.length; i++) {
      const subjectName = this.studentGrades[i].subject.name;
      const grade = this.studentGrades[i].grade;

      if (!this.gradesMap.has(subjectName)) {
        this.gradesMap.set(subjectName, [grade]);
      } else {
        this.gradesMap.get(subjectName)?.push(grade);
      }
    }
    this.gradesArray = Array.from(this.gradesMap.entries()).map(([name, grades]) => ({
      name,
      grades,
    }));
  }
}
