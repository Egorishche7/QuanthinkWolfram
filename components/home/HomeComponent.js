import React, { useState, useEffect } from 'react';
import { View, Button, Text, Image, TouchableOpacity, StyleSheet } from 'react-native';
import { useNavigation } from '@react-navigation/native';
import LanguageService from '../../services/LanguageService';
import InputComponent from '../input-component/InputComponent';
import AsyncStorage from '@react-native-async-storage/async-storage';

function HomeComponent() {
  const [showLogin, setShowLogin] = useState(false);
  const [selectedLibrary, setSelectedLibrary] = useState('JAVA');
  const [showLibraryMenu, setShowLibraryMenu] = useState(false);
  const [selectedCalculations, setSelectedCalculations] = useState('BA');
  const [selectedLanguage, setSelectedLanguage] = useState(LanguageService.defaultLanguage);
  const [showLanguageMenu, setShowLanguageMenu] = useState(false);
  const navigation = useNavigation();

  useEffect(() => {
    const loadLanguage = async () => {
      const language = await LanguageService.getLanguage();
      setSelectedLanguage(language);
    };
    loadLanguage();
  }, []);

  const handleLogout = async () => {
    await AsyncStorage.clear();
    setShowLogin(false);
    navigation.navigate('Login');
  };

  const toggleLanguageMenu = () => setShowLanguageMenu(!showLanguageMenu);

  const toggleLibraryMenu = () => setShowLibraryMenu(!showLibraryMenu);

  const changeLibrary = (library) => {
    setSelectedLibrary(library);
    setShowLibraryMenu(false);
  };

  const changeLanguage = async (language) => {
    await LanguageService.setLanguage(language);
    setSelectedLanguage(language);
    setShowLanguageMenu(false);
  };

  const goToChat = () => {
    navigation.navigate('Chat');
  };

  const goToLogin = () => {
    navigation.navigate('Login');
  };

  const getTranslation = (key) => {
    return LanguageService.translations[selectedLanguage][key] || key;
  };

  return (
    <View style={styles.container}>
      <View style={styles.header}>
        <View style={styles.iconContainer}>
          <Image source={require('../../assets/icons/5.png')} style={styles.logoImage} />
        </View>
        <View style={styles.SignContainer}>
          <Button title={getTranslation('Sign in')} onPress={goToLogin} />
        </View>
      </View>

      <View style={styles.languageContainer}>
        <TouchableOpacity onPress={toggleLanguageMenu} style={styles.languageButton}>
          <Image source={require('../../assets/icons/change_language.png')} style={styles.languageImage} />
        </TouchableOpacity>
        {showLanguageMenu && (
          <View style={styles.languageMenu}>
            <Button title={getTranslation('Русский')} onPress={() => changeLanguage('ru')} />
            <Button title={getTranslation('Белорусский')} onPress={() => changeLanguage('by')} />
            <Button title={getTranslation('English')} onPress={() => changeLanguage('en')} />
          </View>
        )}
      </View>

      <View style={styles.contentContainer}>
        <InputComponent selectedCalculations={selectedCalculations} />
      </View>

      <View style={styles.LowButtonsContainer}>
        <Button title={getTranslation('Basic arithmetics')} onPress={() => setSelectedCalculations('BA')} />
      </View>
      <View style={styles.LowButtonsContainer}>
        <Button title={getTranslation('Equations')} onPress={() => setSelectedCalculations('Eq')} />
      </View>
      <View style={styles.LowButtonsContainer}>
        <Button title={getTranslation('Matrix')} onPress={() => setSelectedCalculations('M')} />
      </View>

      <View style={styles.footer}>
        <View style={styles.libraries}>
          <Text style={styles.purpleText} onPress={toggleLibraryMenu}>
            {getTranslation('Choose your library')} &gt;
          </Text>
          {showLibraryMenu && (
            <View>
              <Button title="Java" onPress={() => changeLibrary('JAVA')} />
              <Button title="C++" onPress={() => changeLibrary('C_PLUS_PLUS')} />
            </View>
          )}
          <Text style={styles.greenText} onPress={() => console.log('Toggle history')}>
            {getTranslation('History')} &gt;
          </Text>
          <Text style={styles.turquoiseText} onPress={goToChat}>
            {getTranslation('Online Chat')} &gt;
          </Text>
        </View>
      </View>

      <View style={styles.bottomText}>
  <Text style={styles.grayText}>© {getTranslation('Resources & Tools About Contact Connect')}</Text>
  <View style={styles.bottomImagesWrapper}>
    <Image source={require('../../assets/icons/facebook.png')} style={styles.bottomImage} />
    <Image source={require('../../assets/icons/twitter.png')} style={styles.bottomImage} />
    <Image source={require('../../assets/icons/instagram.png')} style={styles.bottomImage} />
    <Image source={require('../../assets/icons/telegram.png')} style={styles.bottomImage} />
  </View>
</View>
<View style={styles.bottomText_2}>
  <Text style={styles.grayText}>©2024 QuanThink Wolfram Terms Privacy</Text>
</View>
    </View>
  );
}

