// import {Component, OnInit} from '@angular/core';
// import {ChatService} from '../../services/chat.service';
// import {Message} from '../../interfaces/message';
//
// @Component({
//   selector: 'app-online-chat',
//   templateUrl: './online-chat.component.html',
//   styleUrls: ['./online-chat.component.css']
// })
// export class OnlineChatComponent implements OnInit {
//   messages: Message[] = [];
//   newMessage: string = '';
//
//   constructor(private chatService: ChatService) {
//   }
//
//   loadMessages() {
//     this.chatService.getMessages().subscribe(
//       (messages: Message[]) => {
//         this.messages = messages;
//         console.log('Previous messages:', this.messages);
//       },
//       (error) => {
//         console.error('Failed to load messages:', error);
//       }
//     );
//   }
//
//   sendMessage(senderId: string, message: string): void {
//     // const senderId = localStorage.getItem("")
//     const newMessage: Message = {
//       senderId:
//       content: message,
//       timestamp: new Date().toISOString(),
//       sender: ''
//     };
//     this.chatService.sendMessage(newMessage);
//     console.log('Sent message:', newMessage);
//     this.newMessage = '';
//
//   }
//
//   ngOnInit() {
//     setTimeout(() => {
//       this.loadMessages();
//
//       this.subscribeToNewMessages();
//     }, 500); // Добавляем задержку в 500 миллисекунд
//   }
//
//   subscribeToNewMessages() {
//     this.chatService.receiveMessages().subscribe(
//       (message: Message) => {
//         this.messages.push(message); // Add new message received via WebSocket
//         console.log('Received message:', message);
//       },
//       (error) => {
//         console.error('Failed to receive message:', error);
//       }
//     );
//   }
// }

import { Component, OnInit, OnDestroy } from '@angular/core';
import {ChatService} from "../../services/chat.service";
import {Message} from '../../interfaces/message';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-online-chat',
  templateUrl: './online-chat.component.html',
  styleUrls: ['./online-chat.component.css']
})
export class OnlineChatComponent implements OnInit, OnDestroy {
  messages: Message[] = [];
  messageContent: string = '';
  private messagesSubscription: Subscription | undefined;
  private email: string | null = localStorage.getItem("email");
  private senderId: string | null = localStorage.getItem("userId");

  constructor(private chatService: ChatService) {}

  ngOnInit() {
    //this.chatService.connect(this.email);

    this.messagesSubscription = this.chatService.getMessages().subscribe(message => {
      this.messages.push(message);
    });
  }

  ngOnDestroy() {
    //this.chatService.disconnect(this.email);
    if (this.messagesSubscription) {
      this.messagesSubscription.unsubscribe();
    }
  }

  sendMessage() {
    if (this.messageContent.trim() !== '') {
      //this.chatService.sendPublicMessage({ senderId: this.senderId, content: this.messageContent });
      this.messageContent = '';
    }
  }
}
