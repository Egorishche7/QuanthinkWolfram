import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Calculation } from "src/app/interfaces/calculation";
import { Observable } from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class CalculationService {
  private apiUrl = 'http://localhost:8080/calculations';

  constructor(private http: HttpClient) { }

  createCalculation(calculationDetails: Calculation): Observable<any> {
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Authorization': 'Bearer ' + localStorage.getItem('token') 
      })
    };
    return this.http.post(this.apiUrl, calculationDetails, httpOptions);
  }
}