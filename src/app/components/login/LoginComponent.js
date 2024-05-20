import React, { useState } from 'react';
import { View, Text, TextInput, TouchableOpacity } from 'react-native';
import { useForm, Controller } from 'react-hook-form';
import { showMessage } from 'react-native-flash-message';
import { useNavigation } from '@react-navigation/native';
import { useTranslation } from 'react-i18next';
import { login } from '../../services/authService';

export default function LoginComponent() {
  const navigation = useNavigation();
  const { t } = useTranslation();
  const { control, handleSubmit, errors } = useForm();
  const [inputError, setInputError] = useState('');

  const onSubmit = async (data) => {
    try {
      const response = await login(data);
      console.log(response);
      // сохраните данные в AsyncStorage или SecureStore
      showMessage({
        type: 'success',
        message: 'Success',
        description: 'Login successful',
      });
      navigation.navigate('Home');
    } catch (error) {
      showMessage({
        type: 'error',
        message: 'Error',
        description: 'Incorrect email or password',
      });
    }
  };

  return (
    <View style={styles.container}>
      <Text style={styles.header}>{t('Sign In')}</Text>
      <View style={styles.field}>
        <Text style={styles.label}>{t('Email')}</Text>
        <Controller
          control={control}
          render={({ onChange, value }) => (
            <TextInput
              style={styles.input}
              onChangeText={(text) => onChange(text)}
              value={value}
              placeholder={t('Type your email')}
            />
          )}
          name="email"
          rules={{ required: true, pattern: { value: /\S+@\S+\.\S+/ } }}
          defaultValue=""
        />
        {errors.email && (
          <Text style={styles.error}>{t('Email is required.')}</Text>
        )}
      </View>
      <View style={styles.field}>
        <Text style={styles.label}>{t('Password')}</Text>
        <Controller
          control={control}
          render={({ onChange, value }) => (
            <TextInput
              style={styles.input}
              onChangeText={(text) => onChange(text)}
              value={value}
              placeholder={t('Type your password')}
              secureTextEntry
            />
          )}
          name="password"
          rules={{ required: true }}
          defaultValue=""
        />
        {errors.password && (
          <Text style={styles.error}>{t('Password is required.')}</Text>
        )}
      </View>
      <TouchableOpacity
        style={styles.button}
        onPress={handleSubmit(onSubmit)}
        disabled={Object.keys(errors).length !== 0}
      >
        <Text style={styles.buttonText}>{t('SIGN IN')}</Text>
      </TouchableOpacity>
      <View style={styles.bottomText}>
        <Text>{t("Don't have an account?")}</Text>
        <TouchableOpacity onPress={() => navigation.navigate('Register')}>
          <Text style={styles.signUpText}>{t('Create one.')}</Text>
        </TouchableOpacity>
      </View>
      <Text style={styles.inputError}>{inputError}</Text>
    </View>
  );
}

const styles = {
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    paddingHorizontal: 20,
  },
  header: {
    fontSize: 20,
    fontWeight: 'bold',
    marginBottom: 20,
  },
  field: {
    marginBottom: 20,
  },
  label: {
    color: 'orange',
    marginBottom: 5,
  },
  input: {
    borderWidth: 1,
    borderColor: 'orange',
    height: 40,
    paddingHorizontal: 10,
  },
  error: {
    color: 'red',
  },
  button: {
    backgroundColor: 'orange',
    paddingVertical: 10,
    paddingHorizontal: 20,
    borderRadius: 5,
    marginBottom: 20,
  },
  buttonText: {
    color: 'white',
    textAlign: 'center',
  },
  bottomText: {
    flexDirection: 'row',
    alignItems: 'center',
  },
  signUpText: {
    color: 'orange',
    marginLeft: 5,
  },
  inputError: {
    color: 'red',
  },
};