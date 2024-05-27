import React, { Component } from 'react';
import { View, Text, TextInput, Button } from 'react-native';
import { CalculationService } from "../../services/CalculationService";
import {
  MatrixSum,
  Matrix,
  MatrixSub,
  MatrixMul,
  MatrixSystem,
  MatrixReverse,
  MatrixTranspose,
  MatrixMulByNum,
  MatrixDeterminant
} from "../../interfaces/calculation";

class MatrixComponent extends Component {
  constructor(props) {
    super(props);
    this.state = {
      MulByA: 1,
      MulByB: 1,
      inputValue: '',
      isInputFocused: false,
      inputError: '',
      ThreadCount: 1,
      Matrix1: [[0, 0, 0, 0, 0, 0, 0],
      [0, 0, 0, 0, 0, 0, 0],
      [0, 0, 0, 0, 0, 0, 0],
      [0, 0, 0, 0, 0, 0, 0],
      [0, 0, 0, 0, 0, 0, 0],
      [0, 0, 0, 0, 0, 0, 0],
      [0, 0, 0, 0, 0, 0, 0]],
      Matrix2: [[0, 0, 0, 0, 0, 0, 0],
      [0, 0, 0, 0, 0, 0, 0],
      [0, 0, 0, 0, 0, 0, 0],
      [0, 0, 0, 0, 0, 0, 0],
      [0, 0, 0, 0, 0, 0, 0],
      [0, 0, 0, 0, 0, 0, 0],
      [0, 0, 0, 0, 0, 0, 0]],
      Arows: 3,
      Acols: 3,
      Brows: 3,
      Bcols: 3
    };
    this.calcService = new CalculationService();
    this.router = new Router();
    this.languageService = new LanguageService();
  }

  componentDidMount() {
    this.languageService.languageChanged.subscribe(() => {
      this.languageChangedCallback();
    });
    this.onChangeA();
    this.onChangeB();
  }

  languageChangedCallback() {
    this.setState({ inputError: '' });
  }

  onInputFocus() {
    this.setState({ isInputFocused: true });
  }

  onInputBlur() {
    setTimeout(() => {
      this.setState({ isInputFocused: false });
    }, 200);
  }

  addCharacter(character) {
    this.setState((prevState) => ({ inputValue: prevState.inputValue + character }));
  }

  deleteLastCharacter() {
    this.setState((prevState) => ({ inputValue: prevState.inputValue.slice(0, -1) }));
  }

  clearInput() {
    this.setState({ inputValue: '' });
  }

  getTranslation(key) {
    return this.languageService.getTranslation(key);
  }

  matrixSum() {
    const userId = localStorage.getItem('userId');

    const matrix1 = {
      rows: this.state.Arows,
      cols: this.state.Acols,
      data: this.state.Matrix1
    };
    const matrix2 = {
      rows: this.state.Brows,
      cols: this.state.Bcols,
      data: this.state.Matrix2
    };
    const calcData = {
      userId: userId,
      matrix1: matrix1,
      matrix2: matrix2,
      threadsUsed: this.state.ThreadCount,
      library: localStorage.getItem("Library")
    };

    this.calcService.createCalculationMatrixSum(calcData).subscribe(
      response => {
        let result = "";
        for (let i = 0; i < response.data.size[0]; i++) {
          result += "[";
          for (let j = 0; j < response.data.size[1]; j++) {
            result += response.data.data[i][j] + ", "
          }
          result = result.slice(0, result.length - 2)
          result += "], ";
        }
        result = result.slice(0, result.length - 2)
        console.log(result);
        this.props.resultEventM.emit(result);
      },
      error => {
        this.props.resultEventM.emit(error.error.error);
      }
    );
  }

  matrixSub() {
    const userId = localStorage.getItem('userId');

    const matrix1 = {
      rows: this.state.Arows,
      cols: this.state.Acols,
      data: this.state.Matrix1
    };
    const matrix2 = {
      rows: this.state.Brows,
      cols: this.state.Bcols,
      data: this.state.Matrix2
    };
    const calcData = {
      userId: userId,
      matrix1: matrix1,
      matrix2: matrix2,
      threadsUsed: this.state.ThreadCount,
      library: localStorage.getItem("Library")
    };

    this.calcService.createCalculationMatrixSub(calcData).subscribe(
      response => {
        let result = "";
        for (let i = 0; i < response.data.size[0]; i++) {
          result += "[";
          for (let j = 0; j < response.data.size[1]; j++) {
            result += response.data.data[i][j] + ", "
          }
          result = result.slice(0, result.length - 2)
          result += "], ";
        }
        result = result.slice(0, result.length - 2)
        console.log(result);
        this.props.resultEventM.emit(result);
      },
      error => {
        this.props.resultEventM.emit(error.error.error);
      }
    );
  }

