import {Component, inject} from '@angular/core';
import {NgForOf, NgIf} from "@angular/common";
import { ApiDataService } from '../api-data.service';
import {Class} from "../class";
import {Router} from "@angular/router";

@Component({
  selector: 'app-classes-admin-info',
  standalone: true,
  imports: [
    NgIf,
    NgForOf
  ],
  templateUrl: './classes-admin-info.component.html',
  styleUrl: './classes-admin-info.component.css'
})
export class ClassesAdminInfoComponent {
  selectedContent: string = ''; // Variable to track selected content
  class: any | undefined ;
  ApiDataService = inject(ApiDataService);
  classId: number;
  grades: any[] = [];


  constructor(private router: Router) {
    this.classId = Number(this.router.url.split('/').pop());

    this.ApiDataService.getClass(localStorage.getItem('token'), this.classId).then((class_ : any) => {
      this.class = class_;
      //console.log(class_.students[0].grades);
      for (let i = 0; i < class_.students.length; i++) {
        for (let j = 0; j < class_.subjects.length; j++) {
          console.log(class_.students[i]);
          console.log(class_.subjects[j].name);
          console.log(class_.classname)

          this.ApiDataService.getStudentGrade(localStorage.getItem('token'),class_.classname, class_.students[i].nmec, class_.subjects[j].name).then((grade : any) => {
            console.log(grade);
            grade = {
              student: class_.students[i],
              subject: class_.subjects[j].name,
              grade: grade
            }
            this.grades.push(grade);
            }
          );
        }
      }
    });
  }

  getGradeForSubject(classname: string, nmec: number, subject: string) {
    this.ApiDataService.getStudentGrade(localStorage.getItem('token'),classname, nmec, subject).then((grade : any) => {
      console.log(grade);
      grade = {
        studentnmec: nmec,
        subject: subject,
        grade: grade
      }
      this.grades.push(grade);
      }
    );
  }

  getGradeForSubject_ok(nmec: number, subject: string): string {
    const foundGrade = this.grades.find((grade: any) => grade.studentnmec === nmec && grade.subject === subject);
    return foundGrade ? foundGrade.grade : 'Grade not available';
  }


  showStudents() {
    this.selectedContent = 'students'; // Set selected content to students
  }

  showSubjectsAndTeachers() {
    this.selectedContent = 'subjectsAndTeachers';
  }
}
