import React, { useState } from 'react';
import { View, Text, TextInput, Button } from 'react-native';
import CalculationService from '../../services/CalculationService';
import EquationComponent from '../equation-component/EquationComponent';
import BasicArithmeticComponent from '../basic-arithemtic-component/basic-arithemticComponent';
import MatrixComponent from '../matrix-component/MatrixComponent';
import { KeyboardAvoidingView } from 'react-native';
const InputComponent = ({ selectedCalculations }) => {
const [inputValue, setInputValue] = useState('');
const [isInputFocused, setIsInputFocused] = useState(false);
const [calculationResult, setCalculationResult] = useState('');
const [inputError, setInputError] = useState('');

const calcService = new CalculationService();

const onInputFocus = () => {
setIsInputFocused(true);
};

const onInputBlur = () => {
setTimeout(() => {
setIsInputFocused(false);
}, 200);
};

const addCharacter = (character) => {
setInputValue((prevValue) => prevValue + character);
};

const deleteLastCharacter = () => {
setInputValue((prevValue) => prevValue.slice(0, -1));
};

const clearInput = () => {
setInputValue('');
};

const showResult = (result) => {
console.log(result);
setCalculationResult(result);
};

const changeCalc = (key) => {
// Implement changeCalc functionality
};

return (
<View>
  <View>
    {selectedCalculations === 'BA' && (
      <View>
        <BasicArithmeticComponent
          inputValue={inputValue}
          setInputValue={setInputValue}
          isInputFocused={isInputFocused}
          onInputFocus={onInputFocus}
          onInputBlur={onInputBlur}
          addCharacter={addCharacter}
          deleteLastCharacter={deleteLastCharacter}
          clearInput={clearInput}
          showResult={showResult}
        />
      </View>
    )}
    {selectedCalculations === 'Eq' && (
      <View>
        <EquationComponent
          inputValue={inputValue}
          setInputValue={setInputValue}
          isInputFocused={isInputFocused}
          onInputFocus={onInputFocus}
          onInputBlur={onInputBlur}
          addCharacter={addCharacter}
          deleteLastCharacter={deleteLastCharacter}
          clearInput={clearInput}
          showResult={showResult}
        />
      </View>
    )}
    {selectedCalculations === 'M' && (
      <View>
        <MatrixComponent
          inputValue={inputValue}
          setInputValue={setInputValue}
          isInputFocused={isInputFocused}
          onInputFocus={onInputFocus}
          onInputBlur={onInputBlur}
          addCharacter={addCharacter}
          deleteLastCharacter={deleteLastCharacter}
          clearInput={clearInput}
          showResult={showResult}
        />
      </View>
    )}
    {/* Implement other calculation components based on the selectedCalculations value */}
  </View>

  
</View>
);
};

export default InputComponent;