import React from 'react';
import { View, Button, Image, TouchableOpacity, Text } from 'react-native';
import { useNavigation } from '@react-navigation/native';
import { logout } from '../../services/AuthService';

export default function HomeComponent() {
  const navigation = useNavigation();

  const logOut = () => {
    logout();
    navigation.navigate('Login');
  };

  const toggleLanguageMenu = () => {
    // Implement toggleLanguageMenu functionality
  };

  const isLoggedIn = () => {
    // Implement isLoggedIn functionality
  };

  const goToChat = () => {
    navigation.navigate('Chat');
  };

  const toggleLibraryMenu = () => {
    // Implement toggleLibraryMenu functionality
  };

  return (
    <View style={styles.container}>
      <Image source={require('../../assets/icons/5.png')} style={styles.logoImage} />
      <View style={styles.header}>
      {isLoggedIn() ? (
            <Button title="Sign out" onPress={logOut} style={styles.signInButton} />
          ) : (
            <Button title="Sign in" onPress={logOut} style={styles.signInButton} />
          )}
        <View style={styles.iconContainer}>
          
          
        </View>
      </View>
      <View style={styles.languageContainer}>
        <TouchableOpacity onPress={toggleLanguageMenu} style={styles.languageButton}>
          <Image source={require('../../assets/icons/change_language.png')} style={styles.languageImage} />
        </TouchableOpacity>
        {/* Implement language menu */}
      </View>
      <View style={styles.centerContent}>
        {/* Implement app input component */}
      </View>
      <View style={styles.footer}>
        <View style={styles.libraries}>
          {/* Implement library switch */}
        </View>
        <Text style={styles.purpleText} onPress={toggleLibraryMenu}>
          Choose your library &gt;
        </Text>
        {/* Implement other menu items */}
        <Text style={styles.greenText}>History &gt;</Text>
        <Text style={styles.yellowText}>Settings &gt;</Text>
        <Text style={styles.turquoiseText} onPress={goToChat}>
          Online Chat &gt;
        </Text>
      </View>
      <View style={styles.bottomText}>
        <Text style={styles.grayText}>© Resources & Tools About Contact Connect</Text>
        <View style={{ flexDirection: 'row' }}>
          <Image source={require('../../assets/icons/facebook.png')} style={styles.bottomImage} />
          <Image source={require('../../assets/icons/twitter.png')} style={styles.bottomImage} />
          <Image source={require('../../assets/icons/telegram.png')} style={styles.bottomImage} />
          <Image source={require('../../assets/icons/instagram.png')} style={styles.bottomImage} />
        </View>
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
    right: 60,
  },
  iconContainer: {
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'flex-end',
  },
  signInButton: {
    backgroundColor: '#ffa500',
    color: 'white',
    right: 100,
  },
  logoImage: {
    width: 400,
    height: 200,
    top: 100,
    objectFit: 'contain',
  },
  languageContainer: {
    position: 'absolute',
    top: 20,
    right: 0,
  },
  languageButton: {
    alignItems: 'center',
    backgroundColor: 'transparent',
    borderWidth: 0,
    borderColor: 'transparent',
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
    marginRight: 20,
  },
  purpleText: {
    color: 'purple',
    marginRight: 20,
    fontSize: 20,
  },
  greenText: {
    color: 'green',
    marginRight: 20,
    fontSize: 20,
  },
  yellowText: {
    color: 'yellow',
    marginRight: 20,
    fontSize: 20,
  },
  turquoiseText: {
    color: 'turquoise',
    marginRight: 20,
    fontSize: 20,
  },
  bottomText: {
    position: 'absolute',
    bottom: 200,
    left: 0,
    right: 0,

    padding: 10,
    justifyContent: 'space-between',
    alignItems: 'center',
    flexDirection: 'row',
  },
  grayText: {
    color: 'gray',
  },
  bottomImage: {
    width: 30,
    height: 30,
    marginHorizontal: 5,
  },
};