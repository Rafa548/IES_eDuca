import {Component, inject,OnInit, OnDestroy,} from '@angular/core';
import {StudentService} from "../student.service";
import {JwtHelperService} from "@auth0/angular-jwt";
import { CommonModule, NgFor} from '@angular/common';
import { WebSocketService } from '../websocket.service';


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

  constructor() {
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
