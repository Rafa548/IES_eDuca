import { NgIf } from '@angular/common';
import { Component, ElementRef, Input, ViewChild, inject } from '@angular/core';
import { Router } from '@angular/router';
import { WebSocketService } from '../websocket.service';
import { CommonModule, NgFor} from '@angular/common';
import { ApiDataService } from '../api-data.service';

@Component({
  selector: 'app-student-navbar',
  standalone: true,
  imports: [NgIf, NgFor, CommonModule],
  templateUrl: './student-navbar.component.html',
  styleUrl: './student-navbar.component.css'
})
export class StudentNavbarComponent {
  webSocketService: WebSocketService;
  apiDataService = inject(ApiDataService);
  isLoggedIn: boolean = localStorage.getItem('token') !== null;
  showDropdown: boolean = false;
  showNotifications = false;
  notifications: any[] = [];
  @ViewChild('dropdownContent') dropdownContent!: ElementRef;
  @Input() childData: string = '';
  notification: boolean = false;

  constructor(private router: Router) {
    this.webSocketService = new WebSocketService();
    
  }

  toggleNotifications(event: Event) {
    event.stopPropagation(); // Prevent default event behavior to avoid toggling dropdown and closing it immediately
    this.showNotifications = !this.showNotifications;
    
  }

  ngOnInit(): void {
    console.log('Connecting to WebSocket...');
    this.webSocketService.connect(this.onMessage.bind(this));

    this.apiDataService.getNotifications(localStorage.getItem('token'), localStorage.getItem('user')).then((notifications: any) => {
      this.notifications = notifications;
      console.log(notifications);
    });

    // Subscribe to the class (replace 'ClassName' with the actual class name)
    //this.subscription = this.webSocketService.subscribeToClass('ClassName');
  }

  ngOnDestroy(): void {
    // Check if subscription is not null before unsubscribing
    
    console.log('Disconnecting from WebSocket...');
    this.webSocketService.disconnect();
  }

  falseNotification(){
    this.notification = false;
  }

  private onMessage(notification: any): void {
    // Handle WebSocket messages here
    console.log('Received WebSocket message:', notification);
    this.notifications = [...this.notifications, notification];
    setTimeout(() => this.scrollToBottom(), 0);
    this.notification = true;
  }

  private scrollToBottom() {
    const dropdownContentElement = this.dropdownContent.nativeElement;
    dropdownContentElement.scrollTop = dropdownContentElement.scrollHeight;
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

  toggleDropdown() {
    this.showDropdown = !this.showDropdown;
  }
}
