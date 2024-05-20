import React, { useState, useEffect } from 'react';
import { View, Text, StyleSheet, TouchableOpacity } from 'react-native';
import { CalculationService } from "../../services/calc.service";
import { useNavigation } from '@react-navigation/native';
import { LanguageService } from '../../services/language.service';
import BasicArithmeticComponent from './BasicArithmeticComponent';
import EquationComponent from './EquationComponent';
import MatrixComponent from './MatrixComponent';

const InputComponent = () => {
const [selectedCalculations, setSelectedCalculations] = useState('BA');
const [inputValue, setInputValue] = useState('');
const [isInputFocused, setIsInputFocused] = useState(false);
const [calculationResult, setCalculationResult] = useState(undefined);
const [inputError, setInputError] = useState('');
const navigation = useNavigation();
const calcService = new CalculationService();
const languageService = new LanguageService();

useEffect(() => {
const languageChangedCallback = () => {
setInputError('');
};
languageService.languageChanged.subscribe(languageChangedCallback);
return () => {
languageService.languageChanged.unsubscribe(languageChangedCallback);
};
}, []);

const onInputFocus = () => {
setIsInputFocused(true);
};

const onInputBlur = () => {
setTimeout(() => {
setIsInputFocused(false);
}, 200);
};

const addCharacter = (character) => {
setInputValue(inputValue + character);
};

const deleteLastCharacter = () => {
setInputValue(inputValue.slice(0, -1));
};

const clearInput = () => {
setInputValue('');
};

const showResult = (result) => {
console.log(result);
setCalculationResult(result);
};

const getTranslation = (key) => {
return languageService.getTranslation(key);
};

const changeCalc = (key) => {
setSelectedCalculations(key);
};

return (
<View style={styles.container}>
<View style={styles.inputContainer}>
{selectedCalculations === 'BA' && (
<BasicArithmeticComponent resultEvent={showResult} />
)}
{selectedCalculations === 'Eq' && (
<EquationComponent resultEvent={showResult} />
)}
{selectedCalculations === 'M' && (
<MatrixComponent resultEventM={showResult} />
)}
</View>
<View style={styles.buttonContainer}>
<TouchableOpacity
style={styles.button}
onPress={() => changeCalc('BA')}
>
<Text style={styles.buttonText}>{getTranslation('Basic Arithmetics')}</Text>
</TouchableOpacity>
<TouchableOpacity
style={styles.button}
onPress={() => changeCalc('Eq')}
>
<Text style={styles.buttonText}>{getTranslation('Equations')}</Text>
</TouchableOpacity>
<TouchableOpacity
style={styles.button}
onPress={() => changeCalc('M')}
>
<Text style={styles.buttonText}>{getTranslation('Matrix')}</Text>
</TouchableOpacity>
</View>
{calculationResult !== undefined && (
<View style={styles.resultContainer}>
<Text>{getTranslation('Result')}:</Text>
<Text>{calculationResult}</Text>
</View>
)}
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
flex: 1,
width: '80%',
},
buttonContainer: {
flexDirection: 'row',
justifyContent: 'space-between',
width: '80%',
marginTop: 10,
},
button: {
height: 64,
width: 96,
padding: 8,
marginLeft: 16,
borderRadius: 16,
alignItems: 'center',
justifyContent: 'center',
backgroundColor: 'white',
borderColor: 'orange',
},
buttonText: {
fontSize: 16,
},
resultContainer: {
marginTop: 10,
},
});

export default InputComponent;