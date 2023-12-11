import {Component, inject} from '@angular/core';
import {FormsModule} from "@angular/forms";
import {NgForOf, NgIf} from "@angular/common";
import {Router} from "@angular/router";
import { ApiDataService } from '../api-data.service';


interface Option {
  value: string;
  label: string;
}
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
  studentName: string = '';
  studentClass: string = '';
  all_teaching_assigmments: any = {};

  grades: number[] = [];
  gradeInputs = Array(5).fill(0); // Change '5' to the number of grade inputs you need
  subjects: string[] = ["math", "portuguese", "english", "history", "geography", "biology", "physics", "chemistry"] // Change this to the list of subjects you need
  selectedClass: string = '';

  constructor(private router: Router) {
    this.TeacherNmec = Number(this.router.url.split('/').pop());
    //console.log(this.TeacherNmec);
    this.ApiDataService.getTeacher(localStorage.getItem('token'), this.TeacherNmec).then((teacher : any) => {
      this.teacher = teacher;
      console.log("teacher",this.teacher);
      for (let i = 0; i < this.teacher.subjects.length; i++) {
        if (!this.teacherSubjects.includes(this.teacher.subjects[i].name))
            this.teacherSubjects.push(this.teacher.subjects[i].name);
      }
      for (let i = 0; i < this.teacher.classes.length; i++) {
        if (!this.selectedClasses.includes(this.teacher.classes[i].classname))
          if ( i == 0)
            this.selectedClass = this.teacher.classes[i].classname;
          this.selectedClasses.push(this.teacher.classes[i].classname);
      }
      this.ApiDataService.getTeachingAssigmments(localStorage.getItem('token')).then((teachingAssigmments : any[]) => {
        this.selectedSubjects = [];
        for (let i = 0; i < teachingAssigmments.length; i++) {
          if (teachingAssigmments[i].teacher.nmec == this.TeacherNmec) {
            if (teachingAssigmments[i].sclass.classname == this.selectedClass)
              this.selectedSubjects.push(teachingAssigmments[i].subject.name);
            if (this.all_teaching_assigmments[teachingAssigmments[i].sclass.classname] == undefined)
              this.all_teaching_assigmments[teachingAssigmments[i].sclass.classname] = [teachingAssigmments[i].subject.name];
            else
              this.all_teaching_assigmments[teachingAssigmments[i].sclass.classname].push(teachingAssigmments[i].subject.name);
          }


        }
        console.log(this.all_teaching_assigmments);
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
      console.log(response);
    });




    console.log(this.selectedSubjects);
  }

  toggleDropdown() {
    this.showDropdown = !this.showDropdown;
  }

  toggleOption(option: string) {
    const index = this.selectedSubjects.findIndex(opt => opt === option);
    if (index === -1) {
      this.selectedSubjects.push(option);
    } else {
      this.selectedSubjects.splice(index, 1);
    }
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
    this.selectedContent = 'Profile'; // Set selected content to students
  }

  showGrades() {
    this.selectedContent = 'Grades';
  }

  updateOptions(selectedValue: string) {
    this.selectedClass = selectedValue;
    //console.log(this.selectedClass)
    this.ApiDataService.getTeachingAssigmments(localStorage.getItem('token')).then((teachingAssigmments : any[]) => {
      this.selectedSubjects = [];
      for (let i = 0; i < teachingAssigmments.length; i++) {
        if (teachingAssigmments[i].sclass.classname == this.selectedClass && teachingAssigmments[i].teacher.nmec == this.TeacherNmec) {
          //console.log(teachingAssigmments[i]);
          this.selectedSubjects.push(teachingAssigmments[i].subject.name);
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
}
