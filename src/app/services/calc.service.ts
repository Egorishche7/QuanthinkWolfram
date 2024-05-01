import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Calculation } from "src/app/interfaces/calculation";
import { Observable } from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class CalculationService {
  private apiUrl = 'http://localhost:8080/calculations/basicArithmetic';

  constructor(private http: HttpClient) { }

  createCalculation(calculationDetails: Calculation): Observable<any> {
    return this.http.post(this.apiUrl, calculationDetails);
  }
}
