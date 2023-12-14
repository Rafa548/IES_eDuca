import {Component, inject,OnInit, OnDestroy,} from '@angular/core';
import {StudentService} from "../student.service";
import {JwtHelperService} from "@auth0/angular-jwt";
import { CommonModule, NgFor} from '@angular/common';
import { WebSocketService } from '../websocket.service';
import { Router } from '@angular/router';


@Component({
  selector: 'app-student-home',
  standalone: true,
  imports: [CommonModule, NgFor],
  templateUrl: './student-home.component.html',
  styleUrl: './student-home.component.css'

})
export class StudentHomeComponent implements OnInit, OnDestroy {
  webSocketService: WebSocketService;
  notifications: any[] = [];
  studentData: any = {};
  StudentService = inject(StudentService);
  private alive = true;
  isLoggedIn: boolean = localStorage.getItem('token') !== null;
  showDropdown: boolean = false;

  constructor(private router: Router) {
    this.webSocketService = new WebSocketService();
    this.fetchData();
  }

  ngOnInit() {
    this.webSocketService.connect(this.onMessage.bind(this));
    this.updateContentPeriodically();
  }

  ngOnDestroy() {
    this.alive = false;
    this.webSocketService.disconnect();
  }

  private onMessage(notification: any): void {
    // Handle WebSocket messages here
    console.log('Received WebSocket message:', notification);
    this.notifications = [...this.notifications, notification];
  }

  login() {
    // Implement your login logic here
    // For example, navigate to the login page
    this.router.navigate(['/']);
  }

  logout() {
    localStorage.removeItem('token');
    this.isLoggedIn = false;
    this.router.navigate(['/']);
  }

  redirectTo(path: string): void {
    switch (path) {
      case 'grades':
        this.router.navigate(['student_grades']); // Change the route path as needed
        break;
      case 'profile':
        this.router.navigate(['student_profile']); // Change the route path as needed
        break;
      case 'home':
        this.router.navigate(['student_home']); // Change the route path as needed
        break;
      default:
        break;
    }
  }

  

  updateContentPeriodically() {
    setInterval(() => {
      if (this.alive) {
        this.fetchData();
      }
    }, 5000);
  }

  fetchData() {
    const token=localStorage.getItem('token');
    if (token) {
      const helper = new JwtHelperService();
      const decodedToken = helper.decodeToken(token);
      const email = decodedToken.sub;
      console.log("email: ", email);
      this.StudentService.getStudentByEmail(email).then(student => {
        this.studentData = student;
        console.log(student);
      });
    } else {
      console.error('Token not found in localStorage');
    }
  }

}
