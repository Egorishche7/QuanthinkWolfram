import { Component } from '@angular/core';
import { CalculationService } from 'src/app/services/calc.service';

@Component({
  selector: 'app-input',
  templateUrl: './input-component.component.html',
  styleUrls: ['./input-component.component.css']
})
export class InputComponent {
  inputValue: string = '';
  isInputFocused: boolean = false;
  calculationResult: string = '';

  constructor(private calculationService: CalculationService) { }

  onInputFocus() {
    this.isInputFocused = true;
  }

  onInputBlur() {
    this.isInputFocused = false;
  }

  addCharacter(character: string) {
    this.inputValue += character;
    console.log('Character added:', character);
  }

  deleteLastCharacter() {
    this.inputValue = this.inputValue.slice(0, -1);
  }

  clearInput() {
    this.inputValue = '';
  }

  calculate() {
    this.calculationService.createCalculation(this.inputValue)
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
