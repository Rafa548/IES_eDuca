import {Component, inject} from '@angular/core';
import { Router } from '@angular/router';
import { GenTokenService } from '../gen-token.service';
import { ApiDataService } from '../api-data.service';
import {Class} from "../class";
import {NgForOf, NgIf} from "@angular/common";
import { NavbarComponent } from '../navbar/navbar.component';

import {FormsModule, ReactiveFormsModule} from "@angular/forms";

@Component({
  selector: 'app-class-students-admin-info',
  standalone: true,
  imports: [
    NgForOf,
    NgIf,
    NavbarComponent,
    ReactiveFormsModule,
    FormsModule
  ],
  templateUrl: './class-students-admin-info.component.html',
  styleUrl: './class-students-admin-info.component.css'
})


export class ClassStudentsAdminInfoComponent {
  classes: any[] = [];
  ApiDataService = inject(ApiDataService);
  classname: string = '';
  errorMessage: string = '';


  constructor(private router: Router) {
    this.fetchData();
  }

  fetchData(){
    this.ApiDataService.getClasses(localStorage.getItem('token')).then((classes : any[]) => {
      this.classes = classes;
    });
  }

  navigateToClassDetails(classId: number) {
    this.router.navigate(['admin/class', classId]); // Navigate to a route like '/class/1' based on the class ID
  }

  deleteClass(event: Event, classname: string) {
    event.stopPropagation();
    this.ApiDataService.deleteClass(localStorage.getItem('token'), classname).then((response : any) => {
      this.fetchData();
    });
  }

  openModal(){
    const modal = document.getElementById('createModal');
    if (modal) {
      modal.style.display = 'block';
    }
  }

  saveChanges(){
    if (!this.classname) {
      this.errorMessage = 'Class name cannot be empty.';
      return;
    }
    const existingClass = this.classes.find(c => c.classname === this.classname);
    if (existingClass) {
      this.errorMessage = 'Class name already exists. Please choose a different name.';
    } else {
      this.errorMessage = '';
      const json = {"classname": this.classname};
      this.ApiDataService.createClass(localStorage.getItem('token'), json).then((response: any) => {
        this.fetchData();
        this.closeCreateModal();
      });
    }
  }

  closeCreateModal(){
    const modal = document.getElementById('createModal');
    if (modal) {
      modal.style.display = 'none';
    }
  }
}

