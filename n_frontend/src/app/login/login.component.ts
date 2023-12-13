import { Component, inject } from '@angular/core';
import {FormsModule} from "@angular/forms";
import {Router} from '@angular/router';
import {GenTokenService} from "../gen-token.service"
import {NgIf} from "@angular/common";
import { JwtHelperService } from '@auth0/angular-jwt';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    FormsModule,
    NgIf
  ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  email: string = '';
  password: string = '';
  userType: string = 'STUDENT';
  error : string = ''
  GenTokenService  = inject(GenTokenService);

  constructor(private router : Router) {
  }

  handleUserTypeToggle() {
    const userTypesOrder = ['STUDENT', 'TEACHER', 'ADMIN'];
    const currentIndex = userTypesOrder.indexOf(this.userType);
    const nextIndex = (currentIndex + 1) % userTypesOrder.length;
    this.userType = userTypesOrder[nextIndex];
  }


  async login() {
    try {
      localStorage.clear();
      //console.log('Logging in user:', this.email);
      //console.log("password: ", this.password);
      this.GenTokenService.getToken(this.email, this.password).then(token => {
        const helper = new JwtHelperService();
        const decodedToken = helper.decodeToken(token);
        const u_token:string =token;
        localStorage.setItem('token', u_token);
        localStorage.setItem('user', this.email);
        //console.log(localStorage.getItem('token'));
        //console.log('Login successful');
        const userRole: string = decodedToken.role;
        console.log('User role:', userRole);
        if (this.userType == userRole){
          if (this.userType == 'STUDENT'){
            this.router.navigate(['student_home']);
          }
          else if (this.userType == 'ADMIN'){
            this.router.navigate(['admin_dashboard']);
          }
          else
            this.router.navigate(['teacher_home']);
        }

      });
      //this.router.navigate(['admin_dashboard']);

    } catch (error) {
      console.error(error);
    }
  }


}
