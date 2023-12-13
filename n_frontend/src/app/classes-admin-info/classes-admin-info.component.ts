import {Component, inject, OnDestroy, OnInit} from '@angular/core';
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
export class ClassesAdminInfoComponent implements OnInit, OnDestroy{
  selectedContent: string = 'students'; // Variable to track selected content
  class: any | undefined ;
  ApiDataService = inject(ApiDataService);
  classId: number;
  grades: any[] = [];
  alive: boolean = true;


  constructor(private router: Router) {
    this.classId = Number(this.router.url.split('/').pop());
    
  }

  ngOnInit() {
    this.updateContentPeriodically();
  }

  ngOnDestroy() {
    this.alive = false; // Set alive flag to stop the interval on component destruction
  }

  updateContentPeriodically() {
    setInterval(() => {
      if (this.alive) {
        this.fetchData();
      }
    }, 5000); // Update content every 5 seconds (5000 milliseconds)
  }

  fetchData() {
    this.ApiDataService.getClass(localStorage.getItem('token'), this.classId).then((class_ : any) => {
      this.class = class_;
      this.grades = [];
      //console.log(class_.students[0].grades);
      for (let i = 0; i < class_.students.length; i++) {
        for (let j = 0; j < class_.subjects.length; j++) {
          this.ApiDataService.getStudentGrade(localStorage.getItem('token'),class_.classname, class_.students[i].nmec, class_.subjects[j].name).then((grade : any) => {
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
    });}

  getGrade(student: any, subject: any): any {
    return this.grades.find(
      (grade: any) =>
        grade.student.name === student.name && grade.subject === subject.name
    );
  }



  showStudents() {
    this.selectedContent = 'students'; // Set selected content to students
  }

  showSubjectsAndTeachers() {
    this.selectedContent = 'subjectsAndTeachers';
  }

  deleteStudent(student: any) {
    this.ApiDataService.deleteStudentFromClass(localStorage.getItem('token'), this.class.classname, student.nmec).then((response : any) => {
      this.class.students = this.class.students.filter((s: any) => s.nmec !== student.nmec);
    });
  }

  studentDetails(student: any) {

  }
}
