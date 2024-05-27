import { Component } from '@angular/core';
import { CalculationService } from "../../services/calc.service";
import { Router } from "@angular/router";
import { LanguageService } from '../../services/language.service';
@Component({
  selector: 'app-history',
  templateUrl: './history.component.html',
  styleUrls: ['./history.component.css']
})
export class HistoryComponent {
  selectedCalculations: string = "BA";
  inputValue: string = '';
  isInputFocused: boolean = false;
  calculationResult: string | undefined;
  calculationTime: number | undefined;
  
inputError: string = '';
  constructor(
    private calcService: CalculationService,
    private router: Router,
    private languageService: LanguageService
  ) { }
 ngOnInit() {
    this.languageService.languageChanged.subscribe(() => {
      this.languageChangedCallback();
    });
    const userId: string = localStorage.getItem('userId') as string;
    var userIdNum = parseInt(userId);
    console.log(userId)
    var myHeaders =  new Headers()
    myHeaders.append('Authorization', 'Bearer ' + localStorage.getItem('token'));
    this.calcService.createCalculationHistory(userIdNum, myHeaders).subscribe(
      response => {
        console.log(response);
        let list = document.getElementById("list")!;
        for (let i = response.data.length - 1; i >= 0; i--) {
            let li = document.createElement('li');
            li.setAttribute('style', 'color: white; \
            font-size: 16px;n \
            margin-bottom: 10px; \
            margin-top: 10px; \
            padding: 10px; \
            border: 1px solid orange; \
            background-color: orange; \
            margin-left: 10px;\
            margin-right: 10px;\
            border-radius: 5px; \
            list-style-type: none; \
            width: 90%; \
            overflow-x: auto; \
            ');
            li.innerHTML = this.getTranslation('Date') + ": "+ response.data[i].date +
             "<br>" +this.getTranslation('Lib')+ ": "+ response.data[i].library  +
               "<br>" +this.getTranslation('inputData')+ ": " + response.data[i].inputData +
               "<br>" +this.getTranslation('Result')+ ": " + response.data[i].result +
             "<br>" +this.getTranslation('Time')+ ": " + (response.data[i].time / 1000) +" "+ this.getTranslation('Sec') +
              "<br>" +this.getTranslation('Thread')+ ": " + response.data[i].threadsUsed +
              "<br>" + this.getTranslation('calcType') + ": ";
            switch(response.data[i].type){
              case 'BASIC_ARITHMETIC':
                li.innerHTML += this.getTranslation('BASIC_ARITHMETIC');
              break;
              case 'EQUATION':
                li.innerHTML += this.getTranslation('EQUATION');
              break;
              case 'MATRIX_SUM':
                li.innerHTML += this.getTranslation('MATRIX_SUM');
              break;
              case 'MATRIX_SUB':
                li.innerHTML += this.getTranslation('MATRIX_SUB');
              break;
              case 'MATRIX_MUL':
                li.innerHTML += this.getTranslation('MATRIX_MUL');
              break;
              case 'MATRIX_MUL_BY_NUM':
                li.innerHTML += this.getTranslation('MATRIX_MUL_BY_NUM');
              break;
              case 'MATRIX_TRANSPOSE':
                li.innerHTML += this.getTranslation('MATRIX_TRANSPOSE');
              break;
              case 'MATRIX_REVERSE':
                li.innerHTML += this.getTranslation('MATRIX_REVERSE');
              break;
              case 'MATRIX_DETERMINANT':
                li.innerHTML += this.getTranslation('MATRIX_DETERMINANT');
              break;
              case 'MATRIX_SYSTEM':
                li.innerHTML += this.getTranslation('MATRIX_SYSTEM');
              break;
            }
            list.appendChild(li);
        }
      },
      error => {
        console.log(error.error.error);        
      }
    )
  }

  languageChangedCallback() {
    this.inputError = '';
  }

  getTranslation(key: string): string {
    return this.languageService.getTranslation(key);
  }

  isLoggedIn(): boolean {
    return !!localStorage.getItem('email');
  }
}
