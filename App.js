import { useNetInfo } from "@react-native-community/netinfo";
import { useEffect } from 'react';
import { LogBox, Alert } from 'react-native';
LogBox.ignoreLogs(["AsyncStorage has been extracted from"]);

// import the screens
import Start from './components/Start';
import Chat from './components/Chat';
import LoginComponent from './components/login/LoginComponent';
import HomeComponent from './components/home/HomeComponent';
import RegisterComponent from './components/register/RegisterComponent';
// import Firestore
import { initializeApp } from "firebase/app";
import { getFirestore, disableNetwork, enableNetwork } from "firebase/firestore";
import { getStorage } from "firebase/storage";


// import react Navigation
import { NavigationContainer } from '@react-navigation/native';
import { createNativeStackNavigator } from '@react-navigation/native-stack';

// Create the navigator
const Stack = createNativeStackNavigator();

const App = () => {

  const connectionStatus = useNetInfo();

// Your web app's Firebase configuration
const firebaseConfig = {
  apiKey: "AIzaSyDfaqbiMpHnbKrgJUwS6d1mxBDS6FeLCkc",
  authDomain: "chat-app-75449.firebaseapp.com",
  projectId: "chat-app-75449",
  storageBucket: "chat-app-75449.appspot.com",
  messagingSenderId: "567968191664",
  appId: "1:567968191664:web:9cb4f3d120c9792cc69775"
};

  // Initialize Firebase
  const app = initializeApp(firebaseConfig);

  // Initialize Cloud Firestore/Storage and get a reference to the service
  const db = getFirestore(app);
  const storage = getStorage(app);

  // Network connectivity status
  useEffect(() => {
    if (connectionStatus.isConnected === false) {
      Alert.alert("Connection Lost!");
      disableNetwork(db);
    } else if (connectionStatus.isConnected === true) {
      enableNetwork(db);
    }
  }, [connectionStatus.isConnected]);


  return(
    <NavigationContainer>
      <Stack.Navigator
        initialRouteName="Start"
      >
        <Stack.Screen
          name="QuanthinkWolfram-app"
          component={HomeComponent}
        />
        <Stack.Screen
          name="Login"
        
        >
          {props => <LoginComponent 
            />}
        </Stack.Screen>
        <Stack.Screen
          name="Home"
        
        >
          {props => <HomeComponent 
            />}
        </Stack.Screen>

        <Stack.Screen
          name="Register"
        
        >
          {props => <RegisterComponent 
            />}
             </Stack.Screen>
      </Stack.Navigator>

    </NavigationContainer>


  );
}


export default App;