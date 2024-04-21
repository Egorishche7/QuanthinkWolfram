import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class LanguageService {
  private defaultLanguage: string = 'en'; // Здесь задайте язык по умолчанию

  constructor() { }

  public getLanguage(): string {
    const storedLanguage = localStorage.getItem('selectedLanguage');
    return storedLanguage || this.defaultLanguage;
  }

  public setLanguage(language: string): void {
    localStorage.setItem('selectedLanguage', language);
    location.reload(); // Перезагрузка страницы для применения изменений
  }
}