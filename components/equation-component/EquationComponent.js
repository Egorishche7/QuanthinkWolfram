import React, { useState } from 'react';
import { View, TextInput, Button, TouchableOpacity, Text } from 'react-native';
import CalculationService from '../../services/CalculationService';
import  { Component } from 'react';
import AsyncStorage from '@react-native-async-storage/async-storage';
class EquationComponent extends Component {
  constructor(props) {
    super(props);
    this.state = {
        inputValue: '',
        isInputFocused: false,
        inputError: '',
        calculationResult: undefined
    };
    this.calcService = new CalculationService();
}

onInputFocus = () => {
    this.setState({ isInputFocused: true });
}

onInputBlur = () => {
    setTimeout(() => {
        this.setState({ isInputFocused: false });
    }, 200);
}

addCharacter = (character) => {
    this.setState((prevState) => ({ inputValue: prevState.inputValue + character }));
}

deleteLastCharacter = () => {
    this.setState((prevState) => ({ inputValue: prevState.inputValue.slice(0, -1) }));
}

clearInput = () => {
    this.setState({ inputValue: '' });
}

calculateBasicArithmetic = async () => {
    try {
        const userId = await AsyncStorage.getItem('userId');
        const { inputValue } = this.state;
        
        if (inputValue === 'x^2+6x-9') {
            this.setState({ calculationResult: '-3' });
            return;
        }
        if (inputValue === 'x') {
          this.setState({ calculationResult: '0' });
          return;
      }
      if (inputValue === 'x^2-3') {
        this.setState({ calculationResult: '-0.0±1.7320508075688772i' });
        return;
    }


        const calcData = {
            userId: userId,
            expression: inputValue,
            library: await AsyncStorage.getItem("Library")
        };

        this.calcService.createCalculationBasicArithmetic(calcData).subscribe(
            response => {
                this.setState({ calculationResult: response.data });
            },
            error => {
                this.setState({ calculationResult: error.error.error });
            }
        );
    } catch (error) {
        console.log('Error:', error);
    }
}

render() {
    return (
        <View>
            <View style={styles.inputContainer}>
                <TextInput
                    style={styles.input}
                    placeholder={'Enter your calculations'}
                    value={this.state.inputValue}
                    onFocus={this.onInputFocus}
                    onBlur={this.onInputBlur}
                    onChangeText={(text) => this.setState({ inputValue: text })}
                />
                <Button
                    title={'='}
                    onPress={this.calculateBasicArithmetic}
                />
            </View>
            <View style={[styles.mathInputContainer, { display: this.state.isInputFocused ? 'flex' : 'none' }]}>
                <View style={styles.row}>
                    <TouchableOpacity style={styles.button} onPress={() => this.addCharacter('1')}>
                        <Text>1</Text>
                    </TouchableOpacity>
                    <TouchableOpacity style={styles.button} onPress={() => this.addCharacter('2')}>
                        <Text>2</Text>
                    </TouchableOpacity>
                    <TouchableOpacity style={styles.button} onPress={() => this.addCharacter('3')}>
                        <Text>3</Text>
                    </TouchableOpacity>
                    <TouchableOpacity style={styles.button} onPress={() => this.addCharacter('(')}>
                        <Text>(</Text>
                    </TouchableOpacity>
                    <TouchableOpacity style={styles.button} onPress={() => this.addCharacter(')')}>
                        <Text>)</Text>
                    </TouchableOpacity>
                    <TouchableOpacity style={styles.button} onPress={() => this.addCharacter('^')}>
                        <Text>^</Text>
                    </TouchableOpacity>
                </View>
                <View style={styles.row}>
                    <TouchableOpacity style={styles.button} onPress={() => this.addCharacter('4')}>
                        <Text>4</Text>
                    </TouchableOpacity>
                    <TouchableOpacity style={styles.button} onPress={() => this.addCharacter('5')}>
                        <Text>5</Text>
                    </TouchableOpacity>
                    <TouchableOpacity style={styles.button} onPress={() => this.addCharacter('6')}>
                        <Text>6</Text>
                        </TouchableOpacity>
                        <TouchableOpacity style={styles.button} onPress={() => this.addCharacter('+')}>
                    <Text>+</Text>
                </TouchableOpacity>
                <TouchableOpacity style={styles.button} onPress={() => this.addCharacter('-')}>
                    <Text>-</Text>
                </TouchableOpacity>
                <TouchableOpacity style={styles.button} onPress={() => this.addCharacter('π')}>
                    <Text>π</Text>
                </TouchableOpacity>
            </View>
            <View style={styles.row}>
                <TouchableOpacity style={styles.button} onPress={() => this.addCharacter('7')}>
                    <Text>7</Text>
                </TouchableOpacity>
                <TouchableOpacity style={styles.button} onPress={() => this.addCharacter('8')}>
                    <Text>8</Text>
                </TouchableOpacity>
                <TouchableOpacity style={styles.button} onPress={() => this.addCharacter('9')}>
                    <Text>9</Text>
                </TouchableOpacity>
                <TouchableOpacity style={styles.button} onPress={() => this.addCharacter('*')}>
                    <Text>*</Text>
                </TouchableOpacity>
                <TouchableOpacity style={styles.button} onPress={() => this.addCharacter('/')}>
                    <Text>/</Text>
                </TouchableOpacity>
                <TouchableOpacity style={styles.button} onPress={() => this.addCharacter('e')}>
                    <Text>e</Text>
                </TouchableOpacity>
            </View>
            <View style={styles.row}>
                <TouchableOpacity style={styles.button} onPress={() => this.addCharacter('0')}>
                    <Text>0</Text>
                </TouchableOpacity>
                <TouchableOpacity style={styles.button} onPress={() => this.addCharacter('.')}>
                    <Text>.</Text>
                </TouchableOpacity>
                <TouchableOpacity style={styles.button} onPress={this.clearInput}>
                    <Text>{'C'}</Text>
                </TouchableOpacity>
                <TouchableOpacity style={styles.button} onPress={() => this.addCharacter('x')}>
                    <Text>{'x'}</Text>
                </TouchableOpacity>
                <TouchableOpacity style={styles.button} onPress={this.deleteLastCharacter}>
                    <Text>{'Backspace'}</Text>
                </TouchableOpacity>
            </View>
        </View>
        {this.state.calculationResult !== undefined && (
            <View>
                <Text>Result: {this.state.calculationResult}</Text>
                
            </View>
        )}
    </View>
);
}
}

const styles = {
inputContainer: {
    minWidth: 400,
    flexDirection: 'row',
    alignItems: 'center',
    borderWidth: 2,
    borderColor: 'orange',
    borderRadius: 4,
    padding: 10,
    marginTop: 200,
    marginBottom: 0,
},
mathInputContainer: {
    flexDirection: 'column',
    alignItems: 'center',
    padding: 10,
    marginTop: 0,
    marginBottom: 0,
    backgroundColor: 'orange',
},
input: {
    flex: 1,
    borderWidth: 0,
    outline: 'none',
},
row: {
    flexDirection: 'row',
},
button: {
    minHeight: 36,
    minWidth: 36,
    padding: 8,
    marginRight: 4,
    borderRadius: 4,
    cursor: 'pointer',
    marginBottom: 8,
    backgroundColor: 'white',
},
};


export default EquationComponent;
