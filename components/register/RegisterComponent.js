import React, { useState } from 'react';
import { View, Text, TextInput, Button, StyleSheet, Alert } from 'react-native';
import { useNavigation } from '@react-navigation/native';
import AsyncStorage from '@react-native-async-storage/async-storage';

function RegisterComponent() {
  const [username, setUsername] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');
  const [inputError, setInputError] = useState('');
  const navigation = useNavigation();

  const handleRegister = async () => {
    if (!email || !email.includes('@')) {
      Alert.alert('Error', 'Please enter a valid email.');
      return;
    }

    if (password !== confirmPassword) {
      Alert.alert('Error', 'Passwords should match.');
      return;
    }

    try {
      await AsyncStorage.setItem('registeredEmail', email);
      await AsyncStorage.setItem('registeredPassword', password);
      Alert.alert('Success', 'Registered successfully');
      navigation.navigate('Login');
    } catch (error) {
      console.log('Registration error:', error);
      Alert.alert('Error', 'An error occurred during registration');
    }
  };

  return (
    <View style={styles.container}>
      <Text style={styles.header}>Create an account</Text>
      <TextInput
        style={styles.input}
        placeholder="Type your name"
        value={username}
        onChangeText={setUsername}
      />
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
      <TextInput
        style={styles.input}
        placeholder="Confirm Password"
        value={confirmPassword}
        onChangeText={setConfirmPassword}
        secureTextEntry
      />
      <Button
        title="Create Account"
        onPress={handleRegister}
        disabled={!username || !email || !password || !confirmPassword}
      />
      <Text style={styles.link}>
        Already have an account?{' '}
        <Text style={styles.signInLink} onPress={() => navigation.navigate('Login')}>
          Sign in
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
  signInLink: {
    color: 'orange',
    fontWeight: 'bold',
  },
});

export default RegisterComponent;