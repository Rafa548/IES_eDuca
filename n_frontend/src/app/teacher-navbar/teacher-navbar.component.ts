import { NgIf } from '@angular/common';
import { Component, ElementRef, Input, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { WebSocketService } from '../websocket.service';
import { CommonModule, NgFor} from '@angular/common';

@Component({
  selector: 'app-teacher-navbar',
  standalone: true,
  imports: [NgIf, NgFor, CommonModule],
  templateUrl: './teacher-navbar.component.html',
  styleUrl: './teacher-navbar.component.css'
})
export class TeacherNavbarComponent {
  isLoggedIn: boolean = localStorage.getItem('token') !== null;

  constructor(private router: Router) {
    
  }

  login() {
    // Implement your login logic here
    // For example, navigate to the login page
    this.router.navigate(['/']);
  }

  logout() {
    // Implement your logout logic here
    // For example, clear the token and navigate to the home page
    localStorage.removeItem('token');
    this.isLoggedIn = false;
    this.router.navigate(['/']);
  }

  redirectTo(path: string): void {
    switch (path) {
      case 'profile':
        this.router.navigate(['teacher/profile']); // Change the route path as needed
        break;
      case 'home':
        this.router.navigate(['/teacher_home']); // Change the route path as needed
        break;
      default:
        break;
    }
  }

}