  matrixMul() {
    const userId = localStorage.getItem('userId');

    const matrix1 = {
      rows: this.state.Arows,
      cols: this.state.Acols,
      data: this.state.Matrix1
    };
    const matrix2 = {
      rows: this.state.Brows,
      cols: this.state.Bcols,
      data: this.state.Matrix2
    };
    const calcData = {
      userId: userId,
      matrix1: matrix1,
      matrix2: matrix2,
      threadsUsed: this.state.ThreadCount,
      library: localStorage.getItem("Library")
    };

    this.calcService.createCalculationMatrixMul(calcData).subscribe(
      response => {
        let result = "";
        for (let i = 0; i < response.data.size[0]; i++) {
          result += "[";
          for (let j = 0; j < response.data.size[1]; j++) {
            result += response.data.data[i][j] + ", "
          }
          result = result.slice(0, result.length - 2)
          result += "], ";
        }
        result = result.slice(0, result.length - 2)
        console.log(result);
        this.props.resultEventM.emit(result);
      },
      error => {
        this.props.resultEventM.emit(error.error.error);
      }
    );
  }

  matrixSystem() {
    const userId = localStorage.getItem('userId');

    const matrix1 = {
      rows: this.state.Arows,
      cols: this.state.Acols,
      data: this.state.Matrix1
    };
    const matrix2 = {
      rows: this.state.Brows,
      cols: this.state.Bcols,
      data: this.state.Matrix2
    };
    const calcData = {
      userId: userId,
      matrix1: matrix1,
      matrix2: matrix2,
      threadsUsed: this.state.ThreadCount,
      library: localStorage.getItem("Library")
    };

    this.calcService.createCalculationMatrixSystem(calcData).subscribe(
      response => {
        console.log(response.data);
        this.props.resultEventM.emit(response.data);
      },
      error => {
        this.props.resultEventM.emit(error.error.error);
      }
    );
  }

  clear(matrix) {
    const clearedMatrix = matrix.map(row => row.map(() => 0));
    this.setState({ Matrix1: clearedMatrix });
  }

  transpose(matrix, rows, cols) {
    const userId = localStorage.getItem('userId');

    const matrix1 = {
      rows: rows,
      cols: cols,
      data: matrix
    };
    const calcData = {
      userId: userId,
      matrix: matrix1,
      library: localStorage.getItem("Library")
    };

    this.calcService.createCalculationMatrixTranspose(calcData).subscribe(
      response => {
        let result = "";
        for (let i = 0; i < response.data.size[0]; i++) {
          result += "[";
          for (let j = 0; j < response.data.size[1]; j++) {
            result += response.data.data[i][j] + ", "
          }
          result = result.slice(0, result.length - 2)
          result += "], ";
        }
        result = result.slice(0, result.length - 2)
        console.log(result);
        this.props.resultEventM.emit(result);
      },
      error => {
        this.props.resultEventM.emit(error.error.error);
      }
    );
  }

  mulBy(matrix, rows, cols, num) {
    const userId = localStorage.getItem('userId');

    const matrix1 = {
      rows: rows,
      cols: cols,
      data: matrix
    };
    const calcData = {
      userId: userId,
      matrix:matrix1,
      number: num,
      library: localStorage.getItem("Library")
    };

    this.calcService.createCalculationMatrixMulByNum(calcData).subscribe(
      response => {
        let result = "";
        for (let i = 0; i < response.data.size[0]; i++) {
          result += "[";
          for (let j = 0; j < response.data.size[1]; j++) {
            result += response.data.data[i][j] + ", "
          }
          result = result.slice(0, result.length - 2)
          result += "], ";
        }
        result = result.slice(0, result.length - 2)
        console.log(result);
        this.props.resultEventM.emit(result);
      },
      error => {
        this.props.resultEventM.emit(error.error.error);
      }
    );
  }

