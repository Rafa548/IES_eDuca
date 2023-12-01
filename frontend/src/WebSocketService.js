// WebSocketService.js

import { Client } from '@stomp/stompjs';

const WebSocketService = (onMessageCallback) => {
  const client = new Client({
    brokerURL: 'ws:http://localhost:8080/ws', // Your WebSocket endpoint
    debug: function (str) {
      console.log(str);
    },
    reconnectDelay: 5000,
    heartbeatIncoming: 4000,
    heartbeatOutgoing: 4000,
  });

  const connect = () => {
    client.activate();
  };

  const disconnect = () => {
    client.deactivate();
  };

  const subscribeToClass = (className) => {
    if (client.connected) {
      const subscription = client.subscribe(`/class/${className}`, (message) => {
        onMessageCallback(JSON.parse(message.body));
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
