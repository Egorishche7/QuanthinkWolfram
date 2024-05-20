import React, { useState } from 'react';
import { View, TextInput, TouchableOpacity, Text, StyleSheet } from 'react-native';
import axios from 'axios';

const EquationComponent = ({ resultEvent }) => {
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

  const calculateEquation = () => {
    const userId = localStorage.getItem('userId');

    const calcData = {
      userId: userId,
      equation: inputValue,
      library: localStorage.getItem('Library'),
    };

    axios
      .post('http://localhost:8080/calculateEquation', calcData)
      .then((response) => {
        // Обработка успешного ответа
        console.log(response.data);
        resultEvent.emit(response.data);
      })
      .catch((error) => {
        // Обработка ошибки
        console.error(error);
        resultEvent.emit(error.response.data.error);
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
        <TouchableOpacity style={[styles.button, styles.orangeButton]} onPress={calculateEquation}>
          <Text style={styles.buttonText}>{getTranslation('=')}</Text>
        </TouchableOpacity>
      </View>

      <View style={[styles.mathInputContainer, { display: isInputFocused ? 'flex' : 'none' }]}>
        <View style={styles.row}>
          <TouchableOpacity style={styles.button} onPress={() => addCharacter('1')}>
            <Text style={styles.buttonText}>1</Text>
          </TouchableOpacity>
          <TouchableOpacity style={styles.button} onPress={() => addCharacter('2')}>
            <Text style={styles.buttonText}>2</Text>
          </TouchableOpacity>
          <TouchableOpacity style={styles.button} onPress={() => addCharacter('3')}>
            <Text style={styles.buttonText}>3</Text>
          </TouchableOpacity>
          <TouchableOpacity style={styles.button} onPress={() => addCharacter('(')}>
            <Text style={styles.buttonText}>(</Text>
          </TouchableOpacity>
          <TouchableOpacity style={styles.button} onPress={() => addCharacter(')')}>
            <Text style={styles.buttonText}>)</Text>
          </TouchableOpacity>
          <TouchableOpacity style={styles.button} onPress={() => addCharacter('^')}>
            <Text style={styles.buttonText}>^</Text>
          </TouchableOpacity>
        </View>
        <View style={styles.row}>
          <TouchableOpacity style={styles.button} onPress={() => addCharacter('4')}>
            <Text style={styles.buttonText}>4</Text>
          </TouchableOpacity>
          <TouchableOpacity style={styles.button} onPress={() => addCharacter('5')}>
            <Text style={styles.buttonText}>5</Text>
          </TouchableOpacity>
          <TouchableOpacity style={styles.button} onPress={() => addCharacter('6')}>
            <Text style={styles.buttonText}>6</Text>
          </TouchableOpacity>
          <TouchableOpacity style={styles.button} onPress={() => addCharacter('+')}>
            <Text style={styles.buttonText}>+</Text>
          </TouchableOpacity>
          <TouchableOpacity style={styles.button} onPress={() => addCharacter('-')}>
            <Text style={styles.buttonText}>-</Text>
          </TouchableOpacity>
          <TouchableOpacity style={styles.button} onPress={() => addCharacter('π')}>
            <Text style={styles.buttonText}>π</Text>
          </TouchableOpacity>
        </View>
        <View style={styles.row}>
          <TouchableOpacity style={styles.button} onPress={() => addCharacter('7')}>
            <Text style={styles.buttonText}>7</Text>
          </TouchableOpacity>
          <TouchableOpacity style={styles.button} onPress={() => addCharacter('8')}>
            <Text style={styles.buttonText}>8</Text>
          </TouchableOpacity>
         <TouchableOpacity style={styles.button} onPress={() => addCharacter('9')}>
            <Text style={styles.buttonText}>9</Text>
          </TouchableOpacity>
          <TouchableOpacity style={styles.button} onPress={() => addCharacter('*')}>
            <Text style={styles.buttonText}>*</Text>
          </TouchableOpacity>
          <TouchableOpacity style={styles.button} onPress={() => addCharacter('/')}>
            <Text style={styles.buttonText}>/</Text>
          </TouchableOpacity>
          <TouchableOpacity style={styles.button} onPress={() => addCharacter('e')}>
            <Text style={styles.buttonText}>e</Text>
          </TouchableOpacity>
        </View>
        <View style={styles.row}>
          <TouchableOpacity style={styles.button} onPress={() => addCharacter('0')}>
            <Text style={styles.buttonText}>0</Text>
          </TouchableOpacity>
          <TouchableOpacity style={styles.button} onPress={() => addCharacter('.')}>
            <Text style={styles.buttonText}>.</Text>
          </TouchableOpacity>
          <TouchableOpacity style={styles.button} onPress={() => deleteLastCharacter()}>
            <Text style={styles.buttonText}>DEL</Text>
          </TouchableOpacity>
          <TouchableOpacity style={styles.button} onPress={() => clearInput()}>
            <Text style={styles.buttonText}>CLR</Text>
          </TouchableOpacity>
        </View>
      </View>

      {inputError && <Text style={styles.errorText}>{inputError}</Text>}
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
  },
  inputContainer: {
    flexDirection: 'row',
    alignItems: 'center',
    borderWidth: 1,
    borderRadius: 5,
    padding: 10,
    marginBottom: 20,
  },
  input: {
    flex: 1,
    height: 40,
    marginRight: 10,
  },
  button: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    borderWidth: 1,
    borderRadius: 5,
    margin: 5,
    padding: 10,
  },
  orangeButton: {
    backgroundColor: 'orange',
  },
  buttonText: {
    fontSize: 18,
    fontWeight: 'bold',
  },
  mathInputContainer: {
    width: '100%',
    paddingHorizontal: 20,
    marginTop: 10,
  },
  row: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    marginBottom: 10,
  },
  errorText: {
    color: 'red',
    marginTop: 10,
  },
});

export default EquationComponent;