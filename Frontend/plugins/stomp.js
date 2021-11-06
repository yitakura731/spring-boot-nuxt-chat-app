import { Client } from '@stomp/stompjs';
import Cookies from 'js-cookie';
import * as SockJS from 'sockjs-client';

const stompPlugin = (ctx, inject) => {
  const client = new Client();
  client.brokerURL = `ws://${process.env.PUBLIC_DOMAIN}/api/endpoint`;
  client.webSocketFactory = () => {
    return new SockJS(`http://${process.env.PUBLIC_DOMAIN}/api/endpoint`);
  };
  client.beforeConnect = () => {
    client.connectHeaders = {
      'Auth-Token': Cookies.get('demoapp.idToken')
    };
  };
  inject('stomp', client);
};

export default stompPlugin;
