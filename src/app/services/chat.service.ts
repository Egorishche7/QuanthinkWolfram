import {Injectable} from '@angular/core';
import {Observable, Subject} from 'rxjs';
import {Stomp} from '@stomp/stompjs';
import SockJS from 'sockjs-client';
import {Message} from '../interfaces/message';

@Injectable({
  providedIn: 'root'
})
export class ChatService {
  private stompClient: any | undefined;
  private messageSubject = new Subject<Message>();

  private readonly serverUrl = 'http://localhost:8080/ws';
  private readonly publicTopic = '/chatroom/public';
  private readonly userHistoryTopic = '/user/history';
  private readonly userPrivateTopic = '/user/private';

  connect(email: string) {
    const socket = new SockJS(this.serverUrl);
    this.stompClient = Stomp.over(socket);

    this.stompClient.connect({}, () => {
      this.stompClient.subscribe(this.publicTopic, (message: { body: string; }) => {
        this.onMessageReceived(message);
      });
      this.stompClient.subscribe(`${this.userHistoryTopic}`, (messages: { body: string; }) => {
        const parsedMessages: Message[] = JSON.parse(messages.body);
        parsedMessages.forEach(msg => this.messageSubject.next(msg));
      });
      this.stompClient.subscribe(`${this.userPrivateTopic}`, (message: { body: string; }) => {
        this.onMessageReceived(message);
      });

      this.sendJoinMessage(email);
    });
  }

  disconnect(email: string | undefined) {
    if (this.stompClient) {
      this.sendLeaveMessage(email);
      this.stompClient.disconnect();
    }
  }

  sendPublicMessage(message: Message) {
    this.stompClient.send('/app/publicMessage', {}, JSON.stringify(message));
  }

  sendPrivateMessage(message: Message) {
    this.stompClient.send('/app/privateMessage', {}, JSON.stringify(message));
  }

  sendJoinMessage(email: string) {
    this.stompClient.send('/app/join', {}, email);
  }

  sendLeaveMessage(email: string | undefined) {
    this.stompClient.send('/app/leave', {}, email);
  }

  onMessageReceived(message: { body: string }) {
    const parsedMessage: Message = JSON.parse(message.body);
    this.messageSubject.next(parsedMessage);
  }

  getMessages(): Observable<Message> {
    return this.messageSubject.asObservable();
  }
}
