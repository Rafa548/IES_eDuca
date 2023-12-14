import { Component, inject } from '@angular/core';
import {FormsModule} from "@angular/forms";
import {NgForOf, NgIf} from "@angular/common";
import {Router} from "@angular/router";
import { ApiDataService } from '../api-data.service';

interface Option {
  value: string;
  label: string;
}
@Component({
  selector: 'app-student-admin-info',
  standalone: true,
  imports: [FormsModule,
    NgForOf,
    NgIf],
  templateUrl: './student-admin-info.component.html',
  styleUrl: './student-admin-info.component.css'
})
export class StudentAdminInfoComponent {
  selectedContent: string = 'Profile';
  StudentNmec: number;
  studentGrades: any[] = [];
  ApiDataService = inject(ApiDataService);
  student:any;
  selectedUser: any | undefined ;
  gradeInput: string = '';
  alive: boolean = true;

  constructor(private router: Router) {
    this.StudentNmec = Number(this.router.url.split('/').pop());
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
    this.studentGrades = [];
    this.ApiDataService.getStudent(localStorage.getItem('token'), this.StudentNmec).then((student : any) => {
      this.student = student;
      console.log(student);
      for (let i = 0; i < this.student.grades.length; i++) {
        if (!this.studentGrades.includes(this.student.grades[i]))
            this.studentGrades.push(this.student.grades[i]);
      }
      console.log(this.studentGrades);

      const name = document.getElementById('name') as HTMLInputElement;
      const email = document.getElementById('email') as HTMLInputElement;
      const password = document.getElementById('password') as HTMLInputElement;
      
      if (name) {
        name.value = this.student.name;
      }
      if (email) {
        email.value = this.student.email;
      }
      if (password) {
        password.value = this.student.password;
      }

    });
  }

  showProfile() {
    this.selectedContent = 'Profile'; // Set selected content to students
  }

  showGrades() {
    this.selectedContent = 'Grades';
  }

  submitStudentProfile() {
    this.student = {}
    const name = document.getElementById('name') as HTMLInputElement;
    const email = document.getElementById('email') as HTMLInputElement;
    const password = document.getElementById('password') as HTMLInputElement;

    if (name && email && password) {
      this.student.name = name.value;
      this.student.email = email.value;
      this.student.password = password.value;
      this.student.nmec = this.StudentNmec;
    }
    this.ApiDataService.updateStudent(localStorage.getItem('token'), this.student).then((student : any) => {
      this.student = student;
      console.log(student);
    });
  }

  EditGrade(row: any) {
    console.log(row);
    this.selectedUser = row;
    const modal = document.getElementById('editModal');
    const grade = document.getElementById('grade') as HTMLInputElement;
    const modalSelectElement = document.getElementById('modalSelect') as HTMLSelectElement;
    if (modal) {
      modal.style.display = 'block';
    }
    if (grade) {
      grade.value = this.selectedUser.grade;
    }
    if (modalSelectElement) {
      modalSelectElement.value = this.selectedUser.subjectname;
    }
  }

  deleteGrade(row: any) {
    console.log(row);
    this.selectedUser = row;
    this.ApiDataService.deleteGrade(localStorage.getItem('token'), this.selectedUser).then((response : any) => {
      console.log(response);
    });
  }

  saveChanges(row: any, gradeInput: string) { 
    console.log('Saving changes for grade:', row);
    console.log('New grade:', gradeInput);
    this.selectedUser = row;
    this.selectedUser.grade = gradeInput;
    this.ApiDataService.updateGrade(localStorage.getItem('token'), this.selectedUser).then((response : any) => {
      console.log(response);
    });
    const modal = document.getElementById('editModal');
    if (modal) {
      modal.style.display = 'none';
    }
    
  }

  closeEditModal() {
    const modal = document.getElementById('editModal');
    if (modal) {
      modal.style.display = 'none';
    }
  }

}
