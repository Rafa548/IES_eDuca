import {Component, inject, signal} from '@angular/core';
import {FormsModule} from "@angular/forms";
import {NgForOf, NgIf} from "@angular/common";
import {Router} from "@angular/router";
import { ApiDataService } from '../api-data.service';


@Component({
  selector: 'app-teacher-admin-info',
  standalone: true,
  imports: [
    FormsModule,
    NgForOf,
    NgIf
  ],
  templateUrl: './teacher-admin-info.component.html',
  styleUrl: './teacher-admin-info.component.css'
})
export class TeacherAdminInfoComponent {
  selectedSubjects: string[] = [];
  teacherSubjects: string[] = [];
  initialSelectedSubjects: string[] = [];
  selectedClasses: string[] = [];
  TeacherNmec: number;
  teacher : any;
  ApiDataService = inject(ApiDataService);
  options: string[] = ["math", "portuguese", "english", "history", "geography", "biology", "physics", "chemistry"]
  options2: string[] = []
  showDropdown = false;
  showDropdown2 = false;
  showDropdown3 = false;
  selectedContent: string = 'Profile';
  grades: any[] = [];
  selectedClass: string = '';

  constructor(private router: Router) {
    this.TeacherNmec = Number(this.router.url.split('/').pop());
    //console.log(this.TeacherNmec);
    this.ApiDataService.getTeacher(localStorage.getItem('token'), this.TeacherNmec).then((teacher : any) => {
        this.teacher = teacher;
        //console.log("teacher",this.teacher);
        for (let i = 0; i < this.teacher.subjects.length; i++) {
          if (!this.teacherSubjects.includes(this.teacher.subjects[i].name))
              this.teacherSubjects.push(this.teacher.subjects[i].name);
        }
        for (let i = 0; i < this.teacher.classes.length; i++) {
            if ( i == 0) {}
              this.selectedClass = this.teacher.classes[i].classname;
            this.selectedClasses.push(this.teacher.classes[i].classname);
        }
        //console.log(this.teacherSubjects);
        //console.log(this.selectedClasses);
        this.ApiDataService.getTeachingAssigmments(localStorage.getItem('token')).then((teachingAssigmments : any[]) => {
          this.selectedSubjects = [];
          for (let i = 0; i < teachingAssigmments.length; i++) {
            if (teachingAssigmments[i].teacher.nmec == this.TeacherNmec) {
              if (teachingAssigmments[i].sclass.classname == this.selectedClass){
                this.initialSelectedSubjects.push(teachingAssigmments[i].subject.name);
                this.selectedSubjects.push(teachingAssigmments[i].subject.name);
              }


            }


          }
          //console.log(this.selectedSubjects);
        });
      //console.log(this.teacher);
      //console.log(this.selectedSubjects);
      //console.log(this.selectedClasses);
        const name = document.getElementById('name') as HTMLInputElement;
        const email = document.getElementById('email') as HTMLInputElement;
        const password = document.getElementById('password') as HTMLInputElement;

        if (name) {
          name.value = this.teacher.name;
        }
        if (email) {
          email.value = this.teacher.email;
        }
        if (password) {
          password.value = this.teacher.password;
        }
    });
    this.ApiDataService.getClasses(localStorage.getItem('token')).then((classes : any[]) => {
      this.options2 = [];
      for (let i = 0; i < classes.length; i++) {
        this.options2.push(classes[i].classname);
      }
      //console.log(this.options2);
    });

  }
  submitTeacherProfile() {
    this.teacher = {};
    const name = document.getElementById('name') as HTMLInputElement;
    const email = document.getElementById('email') as HTMLInputElement;
    const password = document.getElementById('password') as HTMLInputElement;

    if (name && email && password) {
      this.teacher.name = name.value;
      this.teacher.email = email.value;
      this.teacher.password = password.value;
      this.teacher.nmec = this.TeacherNmec;
      this.teacher.classes = this.selectedClasses;
    }
    //console.log(this.teacher);
    this.ApiDataService.updateTeacher(localStorage.getItem('token'), this.teacher).then((response : any) => {
      //console.log(response);
    });

  }

  toggleDropdown() {
    this.showDropdown = !this.showDropdown;
  }

