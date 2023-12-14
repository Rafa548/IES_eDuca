import {Component, inject} from '@angular/core';
import { Router } from '@angular/router';
import { GenTokenService } from '../gen-token.service';
import { ApiDataService } from '../api-data.service';
import {Class} from "../class";
import {NgForOf, NgIf} from "@angular/common";

@Component({
  selector: 'app-class-students-admin-info',
  standalone: true,
  imports: [
    NgForOf,
    NgIf
  ],
  templateUrl: './class-students-admin-info.component.html',
  styleUrl: './class-students-admin-info.component.css'
})


export class ClassStudentsAdminInfoComponent {
  classes: any[] = [];
  ApiDataService = inject(ApiDataService);


  constructor(private router: Router) {
    //console.log(localStorage.getItem('token'));
    this.ApiDataService.getClasses(localStorage.getItem('token')).then((classes : any[]) => {
      this.classes = classes;
      //console.log(classes);
    });
  }

  navigateToClassDetails(classId: number) {
    this.router.navigate(['admin/class', classId]); // Navigate to a route like '/class/1' based on the class ID
  }

  deleteClass(event: Event, classname: string) {
    event.stopPropagation();
    this.ApiDataService.deleteClass(localStorage.getItem('token'), classname).then((response : any) => {
      //console.log(response);
      this.ApiDataService.getClasses(localStorage.getItem('token')).then((classes : any[]) => {
        this.classes = classes;
        //console.log(classes);
      });
    });

  }
}
