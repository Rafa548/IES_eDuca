import {Component, inject, OnDestroy, OnInit} from '@angular/core';
import {NgForOf, NgIf} from "@angular/common";
import { ApiDataService } from '../api-data.service';
import {Class} from "../class";
import {Router} from "@angular/router";
import {FormsModule} from "@angular/forms";
import {TeacherService} from "../teacher.service";
import { NavbarComponent } from '../navbar/navbar.component';

@Component({
  selector: 'app-classes-admin-info',
  standalone: true,
  imports: [
    NgIf,
    NgForOf,
    FormsModule,
    NavbarComponent
  ],
  templateUrl: './classes-admin-info.component.html',
  styleUrl: './classes-admin-info.component.css'
})
export class ClassesAdminInfoComponent implements OnInit, OnDestroy{
  selectedContent: string = 'students'; // Variable to track selected content
  class: any | undefined ;
  ApiDataService = inject(ApiDataService);
  TeacherService = inject(TeacherService);
  classId: number;
  grades: any[] = [];
  alive: boolean = true;
  classteachers: any = {};
  teacherpersubject: any = {};
  selectedSubject: string | null = null;
  oldTeacher: string | null = null;


  constructor(private router: Router) {
    this.classId = Number(this.router.url.split('/').pop());
    this.fetchData();
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
    });
  }

  getGrade(student: any, subject: any): any {
    return this.grades.find(
      (grade: any) =>
        grade.student.name === student.name && grade.subject === subject.name
    );
  }

  showStudents() {
    this.selectedContent = 'students';
  }

  showSubjectsAndTeachers() {
    this.selectedContent = 'subjectsAndTeachers';
    this.ApiDataService.getClassTeachers(localStorage.getItem('token'), this.class.classname).then((teachers : any) => {
      this.classteachers = teachers;
    });
  }

  deleteStudent(student: any) {
    this.ApiDataService.deleteStudentFromClass(localStorage.getItem('token'), this.class.classname, student.nmec).then((response : any) => {
      this.class.students = this.class.students.filter((s: any) => s.nmec !== student.nmec);
    });
  }

  getTeacherName(subjectName: string): any {
    for (let i = 0; i < this.classteachers.length; i++) {
      const teacher = this.classteachers[i];
      const subject = teacher.subject;
      const subjectNameInTeacher = subject ? subject.name : null;
      if (subjectNameInTeacher === subjectName) {
        return teacher.teacher.name;
      }
    }
  }

  studentDetails(student: any) {
    this.router.navigate(['/admin/student/', student.nmec]);
  }

  openmodal(subject:string, teacher:string) {
    const modal = document.getElementById('editModal');
    const subjectElement = document.getElementById('modalSubject');
    this.oldTeacher = teacher;
    if (subjectElement) {
      subjectElement.innerHTML = subject;
    }
    if (modal) {
      modal.style.display = 'block';
    }
    console.log("subject: " + subject);
    this.selectedSubject = subject;
    this.ApiDataService.getTeachersBySubject(localStorage.getItem('token'), subject).then((teachers: any) => {
      console.log(teachers);
      this.teacherpersubject = teachers;
    });
  }

  closeEditModal() {
    const modal = document.getElementById('editModal');
    if (modal) {
      modal.style.display = 'none';
    }
  }

  saveChanges () {
    console.log("iofengjkrnmbhokrejm");
    console.log("teacher_old",this.oldTeacher);
    const modalSelectElement = document.getElementById('modalSelect') as HTMLSelectElement;
    console.log("vjegmfjkrmn");
    if (modalSelectElement) {
      const teacher = modalSelectElement.value;
      const subject = this.selectedSubject;
      console.log("classs",this.class.classname)
      console.log("subject",subject)
      this.TeacherService.getByName(this.oldTeacher).then((teacher : any) => {
        const email = teacher.email;
        console.log("email",email);
        const json = {subject: String(subject), class: this.class.classname, email: teacher};
        this.ApiDataService.deleteTeachingAssigment(localStorage.getItem('token'), json).then((response: any) => {
          console.log(response);
          this.ApiDataService.getClassTeachers(localStorage.getItem('token'), this.class.classname).then((teachers: any) => {
            this.classteachers = teachers;
          });
        });
      });
    }
  }
}