  toggleOption(option: string) {
    //console.log(option);
    const index = this.selectedSubjects.findIndex(opt => opt === option);
    if (index === -1) {
      //add
      //console.log(this.selectedClass);
      //console.log(option)
      //console.log(this.teacher.email)
      this.selectedSubjects.push(option);
    } else {
      //delete
      //console.log(this.selectedClass);
      //console.log(option)
      //console.log(this.teacher.email)
      this.selectedSubjects.splice(index, 1);
    }
    //console.log(this.all_teaching_assigmments);
  }

  isSelected(option: string): boolean {
    return this.selectedSubjects.some(opt => opt === option);
  }

  toggleDropdown2() {
    this.showDropdown2 = !this.showDropdown2;
  }

  toggleOption2(option: string) {
    const index = this.selectedClasses.findIndex(opt => opt === option);
    if (index === -1) {
      this.selectedClasses.push(option);
    } else {
      this.selectedClasses.splice(index, 1);
    }

  }

  isSelected2(option: string): boolean {
    return this.selectedClasses.some(opt => opt === option);
  }

  showProfile() {
    this.selectedContent = 'Profile'; // Set selected content to profile

    setTimeout(() => {
      const name = document.getElementById('name') as HTMLInputElement;
      const email = document.getElementById('email') as HTMLInputElement;
      const password = document.getElementById('password') as HTMLInputElement;

      if (name) {
        name.value = this.teacher.name;
      }
      if (email) {
        email.value = this.teacher.email;
      }
      if (password) {
        password.value = this.teacher.password;
      }
    }, 100); // Wait for 3 seconds before executing the code inside setTimeout
  }

  showGrades() {
    this.selectedContent = 'Grades';
    //console.log(this.TeacherNmec)
    this.ApiDataService.getTeacherGrades(localStorage.getItem('token'), this.TeacherNmec).then((grades : any[]) => {
      this.grades = [];
      console.log(grades)
      for (let i = 0; i < grades.length; i++) {
        //console.log(grades[i])
        if (grades[i].student.studentclass != null)
          this.grades.push(grades[i]);
      }
      console.log(this.grades);
    } );
  }

  updateOptions(selectedValue: string) {
    this.selectedClass = selectedValue;
    //console.log(this.selectedClass)
    this.ApiDataService.getTeachingAssigmments(localStorage.getItem('token')).then((teachingAssigmments : any[]) => {
      this.initialSelectedSubjects = [];
      this.selectedSubjects = [];
      for (let i = 0; i < teachingAssigmments.length; i++) {
        if (teachingAssigmments[i].sclass.classname == this.selectedClass && teachingAssigmments[i].teacher.nmec == this.TeacherNmec) {
          //console.log(teachingAssigmments[i]);
          this.selectedSubjects.push(teachingAssigmments[i].subject.name);
          this.initialSelectedSubjects.push(teachingAssigmments[i].subject.name);
        }
      }
      //console.log(this.selectedSubjects);
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

  UpdateTeachingAss() {
    //console.log(this.selectedSubjects);
    //console.log(this.initialSelectedSubjects);
    for (let i = 0; i < this.selectedSubjects.length; i++) {
      if (!this.initialSelectedSubjects.includes(this.selectedSubjects[i])) {
        //console.log("add");
        const json = {class: this.selectedClass, subject: this.selectedSubjects[i],email:this.teacher.email};
        //console.log(json);
        this.ApiDataService.addTeachingAssigment(localStorage.getItem('token'), json).then((response : any) => {
          //console.log(response);
        });
      }
    }

    for (let i = 0; i < this.initialSelectedSubjects.length; i++) {
      if (!this.selectedSubjects.includes(this.initialSelectedSubjects[i])) {
        //console.log("delete");
        const json = {class: this.selectedClass, subject: this.initialSelectedSubjects[i],email:this.teacher.email};
        //console.log(json)
        this.ApiDataService.deleteTeachingAssigment(localStorage.getItem('token'), json).then((response : any) => {
          //console.log(response);
        });
      }
    }
    if (this.showDropdown = true) {
      this.showDropdown = false;
    }

  }
}
