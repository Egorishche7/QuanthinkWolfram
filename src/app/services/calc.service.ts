import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class CalculationService {
  private apiUrl = 'http://localhost:8080/calculations';

  constructor(private http: HttpClient) { }

  createCalculation(expression: string) {
    return this.http.post(this.apiUrl, { expr: expression });
  }
}