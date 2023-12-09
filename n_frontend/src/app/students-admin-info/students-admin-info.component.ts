import {Component, inject} from '@angular/core';
import {InfoTableComponent} from "../info-table/info-table.component";
import {Router} from "@angular/router";
import {FormsModule} from "@angular/forms";
import {NgForOf} from "@angular/common";
import { ApiDataService } from '../api-data.service';



@Component({
  selector: 'app-students-admin-info',
  standalone: true,
  imports: [
    InfoTableComponent,
    FormsModule,
    NgForOf
  ],
  templateUrl: './students-admin-info.component.html',
  styleUrl: './students-admin-info.component.css'
})
export class StudentsAdminInfoComponent {
  tableHeaders: string[];
  tableData: any[] = [];
  selectedUser: any;
  classes: any[] = [];
  ApiDataService = inject(ApiDataService)



    constructor(private router: Router) {
      this.ApiDataService.getClasses(localStorage.getItem('token')).then((classes : any[]) => {
        this.classes = classes;
      });
      this.tableHeaders = ['Nmec', 'Name', 'E-mail'];
      this.ApiDataService.getStudents(localStorage.getItem('token')).then((students : any[]) => {
        this.tableData = students;
      });

    }

    navigateToStudentDetails(studentId: number) {
      this.router.navigate(['admin/student', studentId]); // Navigate to a route like '/student/1' based on the student ID
    }

    deleteStudent(studentId: number) {

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
        const newSelectedValue = '2a'; // Change this to the value of the desired new class
        modalSelectElement.value = newSelectedValue;
      }
      if (email) {
        email.value = this.selectedUser.email;
      }
      if (password) {
        password.value = this.selectedUser.password;
      }
    }

  closeEditModal() {
    const modal = document.getElementById('editModal');
    if (modal) {
      modal.style.display = 'none';
    }
  }

  saveChanges() {
    const name = document.getElementById('name') as HTMLInputElement;
    const email = document.getElementById('email') as HTMLInputElement;
    const password = document.getElementById('password') as HTMLInputElement;
    const modalSelectElement = document.getElementById('modalSelect') as HTMLSelectElement;
    if (name) {
      this.selectedUser.name = name.value;
    }
    if (modalSelectElement) {
      this.selectedUser.class = modalSelectElement.value;
    }
    if (email) {
      this.selectedUser.email = email.value;
    }
    if (password) {
      this.selectedUser.password = password.value;
    }
    console.log(this.selectedUser);


    this.closeEditModal(); // Close the modal after saving changes
  }



}
