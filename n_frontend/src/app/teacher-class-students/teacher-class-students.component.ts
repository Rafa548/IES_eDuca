import {Component, inject, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { TeacherService } from '../teacher.service';
import { CommonModule } from '@angular/common';
import {JwtHelperService} from "@auth0/angular-jwt";
import {StudentService} from "../student.service";
import {FormsModule} from "@angular/forms";
import {GradesService} from "../grades.service";

@Component({
  selector: 'app-teacher-class-students',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './teacher-class-students.component.html',
  styleUrls: ['./teacher-class-students.component.css'],
})
export class TeacherClassStudentsComponent implements OnInit, OnDestroy {
  class_students: any = {};
  teachersubjects: any = {};
  teacherData: any = {};
  filteredStudents: any[] = [];
  selectedSubject: string | undefined;
  selectedStudent: any;
  add_subject: string | undefined;
  email_s :string | undefined;

  TeacherService = inject(TeacherService);
  StudentService = inject(StudentService);
  GradeService = inject(GradesService);
  private alive = true;
  private intervalId: any;
  constructor( private route: ActivatedRoute){this.fetchData()}

  ngOnInit() {
    this.updateContentPeriodically();
    this.fetchData();
  }

  ngOnDestroy() {
    this.alive = false;
    clearInterval(this.intervalId);
  }

  updateContentPeriodically() {
    this.intervalId = setInterval(() => {
      if (this.alive) {
        this.fetchData();
      }
    }, 5000);
  }

  getFilteredGrades(subject: string): any[] {
    if (Array.isArray(this.class_students)) {
      const result = this.class_students
        .flatMap((student: any) => {
          const matchingGrades = student.grades.filter((grade: any) => grade.subject.name === subject);

          return matchingGrades.map((matchingGrade: any) => {
            return {
              student: {
                nmec: student.nmec,
                name: student.name,
                email: student.email,
              },
              grade: matchingGrade,
              gradeid: matchingGrade.id,
            };
          });
        });

      //console.log("filtered grades result:", result);
      this.saveStudents(result);
      return result;
    } else {
      return [];
    }
  }

  saveStudents(filteredGrades: any[]): void {
    if (filteredGrades && filteredGrades.length > 0) {
      if (Array.isArray(this.class_students)) {
        filteredGrades.forEach((filteredGrade: any) => {
          const newStudent = {
            nmec: filteredGrade.student.nmec,
            name: filteredGrade.student.name,
            email: filteredGrade.student.email,
            grade: filteredGrade.grade.grade,
            gradeid: filteredGrade.grade.gradeid,
          };
          this.filteredStudents.push(newStudent);
        });
       // console.log('Updated class_students:', this.filteredStudents);
      } else {
        //console.error('class_students is not an array:', this.filteredStudents);
      }
    } else {
      //console.warn('No filtered grades to save.');
    }
  }


  selectSubject(subjectName: string) {
    this.selectedSubject = subjectName;
    this.fetchData();
  }

  fetchData() {
    const classname = this.route.snapshot.paramMap.get('classname');
    this.TeacherService.getStudentsFromClass(classname).then((students) => {
      this.class_students = students;
      //console.log('students: ', this.class_students);
    });
    const token = localStorage.getItem('token');
    if (token) {
      const helper = new JwtHelperService();
      const decodedToken = helper.decodeToken(token);
      const email = decodedToken.sub;
      //console.log("email: ", email);
      this.TeacherService.getTeacherByEmail(email).then(teacher => {
        this.teacherData = teacher;
        this.TeacherService.getTeacherSubjects(email,classname).then(subjects => {
          this.teachersubjects = subjects;
          //console.log("subjects: ", this.teachersubjects);
        });
      });
    }
  }

  studentEdit(student: any) {
    this.selectedStudent = student;
    const modal = document.getElementById('editModal');
    const name = document.getElementById('name') as HTMLInputElement;
    const email = document.getElementById('email') as HTMLInputElement;
    const grade = document.getElementById('grade') as HTMLInputElement;
    const gradeid = document.getElementById('gradeid') as HTMLInputElement;
    if (modal) {
      modal.style.display = 'block';
    }
    if (name) {
      name.value = this.selectedStudent.student.name;
    }
    if (email) {
      email.value = this.selectedStudent.student.email;
    }
    if (grade) {
      grade.value = this.selectedStudent.grade.grade;
    }
    if (gradeid) {
      gradeid.value = this.selectedStudent.gradeid;
    }
  }

  closeEditModal() {
    const modal = document.getElementById('editModal');
    if (modal) {
      modal.style.display = 'none';
    }
  }

  saveChanges() {
    this.selectedStudent = {};
    const name = document.getElementById('name') as HTMLInputElement;
    const email = document.getElementById('email') as HTMLInputElement;
    const password = document.getElementById('password') as HTMLInputElement;
    const grade = document.getElementById('grade') as HTMLInputElement;
    const modalSelectElement = document.getElementById('modalSelect') as HTMLSelectElement;
    const gradeid = document.getElementById('gradeid') as HTMLInputElement;
    const grade_n= Number(grade.value);
    const grade_id= Number(gradeid.value);
    if (grade) {
      this.selectedStudent.name = name.value;
      this.selectedStudent.email = email.value;
      this.selectedStudent.gradeID=
      this.GradeService.updateGrade(grade_id,grade_n).then(() => {
        this.fetchData();
        //console.log('Updated grade');
      })
    }
    this.closeEditModal();
  }

  closeAddModal() {
    const modal = document.getElementById('addModal');
    if (modal) {
      modal.style.display = 'none';
    }
  }

  addStudentGrade() {
    const nmec= document.getElementById('nmec') as HTMLInputElement;
    const grade = document.getElementById('new_grade') as HTMLInputElement;
    const token= localStorage.getItem('token');
    if (token) {
      const helper = new JwtHelperService();
      const decodedToken = helper.decodeToken(token);
      const email = decodedToken.sub;
    //console.log("new grade",grade);
    //console.log("nmec",nmec);
    //console.log("subject name",this.add_subject);
    const grade_n= Number(grade.value);
    const nmec_n= Number(nmec.value);
    this.StudentService.getStudentByNmec(nmec_n).then(student => {
      if (student) {
        this.email_s = student.email;
        this.GradeService.addGrade(grade_n,email,this.email_s,this.add_subject).then(() => {
          this.fetchData();
          //console.log('Added grade');
        });
      }
    });




    this.closeAddModal();
  }}

  openAddModal(subject :string) {
    const modal = document.getElementById('addModal');
    this.add_subject=subject;
    if (modal) {
      modal.style.display = 'block';
    }
  }

  deleteStudentGrade(student: any) {
    //console.log("student: ", student);
    //console.log("student gradeID: ", student.grade.id);
    if (student.grade.id) {
      this.GradeService.deleteGrade(student.grade.id).then(() => {
        this.fetchData();
        //console.log('Deleted grade');
      });
    }
    return null;
  }
}
