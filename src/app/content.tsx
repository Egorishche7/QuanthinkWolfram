import React from 'react';
import { View } from 'react-native';
import HomeComponent from './components/home/HomeComponent';

export default function Content() {
  return (
    <View style={{ flex: 1 }}>
      <HomeComponent />
    </View>
  );
}