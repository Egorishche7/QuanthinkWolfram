import axios from 'axios';

const apiUrl = 'http://localhost:8080/calculations/';

class CalculationService {
  constructor(apiUrl) {
    this.apiUrl = apiUrl; // Сохранение apiUrl как свойство класса
  }
  createCalculationBasicArithmetic(calculationDetails) {
    return axios.post(apiUrl + 'basicArithmetic', calculationDetails);
  }

  createCalculationEquation(calculationDetails) {
    return axios.post(apiUrl + 'equation', calculationDetails);
  }

  createCalculationMatrixSub(calculationDetails) {
    return axios.post(apiUrl + 'matrixSub', calculationDetails);
  }

  createCalculationMatrixMul(calculationDetails) {
    return axios.post(apiUrl + 'matrixMul', calculationDetails);
  }

  createCalculationMatrixMulByNum(calculationDetails) {
    return axios.post(apiUrl + 'matrixMulByNum', calculationDetails);
  }

  createCalculationMatrixTranspose(calculationDetails) {
    return axios.post(apiUrl + 'matrixTranspose', calculationDetails);
  }

  createCalculationMatrixReverse(calculationDetails) {
    return axios.post(apiUrl + 'matrixReverse', calculationDetails);
  }

  createCalculationMatrixDeterminant(calculationDetails) {
    return axios.post(apiUrl + 'matrixDeterminant', calculationDetails);
  }

  createCalculationMatrixSystem(calculationDetails) {
    return axios.post(apiUrl + 'matrixSystem', calculationDetails);
  }

  createCalculationMatrixSum(calculationDetails) {
    return axios.post(apiUrl + 'matrixSum', calculationDetails);
  }
}

export default CalculationService;