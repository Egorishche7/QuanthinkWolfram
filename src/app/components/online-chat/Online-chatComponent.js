import React, { useState, useEffect } from 'react';
import { View, Text, TextInput, TouchableOpacity, ScrollView, StyleSheet } from 'react-native';
import { useNavigation } from '@react-navigation/native';
import { ChatService } from '../../services/chat.service';
import { showMessage } from 'react-native-flash-message';
import { Message } from '../../interfaces/message';
export default function OnlineChatComponent() {
  const navigation = useNavigation();
  const chatService = new ChatService();
  const [messages, setMessages] = useState<Message[]>([]);
  const [newMessage, setNewMessage] = useState<string>('');

  useEffect(() => {
    setTimeout(() => {
      loadMessages();
      subscribeToNewMessages();
    }, 500);
  }, []);

  const loadMessages = () => {
    chatService.getMessages().then(
      (messages: Message[]) => {
        setMessages(messages);
        console.log('Previous messages:', messages);
      },
      (error) => {
        console.error('Failed to load messages:', error);
      }
    );
  };

  const sendMessage = (message: string) => {
    const newMessage: Message = {
      id: 0,
      content: message,
      timestamp: new Date().toISOString(),
      sender: ''
    };
    chatService.sendMessage(newMessage);
    console.log('Sent message:', newMessage);
    setNewMessage('');
  };

  const subscribeToNewMessages = () => {
    chatService.receiveMessages().subscribe(
      (message: Message) => {
        setMessages((prevMessages) => [...prevMessages, message]);
        console.log('Received message:', message);
      },
      (error) => {
        console.error('Failed to receive message:', error);
      }
    );
  };

  return (
    <View style={styles.chatContainer}>
      <ScrollView style={styles.messagesContainer}>
        {messages.map((message) => (
          <View key={message.id} style={styles.message}>
            <Text style={styles.messageSender}>{message.sender}</Text>
            <Text style={styles.messageContent}>{message.content}</Text>
            <Text style={styles.messageTimestamp}>{message.timestamp}</Text>
          </View>
        ))}
      </ScrollView>
      <View style={styles.inputContainer}>
        <TextInput
          style={styles.messageInput}
          value={newMessage}
          onChangeText={(text) => setNewMessage(text)}
          placeholder="Type a message..."
        />
        <TouchableOpacity style={styles.sendButton} onPress={() => sendMessage(newMessage)}>
          <Text style={styles.sendButtonText}>Send</Text>
        </TouchableOpacity>
      </View>
    </View>
  );
}

const styles = StyleSheet.create({
  chatContainer: {
    flex: 1,
    flexDirection: 'column',
    height: '100%',
  },
  messagesContainer: {
    flex: 1,
    padding: 10,
  },
  message: {
    marginBottom: 10,
  },
  messageSender: {
    fontWeight: 'bold',
  },
  messageContent: {
    marginTop: 5,
  },
  messageTimestamp: {
    fontSize: 12,
    color: 'gray',
  },
  inputContainer: {
    display: 'flex',
    padding: 10,
    flexDirection: 'row',
  },
  messageInput: {
    flex: 1,
    padding: 5,
    borderWidth: 1,
    borderColor: 'lightgray',
    borderRadius: 5,
  },
  sendButton: {
    marginLeft: 10,
    paddingVertical: 5,
    paddingHorizontal: 10,
    backgroundColor: '#007bff',
    borderRadius: 5,
  },
  sendButtonText: {
    color: 'white',
  },
});