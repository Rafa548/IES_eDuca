import {Component, inject} from '@angular/core';
import {NgForOf} from "@angular/common";
import {ReactiveFormsModule} from "@angular/forms";
import {Router} from "@angular/router";
import { ApiDataService } from '../api-data.service';

@Component({
  selector: 'app-teachers-admin-info',
  standalone: true,
    imports: [
        NgForOf,
        ReactiveFormsModule
    ],
  templateUrl: './teachers-admin-info.component.html',
  styleUrl: './teachers-admin-info.component.css'
})
export class TeachersAdminInfoComponent {
  tableHeaders: string[];
  tableData: any[] = [];
  selectedUser: any | undefined ;
  classes: any[] = [];
  ApiDataService = inject(ApiDataService)

  constructor(private router: Router) {
    this.ApiDataService.getClasses(localStorage.getItem('token')).then((classes : any[]) => {
      this.classes = classes;
    });
    this.tableHeaders = ['Nmec', 'Name', 'E-mail'];
    this.ApiDataService.getTeachers(localStorage.getItem('token')).then((teachers : any[]) => {
      this.tableData = teachers;
    });
  }

  TeacherEdit(row: any) {
    this.selectedUser = row;
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
      modalSelectElement.value = this.selectedUser.classes[0].classname;
    }
    if (email) {
      email.value = this.selectedUser.email;
    }
    if (password) {
      password.value = this.selectedUser.password;
    }
  }

  deleteTeacher(row: any) {
    this.ApiDataService.deleteTeacher(localStorage.getItem('token'), row.nmec).then((response : any) => {
      //console.log(response);
      this.ApiDataService.getTeachers(localStorage.getItem('token')).then((teachers : any[]) => {
        this.tableData = teachers;
      });
    } );

  }

  navigateToTeacherDetails(nmec: any) {
    this.router.navigate(['admin/teacher', nmec]);
  }

  saveChanges() {

  }
  closeEditModal() {
    const modal = document.getElementById('editModal');
    if (modal) {
      modal.style.display = 'none';
    }
  }
}
