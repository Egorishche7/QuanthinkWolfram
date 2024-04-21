import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';
import translationsBy from 'src/locale/translations.by.json';
import translationsEn from 'src/locale/translations.en.json';
import translationsRu from 'src/locale/translations.ru.json';

@Injectable({
  providedIn: 'root'
})
export class LanguageService {
  private defaultLanguage: string = 'en';
  private translations: { [key: string]: { [key: string]: string } } = {
    'en': translationsEn,
    'ru': translationsRu,
    'by': translationsBy
  };

  languageChanged: Subject<void> = new Subject<void>();
public selectedLanguageChanged: Subject<string> = new Subject<string>();
  constructor() { }

  public getTranslation(key: string): string {
    const selectedLanguage = this.getLanguage();
    return this.translations[selectedLanguage][key] || key;
  }

  public getLanguage(): string {
    const storedLanguage = localStorage.getItem('selectedLanguage');
    return storedLanguage || this.defaultLanguage;
  }

  public setLanguage(language: string): void {
    localStorage.setItem('selectedLanguage', language);
    this.languageChanged.next(); // Уведомить другие компоненты о смене языка
  }
}