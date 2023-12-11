import {Component, inject} from '@angular/core';
import {Router} from "@angular/router";
import {FormsModule} from "@angular/forms";
import {NgForOf, NgIf} from "@angular/common";
import { ApiDataService } from '../api-data.service';



@Component({
  selector: 'app-students-admin-info',
  standalone: true,
  imports: [
    FormsModule,
    NgForOf,
    NgIf
  ],
  templateUrl: './students-admin-info.component.html',
  styleUrl: './students-admin-info.component.css'
})
export class StudentsAdminInfoComponent {
  tableHeaders: string[];
  tableData: any[] = [];
  selectedUser: any | undefined ;
  classes: any[] = [];
  ApiDataService = inject(ApiDataService)
  selectedClass: any | undefined ;



    constructor(private router: Router) {
      this.ApiDataService.getClasses(localStorage.getItem('token')).then((classes : any[]) => {
        this.classes = classes;
      });
      this.tableHeaders = ['Nmec', 'Name', 'E-mail'];
      this.ApiDataService.getStudents(localStorage.getItem('token')).then((students : any[]) => {
        this.tableData = students;
      });

    }

    navigateToStudentDetails(nmec: number) {
      this.router.navigate(['admin/student', nmec]); // Navigate to a route like '/student/1' based on the student ID
    }

    deleteStudent(student: any) {
      this.ApiDataService.deleteStudent(localStorage.getItem('token'), student.nmec).then((response : any) => {
        //console.log(response);
        this.ApiDataService.getStudents(localStorage.getItem('token')).then((students : any[]) => {
          this.tableData = students;
        });
      } );
    }

    studentEdit(user: any) {
      this.selectedUser = user;
      const modal = document.getElementById('editModal');
      const name = document.getElementById('name') as HTMLInputElement;
      const email = document.getElementById('email') as HTMLInputElement;
      const password = document.getElementById('password') as HTMLInputElement;
      const modalSelectElement = document.getElementById('modalSelect') as HTMLSelectElement;
      if (modal) {
        modal.style.display = 'block';
      }
      if (name) {
        name.value = this.selectedUser.name;
      }
      if (modalSelectElement) {
        // Change the selected option
        //console.log(this.selectedUser.studentclass.classname);
        modalSelectElement.value = this.selectedUser.studentclass.classname;
      }
      if (email) {
        email.value = this.selectedUser.email;
      }
      if (password) {
        password.value = this.selectedUser.password;
      }
      //console.log(this.selectedUser);
    }

  closeEditModal() {
    const modal = document.getElementById('editModal');
    if (modal) {
      modal.style.display = 'none';
    }
  }

  saveChanges() {
    this.selectedUser = {};
    const name = document.getElementById('name') as HTMLInputElement;
    const email = document.getElementById('email') as HTMLInputElement;
    const password = document.getElementById('password') as HTMLInputElement;
    const modalSelectElement = document.getElementById('modalSelect') as HTMLSelectElement;
    if (name) {
      this.selectedUser.name = name.value;
    }
    if (modalSelectElement) {
      this.selectedUser.studentclass = modalSelectElement.value;
    }
    if (email) {
      this.selectedUser.email = email.value;
    }
    for (let i = 0; i < this.tableData.length; i++) {
      if (this.tableData[i].email === this.selectedUser.email) {
        this.selectedUser.nmec = this.tableData[i].nmec;
      }
    }
    if (password) {
      this.selectedUser.password = password.value;
    }

    this.ApiDataService.updateStudent(localStorage.getItem('token'), this.selectedUser).then((response : any) => {
      //console.log(response);
      this.ApiDataService.getStudents(localStorage.getItem('token')).then((students : any[]) => {
        this.tableData = students;
      });
    });


    this.closeEditModal(); // Close the modal after saving changes
  }

  addStudent() {
    this.selectedUser = {};
    const name = document.getElementById('name1') as HTMLInputElement;
    const email = document.getElementById('email1') as HTMLInputElement;
    const password = document.getElementById('password1') as HTMLInputElement;
    const class_ = this.selectedClass;
    const nmec = this.tableData[this.tableData.length - 1].nmec + 1;

    if (name) {
      this.selectedUser.name = name.value;
    }
    if (email) {
      this.selectedUser.email = email.value;
    }
    if (password) {
      this.selectedUser.password = password.value;
    }
    if (class_) {
      this.selectedUser.studentclass = class_;
    }
    this.selectedUser.nmec = nmec;

    this.ApiDataService.addStudent(localStorage.getItem('token'), this.selectedUser).then((response : any) => {
      //console.log(response);
      this.ApiDataService.getStudents(localStorage.getItem('token')).then((students : any[]) => {
        this.tableData = students;
      });
    });

    console.log("Student added", this.selectedUser);


  }

  showAddStudentModal() {
    const modal = document.getElementById('addModal');
    if (modal) {
      modal.style.display = 'block';
    }
  }

  closeAddModal() {
    const modal = document.getElementById('addModal');
    if (modal) {
      modal.style.display = 'none';
    }
  }



}
