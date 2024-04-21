import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { LanguageService } from '../../services/language.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent {
  selectedLanguage: string;
  showLanguageMenu: boolean = false;

  constructor(private router: Router, private languageService: LanguageService) {
    this.selectedLanguage = this.languageService.getLanguage();
  }

  logOut() {
    sessionStorage.clear();
    this.router.navigate(['/login']);
  }

  toggleLanguageMenu(): void {
    this.showLanguageMenu = !this.showLanguageMenu;
  }

  changeLanguage(language: string): void {
    this.languageService.setLanguage(language);
  }

  isLoggedIn(): boolean {
    return !!sessionStorage.getItem('email');
  }
}