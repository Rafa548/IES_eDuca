import { Injectable } from '@angular/core';
import { Client, Message } from '@stomp/stompjs';

@Injectable({
  providedIn: 'root',
})
export class WebSocketService {
  private stompClient!: Client;
  private getTokenCallback: (() => Promise<string>) | null = null;

  constructor() {
    const token = localStorage.getItem('token');
    this.initializeStompClient();
  }

  private async initializeStompClient() {
    const token = localStorage.getItem('token');
    
    this.stompClient = new Client({
      brokerURL: 'ws://localhost:8080/ws',
      connectHeaders: {
        Authorization: `Bearer ${token}`,
      },
      debug: (str) => {
        console.log(str);
        console.log(token);
      },
      reconnectDelay: 5000,
      heartbeatIncoming: 4000,
      heartbeatOutgoing: 4000,
    });
  }

  

  public async connect(onMessageCallback: (message: any) => void): Promise<void> {
    await this.initializeStompClient();

    const intervalId = setInterval(() => {
      const email = localStorage.getItem('user');
      if (email) {
        clearInterval(intervalId); // Stop the interval
        console.log('WebSocket connected');
        const subscription = this.stompClient.subscribe(`/user/` + email + `/queue/notifications`, (message: Message) => {
          onMessageCallback(JSON.parse(message.body));
        });
      }
    }, 1000);
    

    this.stompClient.onDisconnect = () => {
      console.log('WebSocket disconnected');
    };

    this.stompClient.onStompError = (frame) => {
      console.error('WebSocket error:', frame.headers['message']);
    };

    this.stompClient.activate();
    console.log('Connecting to WebSocket...');
  }

  public disconnect(): void {
    this.stompClient.deactivate();
  }

  public subscribeToClass(className: string): any {
    if (this.stompClient.connected) {
      const subscription = this.stompClient.subscribe(`/user/admin/queue/notifications`, (message: Message) => {
        // You can handle the received message here
        console.log('Received message:', JSON.parse(message.body));
      });

      return subscription;
    } else {
      console.error('WebSocket not connected');
      return null;
    }
  }
}
