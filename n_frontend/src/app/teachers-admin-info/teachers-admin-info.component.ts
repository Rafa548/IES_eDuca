import {Component, inject} from '@angular/core';
import {NgForOf, NgIf} from "@angular/common";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {Router} from "@angular/router";
import { ApiDataService } from '../api-data.service';
import { NavbarComponent } from '../navbar/navbar.component';

@Component({
  selector: 'app-teachers-admin-info',
  standalone: true,
    imports: [
        NgForOf,
        ReactiveFormsModule,
        NavbarComponent,
        NgIf,
        FormsModule
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
  selectedClass: any | undefined ;
  selectedTeacher: any | undefined ;
  subjects: any[] = [];
  selectedSubject: any | undefined ;
  options: string[] = ["math", "portuguese", "english", "history", "geography", "biology", "physics", "chemistry"]
  selectedSubjects: string[] = [];
  teacherSubjects: any[] = [];
  showDropdown3 = false;

  constructor(private router: Router) {
    this.ApiDataService.getClasses(localStorage.getItem('token')).then((classes : any[]) => {
      this.classes = classes;
    });
    this.ApiDataService.getSubjects(localStorage.getItem('token')).then((subjects : any[]) => {
      this.subjects = subjects;
    });
    this.tableHeaders = ['Nmec', 'Name', 'E-mail'];
    this.ApiDataService.getTeachers(localStorage.getItem('token')).then((teachers : any[]) => {
      this.tableData = teachers;
    });
  }

  toggleDropdown3() {
    this.showDropdown3 = !this.showDropdown3;
  }

  toggleOption3(option: string) {
    const index = this.teacherSubjects.findIndex(opt => opt === option);
    if (index === -1) {
      this.teacherSubjects.push(option);
    } else {
      this.teacherSubjects.splice(index, 1);
    }

  }

  isSelected3(option: string) {
    return this.teacherSubjects.some(opt => opt === option);
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
    const modal = document.getElementById('editModal');
    if (modal) {
      modal.style.display = 'none';
    }
    const name = document.getElementById('name') as HTMLInputElement;
    const email = document.getElementById('email') as HTMLInputElement;
    const password = document.getElementById('password') as HTMLInputElement;

    const json = {name: name.value, email: email.value, password: password.value, nmec: this.selectedUser.nmec};
    //console.log(json);

    this.ApiDataService.updateTeacher(localStorage.getItem('token'), json).then((response: any) => {
      //console.log(response);
      this.ApiDataService.getTeachers(localStorage.getItem('token')).then((teachers : any[]) => {
        this.tableData = teachers;
      });
    });
  }

  closeEditModal() {
    const modal = document.getElementById('editModal');
    if (modal) {
      modal.style.display = 'none';
    }
  }

  showAddTeacherModal() {
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

  addTeacher() {
    this.selectedTeacher = {};
    const name = document.getElementById('name1') as HTMLInputElement;
    const email = document.getElementById('email1') as HTMLInputElement;
    const password = document.getElementById('password1') as HTMLInputElement;
    const nmec = this.tableData[this.tableData.length - 1].nmec + 1;

    if (name) {
      this.selectedTeacher.name = name.value;
    }
    if (email) {
      this.selectedTeacher.email = email.value;
    }
    if (password) {
      this.selectedTeacher.password = password.value;
    }
    this.selectedTeacher.subjects = this.teacherSubjects;
    this.selectedTeacher.nmec = nmec;

    console.log("Teacher added", this.selectedTeacher);

    this.ApiDataService.addTeacher(localStorage.getItem('token'), this.selectedTeacher).then((response : any) => {
      //console.log(response);
      this.ApiDataService.getTeachers(localStorage.getItem('token')).then((teachers : any[]) => {
        this.tableData = teachers;
      });
    });
    
  }
}
