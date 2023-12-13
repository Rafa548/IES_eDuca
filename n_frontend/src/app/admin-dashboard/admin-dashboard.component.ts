import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { WebSocketService } from '../websocket.service';
import { NavbarComponent } from '../navbar/navbar.component';
import { CommonModule, NgFor} from '@angular/common';


@Component({
  selector: 'app-admin-dashboard',
  standalone: true,
  imports: [ NavbarComponent , NgFor, CommonModule],
  templateUrl: './admin-dashboard.component.html',
  styleUrl: './admin-dashboard.component.css'
})
export class AdminDashboardComponent {
  webSocketService: WebSocketService;
  notifications: any[] = [];
  private subscription: any;


  constructor(private router: Router) {
    this.webSocketService = new WebSocketService();
  }

  ngOnInit(): void {
    console.log('Connecting to WebSocket...');
    this.webSocketService.connect(this.onMessage.bind(this));

    // Subscribe to the class (replace 'ClassName' with the actual class name)
    //this.subscription = this.webSocketService.subscribeToClass('ClassName');
  }

  ngOnDestroy(): void {
    // Check if subscription is not null before unsubscribing
    
    console.log('Disconnecting from WebSocket...');
    this.webSocketService.disconnect();
  }

  private onMessage(notification: any): void {
    // Handle WebSocket messages here
    console.log('Received WebSocket message:', notification);
    this.notifications = [...this.notifications, notification];
  }

  redirectTo(path: string): void {
    switch (path) {
      case 'classes':
        this.router.navigate(['/admin/class_students']); // Change the route path as needed
        break;
      case 'students':
        this.router.navigate(['/admin/students']); // Change the route path as needed
        break;
      case 'teachers':
        this.router.navigate(['/admin/teachers']); // Change the route path as needed
        break;
      default:
        break;
    }
  }

}
