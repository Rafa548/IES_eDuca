// WebSocketService.js

import { Client } from '@stomp/stompjs';

const WebSocketService = (onMessageCallback) => {
  const client = new Client({
    brokerURL: 'ws://localhost:8080/ws', // Your WebSocket endpoint
    debug: function (str) {
      console.log(str);
    },
    reconnectDelay: 5000,
    heartbeatIncoming: 4000,
    heartbeatOutgoing: 4000,
  });

  client.onConnect = () => {
    console.log('WebSocket connected');
    const subscription = client.subscribe(`/user/queue/notifications`, (message) => {
      onMessageCallback(JSON.parse(message.body));
      //console.log('Received message:', JSON.parse(message.body));
    });
  };

  client.onDisconnect = () => {
    console.log('WebSocket disconnected');
  };

  client.onStompError = (frame) => {
    console.error('WebSocket error:', frame.headers.message);
  };

  const connect = () => {
    client.activate();
    console.log('client connecting...');
  };

  const disconnect = () => {
    client.deactivate();
  };

  const subscribeToClass = (className) => {
    if (client.connected) {
      //const subscription = client.subscribe(`/class/${className}`, (message) => {
      const subscription = client.subscribe(`/user/queue/notifications`, (message) => {
        onMessageCallback(JSON.parse(message.body));
        console.log('Received message:', JSON.parse(message.body));
      });

      return subscription;
    } else {
      console.error('WebSocket not connected');
      return null;
    }
  };

  return { connect, disconnect, subscribeToClass };
};

export default WebSocketService;
