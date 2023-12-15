import {Component, inject, OnDestroy, OnInit} from '@angular/core';
import {NgForOf, NgIf} from "@angular/common";
import { ApiDataService } from '../api-data.service';
import {Class} from "../class";
import {Router} from "@angular/router";
import {FormsModule} from "@angular/forms";
import {TeacherService} from "../teacher.service";

@Component({
  selector: 'app-classes-admin-info',
  standalone: true,
  imports: [
    NgIf,
    NgForOf,
    FormsModule
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
  selectedTeacher: any | undefined ;


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
      this.selectedTeacher = teacher;
    });
  }

  closeEditModal() {
    const modal = document.getElementById('editModal');
    if (modal) {
      modal.style.display = 'none';
    }
  }

  saveChanges () {
    const modalSelectElement = document.getElementById('modalSelect') as HTMLSelectElement;
    if (modalSelectElement) {
      const new_teacher = modalSelectElement.value;
      const subject = this.selectedSubject;
      this.TeacherService.getByName(this.oldTeacher).then((teacher1: any) => {
        console.log("old_teacher", teacher1.name)
        const json = {subject: String(subject), class: this.class.classname, email: teacher1.email};
        console.log("json", json);
        console.log("subject", teacher1);
        this.ApiDataService.deleteTeachingAssigment(localStorage.getItem('token'), json).then((response: any) => {
          this.TeacherService.getByName(String(new_teacher)).then((teacher2: any) => {
            const json1 = {subject: String(subject), class: this.class.classname, email: teacher2.email};
            console.log("new_teacher", teacher2);
            this.ApiDataService.addTeachingAssigment(localStorage.getItem('token'), json1).then((response: any) => {
              this.ApiDataService.getClassTeachers(localStorage.getItem('token'), this.class.classname).then((teachers: any) => {
                this.classteachers = teachers;
                this.closeEditModal();
              });
            });
          },);
        });
      });
    }
  }
}
