import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {webSocket, WebSocketSubject} from 'rxjs/webSocket';
import {Message} from '../interfaces/message';
@Injectable({
  providedIn: 'root'
})
export class ChatService {

  public readonly url = 'http://localhost:8080/ws';
  public readonly chat = '/app/chat';

  private socket$: WebSocketSubject<Message>;

  constructor(private http: HttpClient) {
    this.socket$ = webSocket<Message>('ws://localhost:8080/chat');
  }

  getMessages(): Observable<Message[]> {
    return this.http.get<Message[]>(this.url);
  }

  sendMessage(message: Message): void {
    this.socket$.next(message);
  }

  receiveMessages(): Observable<Message> {
    return this.socket$;
  }
}

// import { Injectable } from '@angular/core';
// import { Observable, Subject } from 'rxjs';
// import { Stomp } from '@stomp/stompjs';
// import SockJS from 'sockjs-client';
// import {Message} from '../interfaces/message';
//
//
// @Injectable({
//   providedIn: 'root'
// })
// export class ChatService {
//   private stompClient: any;
//   private messageSubject = new Subject<Message>();
//
//   connect(email: string | null) {
//     const socket = new SockJS('http://localhost:8080/ws');
//     this.stompClient = Stomp.over(socket);
//
//     this.stompClient.connect({}, () => {
//       this.stompClient.subscribe('/chatroom/public', (message: any) => {
//         this.onMessageReceived(message);
//       });
//       this.stompClient.subscribe(`/user/${email}/history`, (messages: { body: string; }) => {
//         const parsedMessages: Message[] = JSON.parse(messages.body);
//         parsedMessages.forEach(msg => this.messageSubject.next(msg));
//       });
//
//       this.sendJoinMessage(email);
//     });
//   }
//
//   disconnect(email: string | null) {
//     if (this.stompClient !== null) {
//       this.sendLeaveMessage(email);
//       this.stompClient.disconnect();
//     }
//   }
//
//   sendPublicMessage(message: { senderId: string | null; content: string }) {
//     this.stompClient.send('/app/publicMessage', {}, JSON.stringify(message));
//   }
//
//   sendPrivateMessage(message: { senderId: number; receiverId: number; content: string }) {
//     this.stompClient.send('/app/privateMessage', {}, JSON.stringify(message));
//   }
//
//   sendJoinMessage(email: string | null) {
//     this.stompClient.send('/app/join', {}, email);
//   }
//
//   sendLeaveMessage(email: string | null) {
//     this.stompClient.send('/app/leave', {}, email);
//   }
//
//   onMessageReceived(message: { body: string; }) {
//     const parsedMessage: Message = JSON.parse(message.body);
//     this.messageSubject.next(parsedMessage);
//   }
//
//   getMessages(): Observable<Message> {
//     return this.messageSubject.asObservable();
//   }
// }
