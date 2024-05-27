import React, { useState, useEffect } from 'react';
import { View, Text, TextInput, Button, StyleSheet, Alert } from 'react-native';
import { useNavigation } from '@react-navigation/native';
import AsyncStorage from '@react-native-async-storage/async-storage';

function LoginComponent() {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [inputError, setInputError] = useState('');
  const navigation = useNavigation();

  useEffect(() => {
    checkLoggedIn();
  }, []);

  const checkLoggedIn = async () => {
    try {
      const storedEmail = await AsyncStorage.getItem('email');
      if (storedEmail) {
        navigation.navigate('Home');
      }
    } catch (error) {
      console.log('Error checking logged in:', error);
    }
  };

  const handleLogin = async () => {
    if (!email || !email.includes('@')) {
      Alert.alert('Error', 'Please enter a valid email.');
      return;
    }

    if (!password) {
      Alert.alert('Error', 'Please enter your password.');
      return;
    }

    try {
      const storedEmail = await AsyncStorage.getItem('registeredEmail');
      const storedPassword = await AsyncStorage.getItem('registeredPassword');

      if (email === storedEmail && password === storedPassword) {
        await AsyncStorage.setItem('email', email);
        Alert.alert('Success', 'Login successful');
        navigation.navigate('Home');
      } else {
        Alert.alert('Error', 'Incorrect email or password');
      }
    } catch (error) {
      console.log('Login error:', error);
      Alert.alert('Error', 'An error occurred during login');
    }
  };

  return (
    <View style={styles.container}>
      <Text style={styles.header}>Sign In</Text>
      <TextInput
        style={styles.input}
        placeholder="Type your email"
        value={email}
        onChangeText={setEmail}
        keyboardType="email-address"
        autoCapitalize="none"
      />
      <TextInput
        style={styles.input}
        placeholder="Type your password"
        value={password}
        onChangeText={setPassword}
        secureTextEntry
      />
      <Button title="SIGN IN" onPress={handleLogin} disabled={!email || !password} />
      <Text style={styles.link}>
        Don't have an account?{' '}
        <Text style={styles.createAccountLink} onPress={() => navigation.navigate('Register')}>
          Create one.
        </Text>
      </Text>
      <Text>{inputError}</Text>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    padding: 20,
  },
  header: {
    fontSize: 24,
    marginBottom: 10,
  },
  input: {
    borderColor: 'orange',
    borderWidth: 1,
    borderRadius: 4,
    padding: 10,
    marginBottom: 5,
  },
  link: {
    color: 'orange',
    marginTop: 10,
  },
  createAccountLink: {
    color: 'orange',
    fontWeight: 'bold',
  },
});

export default LoginComponent;