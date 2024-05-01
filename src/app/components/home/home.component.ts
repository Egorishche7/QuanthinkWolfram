import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { LanguageService } from '../../services/language.service';
import { Subscription } from 'rxjs';
import { EventEmitter } from '@angular/core';
import {AuthService} from "../../services/auth.service";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  selectedLanguage: string;
  showLanguageMenu: boolean = false;
  languageChanged: EventEmitter<string> = new EventEmitter<string>();
  private languageSubscription: Subscription | undefined;

  constructor(private router: Router, private languageService: LanguageService, private authService: AuthService) {
    this.selectedLanguage = this.languageService.getLanguage();
    this.authService = authService;
  }

  ngOnInit() {
    this.languageSubscription = this.languageService.selectedLanguageChanged.subscribe(() => {
      this.selectedLanguage = this.languageService.getLanguage();
    });
  }

  changeLanguage(language: string): void {
    this.languageService.setLanguage(language);
    this.languageService.selectedLanguageChanged.next(language);
  }

  ngOnDestroy() {
    this.languageSubscription?.unsubscribe();
  }

  logOut() {
    localStorage.clear();
    // this.authService.logout().subscribe(
    //   response=>{
    //     console.log(response);
    //   }
    // );
    this.router.navigate(['/login']);
  }

  toggleLanguageMenu(): void {
    this.showLanguageMenu = !this.showLanguageMenu;
  }

  isLoggedIn(): boolean {
    return !!sessionStorage.getItem('email');
  }

  getTranslation(key: string): string {
    return this.languageService.getTranslation(key);
  }

  goToChat(): void {
    this.router.navigate(['/chat']);
  }
}
