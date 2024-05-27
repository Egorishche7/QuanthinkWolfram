import { Component, OnInit } from '@angular/core';
import { ChatService } from '../../services/chat.service';
import { MessageService } from 'primeng/api';
import { Message } from '../../interfaces/message';

@Component({
  selector: 'app-online-chat',
  templateUrl: './online-chat.component.html',
  styleUrls: ['./online-chat.component.css']
})
export class OnlineChatComponent implements OnInit {
  username: string | undefined;
  text: string | undefined;

  received: Message[] = [];
  sent: Message[] = [];

  connected: boolean = false;

  constructor(private chatService: ChatService, private messageService: MessageService) { }

  ngOnInit(): void {
    this.username = localStorage.getItem('userId') || undefined;

    this.chatService.getMessages().subscribe((message: Message) => {
      if (message.senderId !== parseInt(this.username || '', 10)) {
        this.received.push(message);
        this.messageService.add({
          severity: 'info',
          summary: 'New message from ' + message.senderId,
          detail: message.content
        });
      }
    });
  }

  connect() {
    const email = localStorage.getItem('email') || '';
    if (email) {
      this.chatService.connect(email);
      this.connected = true;
    }
  }

  disconnect() {
    const email = localStorage.getItem('email') || '';
    if (this.connected && email) {
      this.chatService.disconnect(email);
      this.connected = false;
      this.sent = [];
      this.received = [];
      this.username = '';
      this.text = '';
    }
  }

  sendMessage() {
    const senderId = localStorage.getItem('userId');
    // @ts-ignore
    if (senderId && this.text.trim() !== '') {
      const message: Message = {
        senderId: parseInt(senderId, 10),
        content: this.text,
        time: new Date().toISOString()
      };
      this.chatService.sendPublicMessage(message);
      this.received.push(message);  // Сразу отображаем сообщение
      this.text = '';
    }
  }
}