const styles = {
  container: {
    flex: 1,
    justifyContent: 'space-between',
    alignItems: 'center',
    padding: 10,
  },
  header: {
    position: 'absolute',
    top: 30,
    right: 10,
  },
  iconContainer: {
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'flex-end',
  },

  SignContainer: {
    position: 'absolute',
    top: -30,
    right: 70,
  },
  signInButton: {
    backgroundColor: '#ffa500',
    color: 'white',
    right: 100,
    top: -20,
    
  },
  logoImage: {
    width: 400,
    height: 200,
    top: 10,
    objectFit: 'contain',
  },
  languageContainer: {
    position: 'absolute',
    top: -10,
    right: 0,
  },
  languageButton: {
    alignItems: 'center',
    backgroundColor: 'transparent',
    borderWidth: 0,
    borderColor: 'transparent',
    top: 0,
  },
  languageImage: {
    width: 60,
    height: 60,
    objectFit: 'contain',
  },

  footer: {
    
    flexDirection: 'row',
    justifyContent: 'flex-end',
    alignItems: 'center',
  },
  libraries: {
    bottom: 35,
    marginBottom: 25,
    flexDirection: 'row',
    marginRight: -5,
    alignItems: 'center',
  },
  purpleText: {
    color: 'purple',
    marginRight: 8,
    fontSize: 13,
  },
  greenText: {
    color: 'green',
    marginRight: 15,
    fontSize: 13,
  },
  yellowText: {
    color: 'yellow',
    marginRight: 15,
    fontSize: 13,
  },
  turquoiseText: {
    color: 'turquoise',
    marginRight: 15,
    fontSize: 13,
  },
  bottomText: {
    position: 'absolute',
    bottom: 30,
    left: 10,
    right: 0,
    padding: 10,
    justifyContent: 'space-between',
    alignItems: 'center',
    flexDirection: 'row',
  },
  bottomText_2: {
    position: 'absolute',
    bottom: 0,
    left: 90,
    right: 50,
    padding: 10,
    justifyContent: 'space-between',
    alignItems: 'center',
    flexDirection: 'row',
    
  },
  grayText: {
    color: 'gray',
    fontSize: 8,
  },
  bottomImagesWrapper: {
    flexDirection: 'row',
    alignItems: 'center',
   
  },
  bottomImage: {
    width: 10,
    height: 10,
    marginHorizontal: 5,
   
    right: 55,
    alignItems: 'center',
    flexDirection: 'row',
  },
  LowButtonsContainer_basic:
{
  width: 400,
    height: 70,
    bottom: 70,
    alignItems: 'center',
    marginBottom: -50,
},
  LowButtonsContainer:
{
  width: 400,
    height: 50,
    bottom: 80,
    
    marginBottom: -100,
    
}
};
export default HomeComponent;