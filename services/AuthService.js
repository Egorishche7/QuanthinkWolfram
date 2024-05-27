import axios from 'axios';

const baseUrl = 'http://localhost:8080';

export function registerUser(userDetails) {
  return axios.post(`${baseUrl}/register`, userDetails);
}

export function login(authRequest) {
  return axios.post(`${baseUrl}/login`, authRequest);
}

export function logout() {
  return axios.post(`${baseUrl}/logout`);
}