  reverse(matrix, rows, cols) {
    const userId = localStorage.getItem('userId');

    const matrix1 = {
      rows: rows,
      cols: cols,
      data: matrix
    };
    const calcData = {
      userId: userId,
      matrix: matrix1,
      library: localStorage.getItem("Library")
    };

    this.calcService.createCalculationMatrixReverse(calcData).subscribe(
      response => {
        let result = "";
        for (let i = 0; i < response.data.size[0]; i++) {
          result += "[";
          for (let j = 0; j < response.data.size[1]; j++) {
            result += response.data.data[i][j] + ", "
          }
          result = result.slice(0, result.length - 2)
          result += "], ";
        }
        result = result.slice(0, result.length - 2)
        console.log(result);
        this.props.resultEventM.emit(result);
      },
      error => {
        this.props.resultEventM.emit(error.error.error);
      }
    );
  }

  determinant(matrix, rows, cols) {
    const userId = localStorage.getItem('userId');

    const matrix1 = {
      rows: rows,
      cols: cols,
      data: matrix
    };
    const calcData = {
      userId: userId,
      matrix: matrix1,
      library: localStorage.getItem("Library")
    };

    this.calcService.createCalculationMatrixDeterminant(calcData).subscribe(
      response => {
        console.log(response.data);
        this.props.resultEventM.emit(response.data);
      },
      error => {
        this.props.resultEventM.emit(error.error.error);
      }
    );
  };
  render() {

    return (
      <View style={styles.container}>
        <View style={[styles.inputContainer, { display: isInputFocused ? 'flex' : 'none' }]}>
          <View style={styles.matrixTable}>
            <View style={styles.matrixRow}>
              <TextInput
                style={styles.matrixInput}
                keyboardType="numeric"
                value={matrix1[0][0].toString()}
                onChangeText={(text) => {
                  const updatedMatrix = [...matrix1];
                  updatedMatrix[0][0] = Number(text);
                  setMatrix1(updatedMatrix);
                }}
                onFocus={onInputFocus}
                onBlur={onInputBlur}
              />
              <TextInput
                style={styles.matrixInput}
                keyboardType="numeric"
                value={matrix1[0][1].toString()}
                onChangeText={(text) => {
                  const updatedMatrix = [...matrix1];
                  updatedMatrix[0][1] = Number(text);
                  setMatrix1(updatedMatrix);
                }}
                onFocus={onInputFocus}
                onBlur={onInputBlur}
              />
              <TextInput
                style={styles.matrixInput}
                keyboardType="numeric"
                value={matrix1[0][2].toString()}
                onChangeText={(text) => {
                  const updatedMatrix = [...matrix1];
                  updatedMatrix[0][2] = Number(text);
                  setMatrix1(updatedMatrix);
                }}
                onFocus={onInputFocus}
                onBlur={onInputBlur}
              />
            </View>
            <View style={styles.matrixRow}>
              <TextInput
                style={styles.matrixInput}
                keyboardType="numeric"
                value={matrix1[1][0].toString()}
                onChangeText={(text) => {
                  const updatedMatrix = [...matrix1];
                  updatedMatrix[1][0] = Number(text);
                  setMatrix1(updatedMatrix);
                }}
                onFocus={onInputFocus}
                onBlur={onInputBlur}
              />
            </View>
            <View style={styles.matrixRow}>
              <TextInput
                style={styles.matrixInput}
                keyboardType="numeric"
                value={matrix1[2][0].toString()}
                onChangeText={(text) => {
                  const updatedMatrix = [...matrix1];
                  updatedMatrix[2][0] = Number(text);
                  setMatrix1(updatedMatrix);
                }}
                onFocus={onInputFocus}
                onBlur={onInputBlur}
              />
              <TextInput
                style={styles.matrixInput}
                keyboardType="numeric"
                value={matrix1[2][1].toString()}
                onChangeText={(text) => {
                  const updatedMatrix = [...matrix1];
                  updatedMatrix[2][1] = Number(text);
                  setMatrix1(updatedMatrix);
                }}
                onFocus={onInputFocus}
                onBlur={onInputBlur}
              />
            </View>
            {/* Add more rows here */}
          </View>
          <View style={styles.matrixTable}>
            <View style={styles.matrixRow}>
              <Button title="Clear" onPress={() => clear(matrix1, setMatrix1)} />
              <View style={styles.selectContainer}>
                <Text style={styles.selectLabel}>Size:</Text>
                <TextInput
                  style={styles.selectInput}
                  keyboardType="numeric"
                  value={Arows.toString()}
                  onChangeText={(text) => setArows(Number(text))}
                  onFocus={onInputFocus}
                  onBlur={onInputBlur}
                />
                <Text style={styles.selectLabel}>x</Text>
                <TextInput
                  style={styles.selectInput}
                  keyboardType="numeric"
                  value={Acols.toString()}
                  onChangeText={(text) => setAcols(Number(text))}
                  onFocus={onInputFocus}
                  onBlur={onInputBlur}
                />
              </View>
            </View>
            <View style={styles.matrixRow}>
              <Button title="Transpose" onPress={() => transpose(matrix1, setMatrix1, Arows, Acols)} />
              <View style={styles.buttonContainer}>
                <Button style={styles.button} title="Multiply by" onPress={() => mulBy(matrix1, setMatrix1, Arows, Acols, MulByA)} />
                <TextInput
                  style={styles.matrixInput}
                  keyboardType="numeric"
                  value={MulByA.toString()}
                  onChangeText={(text) => setMulByA(Number(text))}
                  onFocus={onInputFocus}
                  onBlur={onInputBlur}
                />
              </View>
            </View>
            <View style={styles.matrixRow}>
              <Button style={styles.button} title="Determinant" onPress={() => determinant(matrix1, Arows, Acols)} />
              <Button style={styles.button} title="Reversed" onPress={() => reverse(matrix1, setMatrix1, Arows, Acols)} />
            </View>
          </View>
        </View>
        <View style={styles.inputContainer}>
          <View style={styles.matrixTable}>
            <View style={styles.matrixRow}>
              <TextInput
                style={styles.matrixInput}
                keyboardType="numeric"
                value={matrix2[0][0].toString()}
                onChangeText={(text) => {
                  const updatedMatrix = [...matrix2];
                  updatedMatrix[0][0] = Number(text);
                  setMatrix2(updatedMatrix);
                }}
                onFocus={onInputFocus}
                onBlur={onInputBlur}
              />
              <TextInput
                style={styles.matrixInput}
                keyboardType="numeric"
                value={matrix2[0][1].toString()}
                onChangeText={(text) => {
                  const updatedMatrix = [...matrix2];
                  updatedMatrix[0][1] = Number(text);
                  setMatrix2(updatedMatrix);
                }}
                onFocus={onInputFocus}
                onBlur={onInputBlur}
              />
              <TextInput
                style={styles.matrixInput}
                keyboardType="numeric"
                value={matrix2[0][2].toString()}
                onChangeText={(text) => {
                  const updatedMatrix = [...matrix2];
                  updatedMatrix[0][2] = Number(text);
                  setMatrix2(updatedMatrix);
                }}
                onFocus={onInputFocus}
                onBlur={onInputBlur}
              />
            </View>
            <View style={styles.matrixRow}>
              <TextInput
                style={styles.matrixInput}
                keyboardType="numeric"
                value={matrix2[1][0].toString()}
                onChangeText={(text) => {
                  const updatedMatrix = [...matrix2];
                  updatedMatrix[1][0] = Number(text);
                  setMatrix2(updatedMatrix);
                }}
                onFocus={onInputFocus}
                onBlur={onInputBlur}
              />
            </View>
            <View style={styles.matrixRow}>
              <TextInput
                style={styles.matrixInput}
                keyboardType="numeric"
                value={matrix2[2][0].toString()}
                onChangeText={(text) => {
                  const updatedMatrix = [...matrix2];
                  updatedMatrix[2][0] = Number(text);
                  setMatrix2(updatedMatrix);
                }}
                onFocus={onInputFocus}
                onBlur={onInputBlur}
              />
              <TextInput
                style={styles.matrixInput}
                keyboardType="numeric"
                value={matrix2[2][1].toString()}
                onChangeText={(text) => {
                  const updatedMatrix = [...matrix2];
                  updatedMatrix[2][1] = Number(text);
                  setMatrix2(updatedMatrix);
                }}
                onFocus={onInputFocus}
                onBlur={onInputBlur}
              />
            </View>
            {/* Add more rows here */}
          </View>
          <View style={styles.matrixTable}>
            <View style={styles.matrixRow}>
              <Button title="Clear" onPress={() => clear(matrix2, setMatrix2)} />
              <View style={styles.selectContainer}>
                <Text style={styles.selectLabel}>Size:</Text>
                <TextInput
                  style={styles.selectInput}
                  keyboardType="numeric"
                  value={Brows.toString()}
                  onChangeText={(text) => setBrows(Number(text))}
                  onFocus={onInputFocus}
                  onBlur={onInputBlur}
                />
                <Text style={styles.selectLabel}>x</Text>
                <TextInput
                  style={styles.selectInput}
                  keyboardType="numeric"
                  value={Bcols.toString()}
                  onChangeText={(text) => setBcols(Number(text))}
                  onFocus={onInputFocus}
                  onBlur={onInputBlur}
                />
              </View>
            </View>
            <View style={styles.matrixRow}>
              <Button title="Transpose" onPress={() => transpose(matrix2, setMatrix2, Brows, Bcols)} />
              <View style={styles.buttonContainer}>
                <Button style={styles.button} title="Multiply by" onPress={() => mulBy(matrix2, setMatrix2, Brows, Bcols, MulByB)} />
                <TextInput
                  style={styles.matrixInput}
                  keyboardType="numeric"
                  value={MulByB.toString()}
                  onChangeText={(text) => setMulByB(Number(text))}
                  onFocus={onInputFocus}
                  onBlur={onInputBlur}
                />
              </View>
            </View>
            <View style={styles.matrixRow}>
              <Button style={styles.button} title="Determinant" onPress={() => determinant(matrix2, Brows, Bcols)} />
              <Button style={styles.button} title="Reversed" onPress={() => reverse(matrix2, setMatrix2, Brows, Bcols)} />
            </View>
          </View>
        </View>
        <View style={styles.matrixTable}>
          <View style={styles.matrixRow}>
          <Button title="A + B" onPress={() => addMatrices(matrix1, matrix2, Arows, Acols, Brows, Bcols)} />
          <Button title="A - B" onPress={() => subtractMatrices(matrix1, matrix2, Arows, Acols, Brows, Bcols)} />
        </View>
        <View style={styles.matrixRow}>
          <Button title="B - A" onPress={() => subtractMatrices(matrix2, matrix1, Brows, Bcols, Arows, Acols)} />
          <Button title="A * B" onPress={() => multiplyMatrices(matrix1, matrix2, Arows, Acols, Brows, Bcols)} />
        </View>
      </View>
    </View>
  
  
     );
  }

}
const styles = {
  inputContainer: {
    flexDirection: 'row',
    alignItems: 'center',
    borderWidth: 2,
    borderColor: 'orange',
    borderRadius: 4,
    padding: 10,
    width: '100%',
    marginTop: 0,
    marginBottom: 0,
  },
  mathInputContainer: {
    flexDirection: 'row',
    alignItems: 'center',
    padding: 10,
    width: '100%',
    marginTop: 0,
    marginBottom: 0,
    backgroundColor: 'orange',
  },
  input: {
    flex: 1,
    borderWidth: 0,
    outlineWidth: 0,
  },
  btn: {
    minHeight: 36,
    minWidth: 36,
    padding: 8,
    marginLeft: 4,
    borderWidth: 0,
    borderRadius: 4,
    marginBottom: 0,
    cursor: 'pointer',
  },
  btnChoice: {
    minHeight: 64,
    minWidth: 96,
    padding: 8,
    marginLeft: 16,
    borderRadius: 16,
    cursor: 'pointer',
    marginBottom: 0,
    fontSize: 'larger',
    backgroundColor: 'white',
    borderColor: 'orange',
  },
  orangeBg: {
    backgroundColor: 'orange',
    color: 'white',
  },
  grayText: {
    color: 'gray',
  },
  resultContainer: {
    marginTop: 10,
  },
  matrixEl: {
    maxWidth: 30,
  },
  matrixElHidden: {
    maxWidth: 30,
    display: 'none',
  },
};
export default MatrixComponent; 

    