import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-input',
  templateUrl: './input-component.component.html',
  styleUrls: ['./input-component.component.css']
})
export class InputComponent {
  inputValue: string = '';
  isInputFocused: boolean = false;
  calculationResult: string = '';

  constructor(private http: HttpClient) {}

  onInputFocus() {
    this.isInputFocused = true;
  }

  onInputBlur() {
    this.isInputFocused = false;
  }

  addCharacter(character: string) {
    this.inputValue += character;
  }

  deleteLastCharacter() {
    this.inputValue = this.inputValue.slice(0, -1);
  }

  clearInput() {
    this.inputValue = '';
  }

  calculate() {
    const url = 'http://localhost:8080/calculations';

    this.http.post(url, { expr: this.inputValue })
      .subscribe(
        (response: any) => {
          this.calculationResult = response.result;
        },
        (error: any) => {
          console.error('Error:', error);
        }
      );
  }
}