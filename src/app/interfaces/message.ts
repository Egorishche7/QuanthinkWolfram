export interface Message {
  content: string;
  senderId: number;
  receiverId?: number;
}

// export interface Message {
//   id: number;
//   type: 'PUBLIC' | 'PRIVATE' | 'JOIN' | 'LEAVE';
//   content: string;
//   date: string;
//   senderId: number;
//   receiverId?: number; // Опционально для приватных сообщений
//   timestamp: any; // Может быть представлен как объект или строка в зависимости от того, как передается с сервера
// }

//
// export interface Message {
//   id: number;
//   content: string;
//   timestamp: string;
//   sender: string;
// }
