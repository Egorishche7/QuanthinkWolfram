import React, { useState } from 'react';
import { View, Text, TextInput, TouchableOpacity, Image } from 'react-native';
import { useForm, Controller } from 'react-hook-form';

const RegisterComponent = () => {
  const {
    control,
    handleSubmit,
    formState: { errors },
  } = useForm();
  
  const onSubmit = (data) => {
    const { confirmPassword, ...postData } = data;
    // Register user logic here
  };

  return (
    <View style={{ flex: 1, alignItems: 'center', justifyContent: 'center' }}>
      <Text>Create an account</Text>
      
      <Controller
        control={control}
        rules={{
          required: true,
          pattern: /^[a-zA-Z]+(?: [a-zA-Z]+)*$/,
        }}
        render={({ field }) => (
          <View>
            <Text>Full Name</Text>
            <TextInput
              {...field}
              placeholder="Type your name"
            />
            {errors.username && <Text>Name is required.</Text>}
          </View>
        )}
        name="username"
        defaultValue=""
      />
      
      <Controller
        control={control}
        rules={{ required: true, pattern: /^\S+@\S+$/i }}
        render={({ field }) => (
          <View>
            <Text>Email</Text>
            <TextInput
              {...field}
              placeholder="Type your email"
            />
            {errors.email && <Text>Email is required.</Text>}
          </View>
        )}
        name="email"
        defaultValue=""
      />
      
      <Controller
        control={control}
        rules={{ required: true }}
        render={({ field }) => (
          <View>
            <Text>Password</Text>
            <TextInput
              {...field}
              secureTextEntry
              placeholder="Type your password"
            />
            {errors.password && <Text>Password is required.</Text>}
          </View>
        )}
        name="password"
        defaultValue=""
      />
      
      <Controller
        control={control}
        rules={{ required: true }}
        render={({ field }) => (
          <View>
            <Text>Confirm Password</Text>
            <TextInput
              {...field}
              secureTextEntry
              placeholder="Confirm Password"
            />
            {errors.confirmPassword && <Text>Confirm the password.</Text>}
            {errors.passwordMismatch && <Text>Passwords should match.</Text>}
          </View>
        )}
        name="confirmPassword"
        defaultValue=""
      />
      
      <TouchableOpacity onPress={handleSubmit(onSubmit)}>
        <Text>Create Account</Text>
      </TouchableOpacity>
      
      <View>
        <Text>Already have an account?</Text>
        <TouchableOpacity>
          <Text>Sign in</Text>
        </TouchableOpacity>
      </View>
      
      <View style={{ marginTop: 20 }}>
        <Text>&copy;2024 QuanThink Wolfram Terms Privacy</Text>
        <View style={{ flexDirection: 'row' }}>
          <Image source={require('./assets/icons/facebook.png')} style={{ width: 20, height: 20 }} />
          <Image source={require('./assets/icons/twitter.png')} style={{ width: 20, height: 20 }} />
          <Image source={require('./assets/icons/telegram.png')} style={{ width: 20, height: 20 }} />
          <Image source={require('./assets/icons/instagram.png')} style={{ width: 20, height: 20 }} />
        </View>
      </View>
    </View>
  );
};

export default RegisterComponent;