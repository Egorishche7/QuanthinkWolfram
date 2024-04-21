import { Component } from '@angular/core';
import { CalculationService } from "../../services/calc.service";
import { Calculation } from "../../interfaces/calculation";
import { Router } from "@angular/router";

@Component({
  selector: 'app-input',
  templateUrl: './input-component.component.html',
  styleUrls: ['./input-component.component.css']
})
export class InputComponent {
  inputValue: string = '';
  isInputFocused: boolean = false;
  calculationResult: string | undefined;

  constructor(
    private calcService: CalculationService,
    private router: Router
  ) { }

  onInputFocus() {
    this.isInputFocused = true;
  }

  onInputBlur() {
    setTimeout(() => {
      this.isInputFocused = false;
    }, 200);
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
    const userId = localStorage.getItem('userId'); // Изменено на localStorage

    if (!userId) {
      this.router.navigate(['login']);
      return;
    }

    const calcData: Calculation = {
      userId: userId,
      type: 'basic_calculation',
      expression: this.inputValue,
      threadsUsed: "1"
    };

    this.calcService.createCalculation(calcData as Calculation).subscribe(
      response => {
        console.log(response);
        this.calculationResult = response.result;
      }
    );
  }
}