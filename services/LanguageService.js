import { AsyncStorage } from 'react-native';
import { Subject } from 'rxjs';

const LanguageService = {
  defaultLanguage: 'en',
  translations: {
    en: require('../locale/translations.en.json'),
    ru: require('../locale/translations.ru.json'),
    by: require('../locale/translations.by.json')
  },

  getTranslation: async function (key) {
    const selectedLanguage = await this.getLanguage();
    const translation = this.translations[selectedLanguage][key] || key;
    return translation;
  },

  getLanguage: async function () {
    try {
      const storedLanguage = await AsyncStorage.getItem('SelectedLanguage');
      const language = storedLanguage || this.defaultLanguage;
      return language;
    } catch (error) {
      console.log('Error getting language from AsyncStorage:', error);
      return this.defaultLanguage;
    }
  },

  setLanguage: async function (language) {
    try {
      await AsyncStorage.setItem('SelectedLanguage', language);
    } catch (error) {
      console.log('Error setting language in AsyncStorage:', error);
    }
  },
};

export default LanguageService;