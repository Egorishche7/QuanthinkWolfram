import React from 'react';
import { NavigationContainer } from '@react-navigation/native';
import { createStackNavigator } from '@react-navigation/stack';
import HomeComponent from './components/home/HomeComponent';

const Stack = createStackNavigator();

export default function App() {
  return (
    <NavigationContainer>
      <Stack.Navigator initialRouteName="Home">
        <Stack.Screen name="Home" component={HomeComponent} />
        <Stack.Screen name="Login" component={LoginComponent} />
        <Stack.Screen name="Chat" component={ChatComponent} />
      </Stack.Navigator>
    </NavigationContainer>
  );
}