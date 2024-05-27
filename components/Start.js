import { useState } from 'react';
import { getAuth, signInAnonymously } from "firebase/auth";
import {
  StyleSheet, 
  View,  
  Text, 
  Alert,
  TextInput, 
  ImageBackground,
  KeyboardAvoidingView,
  Platform,
  TouchableOpacity, 
} from 'react-native';

const imgBackGround = require("../assets/Background Image.png")

const Start = ({ navigation }) => {
  const [background, setBackground] = useState('');
  const [name, setName] = useState('');
  const auth = getAuth();

  const signInUser = () => {
    
        navigation.navigate("Login");
        Alert.alert("Signed in Successfully!");
      
      
  }
 

  return (
    <View style={styles.container}>
      <ImageBackground source={imgBackGround} style={styles.image}>
      <Text style={styles.title}>Chat-App</Text>

        <View style={styles.inputBox}>

          {/* username input */}

            <TextInput
              style={styles.textInput}
              value={name}
              onChangeText={setName}
              placeholder='Your Name'>
            </TextInput>

          <View>
        
            <Text style={styles.chooseBgText}>Choose Background Color:</Text>

            {/* container for colors: */}
            <View style={styles.colorButtonBox}>
              {/* color 1 */}

              <TouchableOpacity
                style={[styles.colorButton, styles.colorInput1]}
                onPress={() => {
                  setBackground(styles.colorInput1.backgroundColor);
                }}
              ></TouchableOpacity>

              {/* color 2 */}
              <TouchableOpacity
                style={[styles.colorButton, styles.colorInput2]}
                onPress={() => {
                  setBackground(styles.colorInput2.backgroundColor);
                }}
              ></TouchableOpacity>

              {/* color 3 */}
              <TouchableOpacity
                style={[styles.colorButton, styles.colorInput3]}
                onPress={() => {
                  setBackground(styles.colorInput3.backgroundColor);
                }}
              ></TouchableOpacity>

              {/* color 4 */}
              <TouchableOpacity
                style={[styles.colorButton, styles.colorInput4]}
                onPress={() => {
                  setBackground(styles.colorInput4.backgroundColor);
                }}
              ></TouchableOpacity>
            </View>
          </View>
          {/* button to start chatting, links to chat.js */}
          <TouchableOpacity 
          style={styles.button} 
          onPress={signInUser}>
            <Text style={styles.buttonText}>Start Chatting</Text>
          </TouchableOpacity>
        </View>

      </ImageBackground>

         {/* when typing, makes the keyboard not hide imput or information that would be behind it // padding should be used for ios instead of "height*/}
         {Platform.OS === "ios"?<KeyboardAvoidingView behavior="padding" /> : null}

    </View>

  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
  },
  image: {
    flex: 1,
    justifyContent: "center",
    resizeMode: "cover",
    alignItems: "center",
    justifyContent: "flex-end",
  },
  title: {
    fontSize: 45,
    fontWeight: "600",
    color: "white",
    alignSelf: "center",
    marginBottom: 250,
  },
  inputBox: {
    height: "44%",
    width: "88%",
    backgroundColor: "white",
    alignItems: "center",
    marginBottom: 30,
    justifyContent: "space-evenly",
  },
  textInput: {
    width: '88%',
    padding: 15,
    borderWidth: 2,
    borderRadius: 10,
    marginTop: 15,
    marginBottom: 15,
    fontSize: 16,
    fontWeight: "300",
    color: "#757083",
    opacity: 50
  },
  chooseBgText: {
    fontSize: 16,
    fontWeight: "300",
    color: "#757083",
    margin: 10,
    textAlign: "left",
    alignSelf: "flex-start",
    marginLeft: 20,
  },
  colorButtonBox: {
    // justifyContent: "flex-start",
    display: "flex",
    flexDirection: "row",
    width: "90%",
    justifyContent: "space-evenly",
  },
  colorButton: {
    width: 40,
    height: 40,
    borderRadius: 20,
  },
  colorInput1: {
    backgroundColor: "#090C08",
  },
  colorInput2: {
    backgroundColor: "#474056",
  },
  colorInput3: {
    backgroundColor: "#8A95A5",
  },
  colorInput4: {
    backgroundColor: "#B9C6AE",
  },
  button: {
    backgroundColor: "#757083",
    width: "88%",
    alignItems: "center",
    padding: 20,
    marginTop: 20,
    marginBottom: 20,
    borderRadius: 10,
  },
  buttonText: {
    color: "white",
    fontSize: 16,
    fontWeight: "600",
  },
});

export default Start;