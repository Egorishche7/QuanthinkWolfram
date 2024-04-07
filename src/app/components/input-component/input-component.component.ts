import { Component } from '@angular/core';

@Component({
  selector: 'app-input',
  templateUrl: './input-component.component.html',
  styleUrls: ['./input-component.component.css']
})
export class InputComponent {
  inputValue: string = '';
  isInputFocused: boolean = false;

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
  }
}
