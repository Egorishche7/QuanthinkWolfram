import React, { useState } from 'react';
import { View, TextInput, TouchableOpacity, Text, StyleSheet } from 'react-native';
import axios from 'axios';

const BasicArithmeticComponent = () => {
  const [inputValue, setInputValue] = useState('');
  const [isInputFocused, setIsInputFocused] = useState(false);
  const [inputError, setInputError] = useState('');

  const addCharacter = (character) => {
    setInputValue(inputValue + character);
  };

  const deleteLastCharacter = () => {
    setInputValue(inputValue.slice(0, -1));
  };

  const clearInput = () => {
    setInputValue('');
  };

  const onInputFocus = () => {
    setIsInputFocused(true);
  };

  const onInputBlur = () => {
    setTimeout(() => {
      setIsInputFocused(false);
    }, 200);
  };

  const getTranslation = (key) => {
    // Ваша функция для перевода ключей
    // return translation;
  };

  const calculateBasicArithmetic = () => {
    const userId = localStorage.getItem('userId');

    const calcData = {
      userId: userId,
      expression: inputValue,
      library: localStorage.getItem('Library'),
    };

    axios
      .post('http://localhost:8080/calculate', calcData)
      .then((response) => {
        // Обработка успешного ответа
        console.log(response.data);
        // Отправка результата, если необходимо
      })
      .catch((error) => {
        // Обработка ошибки
        console.error(error);
        // Отправка ошибки, если необходимо
      });
  };

  return (
    <View style={styles.container}>
      <View style={[styles.inputContainer, { borderColor: isInputFocused ? 'orange' : 'grey' }]}>
        <TextInput
          style={styles.input}
          placeholder={getTranslation('Enter what you want to calculate or know about')}
          value={inputValue}
          onChangeText={(text) => setInputValue(text)}
          onFocus={onInputFocus}
          onBlur={onInputBlur}
        />
        <TouchableOpacity style={[styles.button, styles.orangeButton]} onPress={calculateBasicArithmetic}>
          <Text style={styles.buttonText}>{getTranslation('=')}</Text>
        </TouchableOpacity>
      </View>

      <View style={[styles.mathInputContainer, { display: isInputFocused ? 'flex' : 'none' }]}>
        {/* Ваши кнопки */}
      </View>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    padding: 10,
  },
  inputContainer: {
    flexDirection: 'row',
    alignItems: 'center',
    borderWidth: 2,
    borderRadius: 4,
    padding: 10,
    marginBottom: 10,
  },
  input: {
    flex: 1,
    borderWidth: 0,
    outlineColor: 'transparent',
  },
  button: {
    minHeight: 36,
    minWidth: 36,
    padding: 8,
    marginLeft: 4,
    borderRadius: 4,
    justifyContent: 'center',
    alignItems: 'center',
    cursor: 'pointer',
  },
  orangeButton: {
    backgroundColor: 'orange',
  },
  buttonText: {
    color: 'white',
  },
  mathInputContainer: {
    padding: 10,
    backgroundColor: 'orange',
  },
});

export default BasicArithmeticComponent;