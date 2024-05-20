import React from 'react';
import { NavigationContainer } from '@react-navigation/native';
import { createStackNavigator } from '@react-navigation/stack';

import HomeComponent from './components/home/HomeComponent';

const Stack = createStackNavigator();

export default function AppRouting() {
  return (
    <NavigationContainer>
      <Stack.Navigator>
        <Stack.Screen name="Home" component={HomeComponent} />

      </Stack.Navigator>
    </NavigationContainer>
  );
}