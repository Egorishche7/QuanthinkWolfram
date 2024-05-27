export interface Message {
  content: string | undefined;
  senderId: number;
  receiverId?: number;
  time?: string;
}
